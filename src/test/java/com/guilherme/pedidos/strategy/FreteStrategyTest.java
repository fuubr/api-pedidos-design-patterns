package com.guilherme.pedidos.strategy;

import com.guilherme.pedidos.strategy.frete.FreteEconomico;
import com.guilherme.pedidos.strategy.frete.FreteExpresso;
import com.guilherme.pedidos.strategy.frete.RetiradaNaLoja;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class FreteStrategyTest {

    @Test
    void economicoCobraQuinzeAbaixoDeDuzentos() {
        assertThat(new FreteEconomico().calcular(new BigDecimal("199.99"))).isEqualByComparingTo("15.00");
    }

    @Test
    void economicoEGratisAPartirDeDuzentos() {
        assertThat(new FreteEconomico().calcular(new BigDecimal("200.00"))).isEqualByComparingTo("0");
    }

    @Test
    void expressoTemValorFixo() {
        assertThat(new FreteExpresso().calcular(new BigDecimal("500.00"))).isEqualByComparingTo("35.00");
    }

    @Test
    void retiradaNaoCobra() {
        assertThat(new RetiradaNaLoja().calcular(new BigDecimal("80.00"))).isEqualByComparingTo("0");
    }
}
