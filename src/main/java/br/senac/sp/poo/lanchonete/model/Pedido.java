package br.senac.sp.poo.lanchonete.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
public class Pedido {
    private int id;
    private List<ItemPedido> itens;
    private double total;
    private StatusPedido status;
    private Date data;

    public double calcularSubtotal() {
        return itens.stream().mapToDouble(ItemPedido::calcularSubtotal).sum();
    }

    public double calcularTotal() {
        return calcularSubtotal(); // Pode incluir taxas ou descontos futuramente
    }
}
