package com.br.wallet.repository.views;

import com.br.wallet.dto.filter.DespesaFilterView;
import com.br.wallet.entity.Despesa;
import com.br.wallet.entity.Despesa_;
import com.br.wallet.entity.views.ListDespesasView;
import com.br.wallet.entity.views.ListDespesasView_;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public interface ListDespesaViewRepository extends JpaRepository<ListDespesasView, UUID>,
        JpaSpecificationExecutor<ListDespesasView> {

    default Page<ListDespesasView> listDespesaPagedView(Pageable pageable, DespesaFilterView despesaFilterView) {
        return findAll(getSpecificatonDespesaView(despesaFilterView), pageable);
    }

    default BigDecimal getSumTotalDespesaView(DespesaFilterView despesaFilterView, EntityManager entityManager) {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Object> query = cb.createQuery(Object.class);
        final Root<ListDespesasView> root = query.from(ListDespesasView.class);

        query.multiselect(cb.sum(root.get(ListDespesasView_.valor)));
        query.where(getSpecificatonDespesaView(despesaFilterView).toPredicate(root, query, cb));
        Query qry = entityManager.createQuery(query);
        return (BigDecimal) qry.getSingleResult();
    }

    default List getDespesaByTipo(EntityManager entityManager) {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Object> query = cb.createQuery(Object.class);
        final Root<ListDespesasView> root = query.from(ListDespesasView.class);
        query.multiselect(
                root.get(ListDespesasView_.tipoDespesa),
                cb.sum(root.get(ListDespesasView_.valor)));
        query.groupBy(root.get(ListDespesasView_.tipoDespesa));
        Query qry = entityManager.createQuery(query);
        return qry.getResultList();
    }

    default Specification<ListDespesasView> getSpecificatonDespesaView(DespesaFilterView despesaFilterView) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (despesaFilterView.tipos()!=null && !despesaFilterView.tipos().isEmpty())
                predicates.add(root.get(ListDespesasView_.tipoDespesa).in(despesaFilterView.tipos()));
            if (Objects.nonNull(despesaFilterView.fornecedorUuid()))
                predicates.add(criteriaBuilder.equal(root.get(ListDespesasView_.fornecedorUuid), despesaFilterView.fornecedorUuid()));
            if (Objects.nonNull(despesaFilterView.inicio()) && Objects.nonNull(despesaFilterView.termino()))
                predicates.add(criteriaBuilder.between(root.get(ListDespesasView_.dataPagamento), despesaFilterView.inicio(), despesaFilterView.termino()));
            if (Objects.nonNull(despesaFilterView.inicio()))
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(ListDespesasView_.dataPagamento), despesaFilterView.inicio()));
            if (Objects.nonNull(despesaFilterView.termino()))
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(ListDespesasView_.dataPagamento), despesaFilterView.termino()));
            if (Objects.nonNull(despesaFilterView.formaPagamentoUuid()))
                predicates.add(criteriaBuilder.equal(root.get(ListDespesasView_.formaPagamentoUuid), despesaFilterView.formaPagamentoUuid()));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
