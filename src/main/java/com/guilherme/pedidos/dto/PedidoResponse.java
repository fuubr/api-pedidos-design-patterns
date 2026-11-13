package com.guilherme.pedidos.dto;

import com.guilherme.pedidos.model.FormaPagamento;
import com.guilherme.pedidos.model.Pedido;
import com.guilherme.pedidos.model.TipoFrete;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PedidoResponse(
        Long id,
        String protocolo,
        BigDecimal valor,
        FormaPagamento formaPagamento,
        BigDecimal desconto,
        TipoFrete tipoFrete,
        BigDecimal frete,
        BigDecimal total,
        LocalDateTime criadoEm
) {

    public static PedidoResponse de(Pedido pedido) {
        return new PedidoResponse(
                pedido.getId(),
                pedido.getProtocolo(),
                pedido.getValor(),
                pedido.getFormaPagamento(),
                pedido.getDesconto(),
                pedido.getTipoFrete(),
                pedido.getFrete(),
                pedido.getTotal(),
                pedido.getCriadoEm()
        );
    }
}
