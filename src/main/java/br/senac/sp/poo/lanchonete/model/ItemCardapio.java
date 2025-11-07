package br.senac.sp.poo.lanchonete.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
public class ItemCardapio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private double preco;

    @ManyToOne
    @JoinColumn (name = "categoria_id")
    private Categoria categoria;

    public void exibirDetalhes() {
        System.out.println(nome + " - R$" + preco);
    }

    public double getPreco() {
        return preco;
    }
}
