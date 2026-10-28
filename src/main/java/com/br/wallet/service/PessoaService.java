package com.br.wallet.service;

import com.br.wallet.dto.response.PessoaResponseDTO;
import com.br.wallet.entity.Pessoa;
import com.br.wallet.repository.PessoaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public List<PessoaResponseDTO> find(String texto, Sort sort){
        return this.pessoaRepository.find(texto, sort);
    }

    public Pessoa findPessoaByUuid(UUID uuid){
        return pessoaRepository.findPessoaByUuid(uuid).orElseThrow(() -> new
                EntityNotFoundException("Pessoa não encontrado com o uudi: " + uuid));
    }
}
