package com.guilherme.pedidos.strategy.frete;

import com.guilherme.pedidos.model.TipoFrete;

import java.math.BigDecimal;

public interface FreteStrategy {

    TipoFrete tipo();

    BigDecimal calcular(BigDecimal valorPedido);
}
