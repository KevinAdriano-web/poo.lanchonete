package br.senac.sp.poo.lanchonete.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class Erro {
    private HttpStatus status;
    private String mensagem;
    private String exception;

}
