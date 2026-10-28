package com.br.wallet.api.restcontroller;

import com.br.wallet.dto.request.create.PessoaFisicaRequestCreateDTO;
import com.br.wallet.dto.response.PessoaFisicaResponseDTO;
import com.br.wallet.service.PessoaFisicaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/pessoa-fisica")
public class PessoaFisicaRestController {
    private final PessoaFisicaService pessoaFisicaService;

    public PessoaFisicaRestController(PessoaFisicaService pessoaFisicaService) {
        this.pessoaFisicaService = pessoaFisicaService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<PessoaFisicaResponseDTO>> findByNomeOrCpfPaged(
            @RequestParam(value = "busca", required = false) String busca,
            Pageable pageable) {

        String texto = busca == null ? "" : busca.trim();
        return ResponseEntity.ok(pessoaFisicaService.findByNomeOrCpfPaged(texto, pageable));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PessoaFisicaResponseDTO> create(
            @RequestBody @Valid PessoaFisicaRequestCreateDTO pessoaFisicaRequestCreateDTO){
        return new ResponseEntity<>(pessoaFisicaService.save(pessoaFisicaRequestCreateDTO), HttpStatus.CREATED);
    }
}
