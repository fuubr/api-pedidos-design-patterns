package com.guilherme.pedidos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PedidoControllerTest {

    @Autowired
    private MockMvc mvc;

    private ResultActions criarPedido(String json) throws Exception {
        return mvc.perform(post("/pedidos").contentType(MediaType.APPLICATION_JSON).content(json));
    }

    @Test
    void criaPedidoNoPixComFreteEconomico() throws Exception {
        criarPedido("""
                {"valor": 100.00, "formaPagamento": "PIX", "tipoFrete": "ECONOMICO"}
                """)
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.protocolo").value(startsWith("PED-")))
                .andExpect(jsonPath("$.desconto").value(10.00))
                .andExpect(jsonPath("$.frete").value(15.00))
                .andExpect(jsonPath("$.total").value(105.00));
    }

    @Test
    void aceitaEnumEmMinusculo() throws Exception {
        criarPedido("""
                {"valor": 250.00, "formaPagamento": "boleto", "tipoFrete": "economico"}
                """)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.frete").value(0.00))
                .andExpect(jsonPath("$.total").value(237.50));
    }

    @Test
    void buscaPedidoCriado() throws Exception {
        String location = criarPedido("""
                {"valor": 50.00, "formaPagamento": "CARTAO", "tipoFrete": "EXPRESSO"}
                """)
                .andReturn().getResponse().getHeader("Location");

        mvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(85.00));
    }

    @Test
    void retorna404QuandoPedidoNaoExiste() throws Exception {
        mvc.perform(get("/pedidos/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value("Pedido 9999 não encontrado"));
    }

    @Test
    void recusaFormaDePagamentoQueNaoExiste() throws Exception {
        criarPedido("""
                {"valor": 100.00, "formaPagamento": "FIADO", "tipoFrete": "RETIRADA"}
                """)
                .andExpect(status().isBadRequest());
    }

    @Test
    void recusaValorZerado() throws Exception {
        criarPedido("""
                {"valor": 0, "formaPagamento": "PIX", "tipoFrete": "RETIRADA"}
                """)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detalhes[0]").value("valor: o valor precisa ser maior que zero"));
    }
}
