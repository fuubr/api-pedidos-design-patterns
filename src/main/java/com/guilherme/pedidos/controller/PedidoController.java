package com.guilherme.pedidos.controller;

import com.guilherme.pedidos.dto.PedidoRequest;
import com.guilherme.pedidos.dto.PedidoResponse;
import com.guilherme.pedidos.facade.PedidoFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Pedidos")
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoFacade pedidoFacade;

    public PedidoController(PedidoFacade pedidoFacade) {
        this.pedidoFacade = pedidoFacade;
    }

    @Operation(summary = "Cria um pedido calculando desconto e frete")
    @PostMapping
    public ResponseEntity<PedidoResponse> criar(@Valid @RequestBody PedidoRequest request) {
        PedidoResponse pedido = pedidoFacade.criar(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(pedido.id()).toUri();
        return ResponseEntity.created(location).body(pedido);
    }

    @Operation(summary = "Lista todos os pedidos")
    @GetMapping
    public List<PedidoResponse> listar() {
        return pedidoFacade.listar();
    }

    @Operation(summary = "Busca um pedido pelo id")
    @GetMapping("/{id}")
    public PedidoResponse buscar(@PathVariable Long id) {
        return pedidoFacade.buscar(id);
    }
}
