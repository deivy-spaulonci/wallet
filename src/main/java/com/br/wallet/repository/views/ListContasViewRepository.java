package com.br.wallet.repository.views;

import com.br.wallet.dto.filter.ContaFilterView;
import com.br.wallet.entity.ContaStatusEnum;
import com.br.wallet.entity.views.ListContasView;
import com.br.wallet.entity.views.ListContasView_;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public interface ListContasViewRepository extends JpaRepository<ListContasView, UUID>,
        JpaSpecificationExecutor<ListContasView> {

    default BigDecimal getSumContaView(ContaFilterView contaFilterView, EntityManager entityManager){
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Object> query = cb.createQuery(Object.class);
        final Root<ListContasView> root = query.from(ListContasView.class);

        query.multiselect(cb.sum(root.get("valor")));
        query.where(getSpecificatonContaView(contaFilterView).toPredicate(root, query, cb));
        Query qry = entityManager.createQuery(query);
        return (BigDecimal) qry.getSingleResult();
    }

    default Page<ListContasView> listContaViewPaged(Pageable pageable, ContaFilterView contaFilterView){
        return findAll(getSpecificatonContaView(contaFilterView), pageable);
    }

    default Specification<ListContasView> getSpecificatonContaView(ContaFilterView contaFilterView){
        return new Specification<ListContasView>() {
            @Override
            public Predicate toPredicate(Root<ListContasView> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if(contaFilterView!=null){
                    if (Objects.nonNull(contaFilterView.tipoUuid()))
                        predicates.add(criteriaBuilder.equal(root.get(ListContasView_.tipoUuid), contaFilterView.tipoUuid()));

                    if (Objects.nonNull(contaFilterView.vencimentoInicial()) && Objects.nonNull(contaFilterView.vencimentoFinal()))
                        predicates.add(criteriaBuilder.between(root.get(ListContasView_.vencimento), contaFilterView.vencimentoInicial(), contaFilterView.vencimentoFinal()));
                    if (Objects.nonNull(contaFilterView.vencimentoInicial()))
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(ListContasView_.vencimento), contaFilterView.vencimentoInicial()));
                    if (Objects.nonNull(contaFilterView.vencimentoFinal()))
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(ListContasView_.vencimento), contaFilterView.vencimentoFinal()));

                    if (contaFilterView.contaStatus() != null && !contaFilterView.contaStatus().isEmpty()) {
                        List<Predicate> statusPredicates = new ArrayList<>();

                        contaFilterView.contaStatus().forEach((status) -> {
                            List<Predicate> grupo = new ArrayList<>();

                            if(status== ContaStatusEnum.PAGO){
                                grupo.add(criteriaBuilder.isNotNull(root.get(ListContasView_.dataPagamento)));
                            }
                            if(status==ContaStatusEnum.ABERTO){
                                grupo.add(criteriaBuilder.isNull(root.get(ListContasView_.dataPagamento)));
                                grupo.add(criteriaBuilder.greaterThan(root.get(ListContasView_.vencimento), LocalDate.now()));
                            }
                            if(status==ContaStatusEnum.HOJE){
                                grupo.add(criteriaBuilder.isNull(root.get(ListContasView_.dataPagamento)));
                                grupo.add(criteriaBuilder.equal(root.get(ListContasView_.vencimento), LocalDate.now()));
                            }
                            if(status==ContaStatusEnum.ATRASADO){
                                grupo.add(criteriaBuilder.isNull(root.get(ListContasView_.dataPagamento)));
                                grupo.add(criteriaBuilder.lessThan(root.get(ListContasView_.vencimento), LocalDate.now()));
                            }
                            statusPredicates.add(criteriaBuilder.and(grupo.toArray(new Predicate[0])));
                        });
                        predicates.add(criteriaBuilder.or(statusPredicates.toArray(new Predicate[0])));
                    }
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }
}
