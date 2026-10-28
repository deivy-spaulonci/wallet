package com.br.wallet.service;

import com.br.wallet.dto.mapper.FormaPagamentoMapper;
import com.br.wallet.dto.response.FormaPagamentoResponseDTO;
import com.br.wallet.entity.FormaPagamento;
import com.br.wallet.repository.FormaPagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FormaPagamentoService {

    private final FormaPagamentoRepository formaPagamentoRepository;
    private static final FormaPagamentoMapper formaPagamentoMapper = FormaPagamentoMapper.INSTANCE;

    public FormaPagamentoService(FormaPagamentoRepository formaPagamentoRepository) {
        this.formaPagamentoRepository = formaPagamentoRepository;
    }

    public List<FormaPagamentoResponseDTO> all() {
        return formaPagamentoMapper.toListPagamentoResponseDTO(formaPagamentoRepository.findAll(Sort.by(Sort.Direction.ASC, "modalidade")));
    }

    public FormaPagamentoResponseDTO findOne(UUID uuid){
        return formaPagamentoMapper.toDTO(findByUuid(uuid));
    }

    public FormaPagamento findByUuid(UUID uuid) {
        return formaPagamentoRepository.findByUuid(uuid).orElseThrow(() -> new
                EntityNotFoundException("Forma Pagamento não encontrada com o uudi: " + uuid));
    }
}
