package br.senac.sp.poo.lanchonete.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ItemCardapio {
    private int id;
    private String nome;
    private double preco;
    private Categoria categoria;

    public void exibirDetalhes() {
        System.out.println(nome + " - R$" + preco);
    }

    public double getPreco() {
        return preco;
    }
}
