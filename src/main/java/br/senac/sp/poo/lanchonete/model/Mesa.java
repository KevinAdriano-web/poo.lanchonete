package br.senac.sp.poo.lanchonete.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Mesa {
    private int numero;
    private StatusMesa status;
    private boolean ocupado;

    public StatusMesa getStatus() {
        return status;
    }

    // Getters, setters e construtores podem ser adicionados conforme necessário
}
