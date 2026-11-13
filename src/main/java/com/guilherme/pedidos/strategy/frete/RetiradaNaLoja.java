package com.guilherme.pedidos.strategy.frete;

import com.guilherme.pedidos.model.TipoFrete;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class RetiradaNaLoja implements FreteStrategy {

    @Override
    public TipoFrete tipo() {
        return TipoFrete.RETIRADA;
    }

    @Override
    public BigDecimal calcular(BigDecimal valorPedido) {
        return new BigDecimal("0.00");
    }
}
