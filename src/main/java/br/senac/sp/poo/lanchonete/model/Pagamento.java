package br.senac.sp.poo.lanchonete.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private double valorPago;
    private String metodo;

    @OneToOne
    @JoinColumn(name = "pedido_id")
    @com.fasterxml.jackson.annotation.JsonBackReference
    private Pedido pedido;

    private StatusPagamento status;

    public void processarPagamento() {
        this.status = StatusPagamento.PROCESSADO;
    }
}
