package br.senac.sp.poo.lanchonete.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Funcionario {
    private Long id;
    private String nome;
    private String cargo;

    public void registrarPedido(Pedido pedido) {
        // Lógica de registro
    }
}
