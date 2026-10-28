package com.br.wallet.service;

import com.br.wallet.dto.mapper.PessoaJuridicaMapper;
import com.br.wallet.dto.request.create.PessoaJuridicaRequestCreateDTO;
import com.br.wallet.dto.request.update.PessoaJuridicaRequestUpdateDTO;
import com.br.wallet.dto.response.PessoaJuridicaResponseDTO;
import com.br.wallet.entity.PessoaJuridica;
import com.br.wallet.repository.PessoaJuridicaRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaJuridicaService {

    private final PessoaJuridicaRepository pessoaJuridicaRepository;
    private static final PessoaJuridicaMapper pessoaJuridicaMapper = PessoaJuridicaMapper.INSTANCE;
    private final String RECEITA_CLOUD = "https://publica.cnpj.ws/cnpj/";

    public PessoaJuridicaService(PessoaJuridicaRepository pessoaJuridicaRepository) {
        this.pessoaJuridicaRepository = pessoaJuridicaRepository;
    }

    public Page<PessoaJuridicaResponseDTO> findByNomeOrCnpjOrRazaoPaged(String busca, Pageable pageable){
        return pessoaJuridicaRepository.findByNomeOrRazaoSocialOrCnpjPage(busca,pageable);
    }

    public List<PessoaJuridicaResponseDTO> findByNomeOrCnpjOrRazaoPaged(String busca){
        return pessoaJuridicaRepository.findByNomeOrRazaoSocialOrCnpj(busca);
    }

    @Transactional
    public PessoaJuridicaResponseDTO save(PessoaJuridicaRequestCreateDTO pessoaJuridicaRequestCreateDTO){

        PessoaJuridica pessoaJuridicaNew = pessoaJuridicaMapper.toEntity(pessoaJuridicaRequestCreateDTO);
        pessoaJuridicaNew.setTipo('J');
        pessoaJuridicaNew.setFornecedor(true);
        pessoaJuridicaNew.setCnpj(pessoaJuridicaNew.getCnpj().replaceAll("\\D", ""));
        if(this.findByCNPJ(pessoaJuridicaNew.getCnpj()).isEmpty()){
            PessoaJuridica pessoaJuridicaSaved = pessoaJuridicaRepository.save(pessoaJuridicaNew);
            return pessoaJuridicaMapper.toResponseDTO(pessoaJuridicaSaved);
        }else{
            throw new RuntimeException("JA EXISTE O CNPJ CADASTRADO!");
        }
    }

    @Transactional
    public PessoaJuridicaResponseDTO update(PessoaJuridicaRequestUpdateDTO pessoaJuridicaRequestUpdateDTO){
        PessoaJuridica pessoaJuridicaNew = pessoaJuridicaMapper.toEntity(pessoaJuridicaRequestUpdateDTO);
        pessoaJuridicaNew.setTipo('J');
        pessoaJuridicaNew.setFornecedor(true);
        pessoaJuridicaNew.setCnpj(pessoaJuridicaNew.getCnpj().replaceAll("\\D", ""));
        if(this.findByCNPJ(pessoaJuridicaNew.getCnpj()).isEmpty()){
            PessoaJuridica pessoaJuridicaSaved = pessoaJuridicaRepository.save(pessoaJuridicaNew);
            return pessoaJuridicaMapper.toResponseDTO(pessoaJuridicaSaved);
        }else{
            throw new RuntimeException("JA EXISTE O CNPJ CADASTRADO!");
        }
    }

    public Optional<PessoaJuridica> findByCNPJ(String cnpj){
        return pessoaJuridicaRepository.findByCnpj(cnpj);
    }
}
