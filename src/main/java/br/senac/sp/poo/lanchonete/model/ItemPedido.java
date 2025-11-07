package br.senac.sp.poo.lanchonete.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int quantidade;
    private double subtotal;

    @ManyToOne
    @JoinColumn(name = "item_cardapio_id")
    private ItemCardapio item;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    public double calcularSubtotal() {
        return item.getPreco() * quantidade;
    }
}
