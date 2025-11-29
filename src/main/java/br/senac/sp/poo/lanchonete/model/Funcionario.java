package br.senac.sp.poo.lanchonete.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "nome é obrigatório")
    @Size(max = 100)
    private String nome;

    @NotBlank(message = "cargo é obrigatório")
    @Size(max = 100)
    private String cargo;

    public void registrarPedido(Pedido pedido) {

    }
}
