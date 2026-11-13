package com.guilherme.pedidos.strategy;

import com.guilherme.pedidos.strategy.desconto.DescontoBoleto;
import com.guilherme.pedidos.strategy.desconto.DescontoPix;
import com.guilherme.pedidos.strategy.desconto.SemDescontoCartao;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class DescontoStrategyTest {

    private final BigDecimal valor = new BigDecimal("150.00");

    @Test
    void pixDaDezPorCento() {
        assertThat(new DescontoPix().calcular(valor)).isEqualByComparingTo("15.00");
    }

    @Test
    void boletoDaCincoPorCento() {
        assertThat(new DescontoBoleto().calcular(valor)).isEqualByComparingTo("7.50");
    }

    @Test
    void cartaoNaoTemDesconto() {
        assertThat(new SemDescontoCartao().calcular(valor)).isEqualByComparingTo("0");
    }

    @Test
    void arredondaParaDuasCasas() {
        assertThat(new DescontoBoleto().calcular(new BigDecimal("33.33"))).isEqualByComparingTo("1.67");
    }
}
