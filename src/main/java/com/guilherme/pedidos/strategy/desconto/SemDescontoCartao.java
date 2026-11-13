package com.guilherme.pedidos.strategy.desconto;

import com.guilherme.pedidos.model.FormaPagamento;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SemDescontoCartao implements DescontoStrategy {

    @Override
    public FormaPagamento formaPagamento() {
        return FormaPagamento.CARTAO;
    }

    @Override
    public BigDecimal calcular(BigDecimal valor) {
        return new BigDecimal("0.00");
    }
}
