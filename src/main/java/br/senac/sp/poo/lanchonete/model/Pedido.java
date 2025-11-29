package br.senac.sp.poo.lanchonete.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonManagedReference
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens;

    private double total;

    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    @ManyToOne
    @JoinColumn(name = "mesa_numero")
    private Mesa mesa;

    @JsonManagedReference
    @OneToMany (mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pagamento> pagamento;

    private StatusPedido status;
    private Date data;

    public double calcularSubtotal() {
        if (itens == null) return 0.0;
        double total = 0.0;
        for (ItemPedido ip : itens) {
            if (ip != null) {
                total += ip.calcularSubtotal();
            }
        }
        return total;
    }

    public double calcularTotal() {
        return calcularSubtotal();
    }
}
