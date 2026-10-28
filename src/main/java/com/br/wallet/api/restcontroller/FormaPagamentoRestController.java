package com.br.wallet.api.restcontroller;

import com.br.wallet.dto.response.FormaPagamentoResponseDTO;
import com.br.wallet.service.FormaPagamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/forma-pagamento")
public class FormaPagamentoRestController {

    private final FormaPagamentoService formaPagamentoService;

    FormaPagamentoRestController(FormaPagamentoService formaPagamentoService) {
        this.formaPagamentoService = formaPagamentoService;
    }

    @GetMapping
    public List<FormaPagamentoResponseDTO> findAll(){
        return formaPagamentoService.all();
    }

    @GetMapping("/uuid")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<FormaPagamentoResponseDTO> findOne(@RequestParam("uuid") UUID uuid){
        return new ResponseEntity<>(formaPagamentoService.findOne(uuid), HttpStatus.OK);
    }
}
