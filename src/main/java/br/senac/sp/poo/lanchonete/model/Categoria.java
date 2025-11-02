package br.senac.sp.poo.lanchonete.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Categoria {
    private int id;
    private String nome;
    private String descricao;
}
