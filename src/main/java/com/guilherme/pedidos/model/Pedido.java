package com.guilherme.pedidos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String protocolo;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FormaPagamento formaPagamento;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal desconto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoFrete tipoFrete;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal frete;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal total;

    @Column(nullable = false)
    private LocalDateTime criadoEm;

    protected Pedido() {
        // exigido pelo JPA
    }

    public Pedido(String protocolo, BigDecimal valor, FormaPagamento formaPagamento, BigDecimal desconto,
                  TipoFrete tipoFrete, BigDecimal frete) {
        this.protocolo = protocolo;
        this.valor = valor;
        this.formaPagamento = formaPagamento;
        this.desconto = desconto;
        this.tipoFrete = tipoFrete;
        this.frete = frete;
        this.total = valor.subtract(desconto).add(frete);
        this.criadoEm = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public TipoFrete getTipoFrete() {
        return tipoFrete;
    }

    public BigDecimal getFrete() {
        return frete;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
}
