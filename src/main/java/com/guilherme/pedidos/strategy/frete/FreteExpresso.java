package com.guilherme.pedidos.strategy.frete;

import com.guilherme.pedidos.model.TipoFrete;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FreteExpresso implements FreteStrategy {

    @Override
    public TipoFrete tipo() {
        return TipoFrete.EXPRESSO;
    }

    @Override
    public BigDecimal calcular(BigDecimal valorPedido) {
        return new BigDecimal("35.00");
    }
}
