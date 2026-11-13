package com.guilherme.pedidos.strategy.frete;

import com.guilherme.pedidos.model.TipoFrete;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FreteEconomico implements FreteStrategy {

    private static final BigDecimal VALOR = new BigDecimal("15.00");
    private static final BigDecimal MINIMO_FRETE_GRATIS = new BigDecimal("200.00");

    @Override
    public TipoFrete tipo() {
        return TipoFrete.ECONOMICO;
    }

    @Override
    public BigDecimal calcular(BigDecimal valorPedido) {
        if (valorPedido.compareTo(MINIMO_FRETE_GRATIS) >= 0) {
            return new BigDecimal("0.00");
        }
        return VALOR;
    }
}
