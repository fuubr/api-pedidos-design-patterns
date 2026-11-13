package com.guilherme.pedidos.facade;

import com.guilherme.pedidos.dto.PedidoRequest;
import com.guilherme.pedidos.dto.PedidoResponse;
import com.guilherme.pedidos.exception.PedidoNaoEncontradoException;
import com.guilherme.pedidos.model.FormaPagamento;
import com.guilherme.pedidos.model.Pedido;
import com.guilherme.pedidos.model.TipoFrete;
import com.guilherme.pedidos.repository.PedidoRepository;
import com.guilherme.pedidos.singleton.GeradorDeProtocolo;
import com.guilherme.pedidos.strategy.desconto.DescontoStrategy;
import com.guilherme.pedidos.strategy.frete.FreteStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Facade: o controller só chama criar/listar/buscar e não precisa saber
 * qual strategy usar, como o total é calculado ou de onde vem o protocolo.
 */
@Service
public class PedidoFacade {

    private final Map<FormaPagamento, DescontoStrategy> descontos = new EnumMap<>(FormaPagamento.class);
    private final Map<TipoFrete, FreteStrategy> fretes = new EnumMap<>(TipoFrete.class);
    private final PedidoRepository repository;

    public PedidoFacade(List<DescontoStrategy> descontos, List<FreteStrategy> fretes,
                        PedidoRepository repository) {
        descontos.forEach(d -> this.descontos.put(d.formaPagamento(), d));
        fretes.forEach(f -> this.fretes.put(f.tipo(), f));
        this.repository = repository;
    }

    @Transactional
    public PedidoResponse criar(PedidoRequest request) {
        BigDecimal valor = request.valor().setScale(2, RoundingMode.HALF_UP);

        BigDecimal desconto = descontos.get(request.formaPagamento()).calcular(valor);
        BigDecimal frete = fretes.get(request.tipoFrete()).calcular(valor);
        String protocolo = GeradorDeProtocolo.getInstancia().proximo();

        Pedido pedido = new Pedido(protocolo, valor, request.formaPagamento(), desconto,
                request.tipoFrete(), frete);

        return PedidoResponse.de(repository.save(pedido));
    }

    @Transactional(readOnly = true)
    public List<PedidoResponse> listar() {
        return repository.findAll().stream().map(PedidoResponse::de).toList();
    }

    @Transactional(readOnly = true)
    public PedidoResponse buscar(Long id) {
        return repository.findById(id)
                .map(PedidoResponse::de)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));
    }
}
