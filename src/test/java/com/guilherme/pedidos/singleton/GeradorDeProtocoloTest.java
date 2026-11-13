package com.guilherme.pedidos.singleton;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GeradorDeProtocoloTest {

    @Test
    void sempreDevolveAMesmaInstancia() {
        assertThat(GeradorDeProtocolo.getInstancia()).isSameAs(GeradorDeProtocolo.getInstancia());
    }

    @Test
    void geraProtocolosDiferentesEmSequencia() {
        GeradorDeProtocolo gerador = GeradorDeProtocolo.getInstancia();
        String primeiro = gerador.proximo();
        String segundo = gerador.proximo();

        assertThat(primeiro).matches("PED-\\d{4}-\\d{6}");
        assertThat(segundo).isNotEqualTo(primeiro);
    }
}
