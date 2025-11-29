package br.senac.sp.poo.lanchonete.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantidade;
    private double subtotal;

    @ManyToOne
    @JoinColumn(name = "item_cardapio_id")
    private ItemCardapio item;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @com.fasterxml.jackson.annotation.JsonBackReference
    private Pedido pedido;

    public double calcularSubtotal() {
        if (item == null || item.getPreco() == null) return 0.0;
        return item.getPreco() * quantidade;
    }
}
