package com.guilherme.pedidos.singleton;

import java.time.Year;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Singleton no estilo "Lazy Holder": a instância só é criada na primeira chamada de
 * getInstancia() e a própria JVM garante que isso acontece uma vez só (thread-safe).
 * A sequência tem que ser única, senão dois pedidos poderiam ganhar o mesmo protocolo.
 */
public final class GeradorDeProtocolo {

    private final AtomicLong sequencia = new AtomicLong();

    private GeradorDeProtocolo() {
    }

    private static class Holder {
        private static final GeradorDeProtocolo INSTANCIA = new GeradorDeProtocolo();
    }

    public static GeradorDeProtocolo getInstancia() {
        return Holder.INSTANCIA;
    }

    public String proximo() {
        return String.format("PED-%d-%06d", Year.now().getValue(), sequencia.incrementAndGet());
    }
}
