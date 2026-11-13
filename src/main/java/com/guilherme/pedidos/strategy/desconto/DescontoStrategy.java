package com.guilherme.pedidos.strategy.desconto;

import com.guilherme.pedidos.model.FormaPagamento;

import java.math.BigDecimal;

public interface DescontoStrategy {

    FormaPagamento formaPagamento();

    BigDecimal calcular(BigDecimal valor);
}
