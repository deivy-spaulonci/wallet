package com.br.wallet.repository;

import com.br.wallet.dto.response.PessoaJuridicaResponseDTO;
import com.br.wallet.entity.PessoaJuridica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, BigInteger> {
    String VALOR = """
    SELECT new com.br.wallet.dto.response.PessoaJuridicaResponseDTO(        
        pj.nome,
        pj.razaoSocial,
        pj.cnpj,
        pj.codigoIbge
    )
    FROM PessoaJuridica pj
    WHERE LOWER(pj.nome) LIKE LOWER(CONCAT('%', :busca, '%'))
       OR LOWER(pj.cnpj) LIKE LOWER(CONCAT('%', :busca, '%'))
       OR LOWER(pj.razaoSocial)  LIKE LOWER(CONCAT('%', :busca, '%'))
    """;
    @Query(VALOR)
    Page<PessoaJuridicaResponseDTO> findByNomeOrRazaoSocialOrCnpjPage(@Param("busca") String busca, Pageable pageable);

    @Query(VALOR)
    List<PessoaJuridicaResponseDTO> findByNomeOrRazaoSocialOrCnpj(@Param("busca") String busca);

    Optional<PessoaJuridica> findByCnpj(String cnpj);
}
