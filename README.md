# API de Pedidos com Design Patterns

Projeto que fiz para o desafio do curso **Design Patterns com Java: Dos Clássicos (GoF) ao Spring Framework**, da DIO.

A ideia foi pegar o que vi nas aulas (Singleton, Strategy e Facade) e aplicar num cenário que eu conseguisse explicar fácil: uma API de pedidos de uma loja, onde o desconto depende da forma de pagamento e o frete depende do tipo de entrega.

## Tecnologias

- Java 17
- Spring Boot 3 (Web, Data JPA, Validation)
- H2 (banco em memória)
- Springdoc OpenAPI (Swagger)
- JUnit 5 + MockMvc

## Onde cada padrão aparece

**Strategy** - pacote `strategy`

Cada forma de pagamento tem sua regra de desconto (`DescontoPix`, `DescontoBoleto`, `SemDescontoCartao`) e cada tipo de entrega tem sua regra de frete (`FreteEconomico`, `FreteExpresso`, `RetiradaNaLoja`). Todas implementam a mesma interface, então pra criar uma forma de pagamento nova é só criar outra classe com `@Component`, sem encher o código de `if/else`.

**Facade** - `facade/PedidoFacade`

O controller só chama `criar`, `listar` e `buscar`. Quem escolhe a strategy certa, calcula o total, gera o protocolo e salva no banco é a facade. Assim o controller fica bem simples.

**Singleton** - `singleton/GeradorDeProtocolo`

Gera o número de protocolo do pedido (ex: `PED-2026-000001`). Precisa ser uma instância só, senão a sequência poderia repetir. Fiz no estilo *Lazy Holder*, que foi o jeito thread-safe visto na aula de Java puro. Vale lembrar que os beans do Spring (`PedidoFacade`, as strategies...) também são singleton por padrão.

## Regras

- PIX: 10% de desconto
- Boleto: 5% de desconto
- Cartão: sem desconto
- Frete econômico: R$ 15,00 (grátis em pedidos a partir de R$ 200,00)
- Frete expresso: R$ 35,00
- Retirada na loja: grátis

Total = valor - desconto + frete

## Como rodar

```bash
mvn spring-boot:run
```

Depois é só abrir:

- Swagger: http://localhost:8080/swagger-ui.html
- Console do H2: http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:pedidos`, usuário `sa`, sem senha)

## Endpoints

| Método | Rota | O que faz |
|---|---|---|
| POST | `/pedidos` | cria um pedido |
| GET | `/pedidos` | lista os pedidos |
| GET | `/pedidos/{id}` | busca um pedido |

Exemplo:

```bash
curl -X POST http://localhost:8080/pedidos \
  -H "Content-Type: application/json" \
  -d '{"valor": 120.50, "formaPagamento": "PIX", "tipoFrete": "EXPRESSO"}'
```

```json
{
  "id": 1,
  "protocolo": "PED-2026-000001",
  "valor": 120.50,
  "formaPagamento": "PIX",
  "desconto": 12.05,
  "tipoFrete": "EXPRESSO",
  "frete": 35.00,
  "total": 143.45,
  "criadoEm": "2026-11-13T14:04:45.813"
}
```

## Testes

```bash
mvn test
```

Tem teste unitário para as strategies e para o singleton, e teste de integração da API com MockMvc.

## O que eu melhoraria depois

- Trocar o H2 por PostgreSQL
- Salvar a sequência do protocolo no banco (hoje ela reinicia quando a aplicação reinicia)
- Adicionar cupom de desconto usando Chain of Responsibility
