package br.senac.sp.poo.lanchonete.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
public class ItemCardapio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "nome é obrigatório")
    @Column(nullable = false, length = 150)
    private String nome;

    @NotNull(message = "preco é obrigatório")
    @Positive(message = "preco deve ser maior que zero")
    @Column(nullable = false)
    private Double preco;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @NotNull(message = "categoria é obrigatória")
    private Categoria categoria;

    public void exibirDetalhes() {
        System.out.println(nome + " - R$" + preco);
    }
}
