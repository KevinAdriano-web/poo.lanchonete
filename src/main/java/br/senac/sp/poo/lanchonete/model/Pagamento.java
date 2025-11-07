package br.senac.sp.poo.lanchonete.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double valorPago;
    private String metodo;

    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    private StatusPagamento status;

    public void processarPagamento() {
        this.status = StatusPagamento.PROCESSADO;
    }
}
