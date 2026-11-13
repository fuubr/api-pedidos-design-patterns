package com.guilherme.pedidos.dto;

import com.guilherme.pedidos.model.FormaPagamento;
import com.guilherme.pedidos.model.TipoFrete;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PedidoRequest(
        @NotNull(message = "informe o valor")
        @DecimalMin(value = "0.01", message = "o valor precisa ser maior que zero")
        BigDecimal valor,

        @NotNull(message = "informe a forma de pagamento")
        FormaPagamento formaPagamento,

        @NotNull(message = "informe o tipo de frete")
        TipoFrete tipoFrete
) {
}
