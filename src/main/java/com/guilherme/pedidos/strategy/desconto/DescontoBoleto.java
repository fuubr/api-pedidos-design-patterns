package com.guilherme.pedidos.strategy.desconto;

import com.guilherme.pedidos.model.FormaPagamento;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class DescontoBoleto implements DescontoStrategy {

    private static final BigDecimal PERCENTUAL = new BigDecimal("0.05");

    @Override
    public FormaPagamento formaPagamento() {
        return FormaPagamento.BOLETO;
    }

    @Override
    public BigDecimal calcular(BigDecimal valor) {
        return valor.multiply(PERCENTUAL).setScale(2, RoundingMode.HALF_UP);
    }
}
