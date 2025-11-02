package br.senac.sp.poo.lanchonete.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ItemPedido {
    private int id;
    private int quantidade;
    private double subtotal;
    private ItemCardapio item;

    public double calcularSubtotal() {
        return item.getPreco() * quantidade;
    }
}
