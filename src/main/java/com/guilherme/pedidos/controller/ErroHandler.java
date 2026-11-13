package com.guilherme.pedidos.controller;

import com.guilherme.pedidos.exception.PedidoNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ErroHandler {

    public record Erro(String mensagem, List<String> detalhes) {
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PedidoNaoEncontradoException.class)
    public Erro naoEncontrado(PedidoNaoEncontradoException e) {
        return new Erro(e.getMessage(), List.of());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Erro dadosInvalidos(MethodArgumentNotValidException e) {
        List<String> detalhes = e.getBindingResult().getFieldErrors().stream()
                .map(campo -> campo.getField() + ": " + campo.getDefaultMessage())
                .toList();
        return new Erro("Dados inválidos", detalhes);
    }

    // cai aqui quando vem uma forma de pagamento ou frete que não existe no enum
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Erro jsonInvalido(HttpMessageNotReadableException e) {
        return new Erro("Requisição inválida. Formas de pagamento: PIX, BOLETO, CARTAO. "
                + "Fretes: ECONOMICO, EXPRESSO, RETIRADA", List.of());
    }
}
