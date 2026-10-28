package com.br.wallet.api.restcontroller;

import com.br.wallet.dto.response.TipoDespesaResponseDTO;
import com.br.wallet.service.TipoDespesaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tipo-despesa")
public class TipoDespesaRestController {

    private final TipoDespesaService tipoDespesaService;

    TipoDespesaRestController(TipoDespesaService tipoDespesaService) {
        this.tipoDespesaService = tipoDespesaService;
    }

    @GetMapping
    public List<TipoDespesaResponseDTO> findAll(){
        return tipoDespesaService.all();
    }
}
