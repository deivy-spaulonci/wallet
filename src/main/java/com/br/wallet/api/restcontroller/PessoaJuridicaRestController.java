package com.br.wallet.api.restcontroller;

import com.br.wallet.dto.request.create.PessoaJuridicaRequestCreateDTO;
import com.br.wallet.dto.response.PessoaJuridicaResponseDTO;
import com.br.wallet.service.PessoaJuridicaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/pessoa-juridica")
public class PessoaJuridicaRestController {

    private final PessoaJuridicaService pessoaJuridicaService;

    public PessoaJuridicaRestController(PessoaJuridicaService pessoaJuridicaService) {
        this.pessoaJuridicaService = pessoaJuridicaService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<PessoaJuridicaResponseDTO>> findByNomeOrCnpjOrRazaoPaged(
            @RequestParam(value = "busca", required = false) String busca,
            Pageable pageable) {
        String texto = busca == null ? "" : busca.trim();
        return ResponseEntity.ok(pessoaJuridicaService.findByNomeOrCnpjOrRazaoPaged(texto, pageable));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PessoaJuridicaResponseDTO> create(@RequestBody @Valid PessoaJuridicaRequestCreateDTO pessoaJuridicaRequestCreateDTO) {
        return new ResponseEntity<>(pessoaJuridicaService.save(pessoaJuridicaRequestCreateDTO), HttpStatus.CREATED);
    }
}
