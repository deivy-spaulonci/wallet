package com.br.wallet.api.restcontroller;

import com.br.wallet.dto.filter.ContaFilterView;
import com.br.wallet.dto.request.create.ContaRequestCreateDTO;
import com.br.wallet.dto.request.update.ContaPagaRequestDTO;
import com.br.wallet.dto.request.update.ContaRequestUpdateDTO;
import com.br.wallet.dto.response.ContaResponseDTO;
import com.br.wallet.dto.response.ContaResponseGastoTipoDTO;
import com.br.wallet.dto.response.ContaResponseTotaisStatusDTO;
import com.br.wallet.dto.response.ContaResponseViewDTO;
import com.br.wallet.service.ContaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/conta")
public class ContaRestController {

    private final ContaService contaService;

    public ContaRestController(ContaService contaService) {
        this.contaService = contaService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<ContaResponseViewDTO> getContasList(@ModelAttribute ContaFilterView contaFilterView, Pageable pageable) {
        return contaService.findContaViewPaged(contaFilterView, pageable);
    }

    @GetMapping("/valorTotal")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal get(@ModelAttribute ContaFilterView contaFilterView){
        return contaService.getSumContaView(contaFilterView);
    }

    @GetMapping("/totalPorTipo")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ContaResponseGastoTipoDTO>> gastoTotalTipo(){
        return new ResponseEntity<List<ContaResponseGastoTipoDTO>>(contaService.gastoTotalTipo(), HttpStatus.OK);
    }

    @GetMapping("/totaisStatus")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ContaResponseTotaisStatusDTO>> totaisStatus(){
        return new ResponseEntity<List<ContaResponseTotaisStatusDTO>>(contaService.totaisStatus(), HttpStatus.OK);
    }

    @GetMapping("/uuid")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ContaResponseDTO> gastoTotalMensal(@RequestParam(value = "uuid", required = true) UUID uuid){
        return new ResponseEntity<>(contaService.findByUuid(uuid), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody @Valid ContaRequestCreateDTO contaRequestCreateDTO){
        return new ResponseEntity<>(contaService.create(contaRequestCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping("/setContaPaga")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> setContaPaga(@RequestBody @Valid ContaPagaRequestDTO contaPagaRequestDTO){
        return new ResponseEntity<>(contaService.setContaPaga(contaPagaRequestDTO), HttpStatus.OK);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> update(@RequestBody @Valid ContaRequestUpdateDTO contaRequestUpdateDTO){
        return new ResponseEntity<>(contaService.update(contaRequestUpdateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("uuid") UUID uuid){
        contaService.deleteByUuid(uuid);
    }

}
