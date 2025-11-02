package br.senac.sp.poo.lanchonete.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Pagamento {
    private int id;
    private double valorPago;
    private String metodo;
    private Pedido pedido;
    private StatusPagamento status;

    public void processarPagamento() {
        // Lógica de processamento
        this.status = StatusPagamento.PROCESSADO;
    }
}
