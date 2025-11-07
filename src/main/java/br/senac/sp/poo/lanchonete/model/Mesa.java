package br.senac.sp.poo.lanchonete.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@Entity
public class Mesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numero;
    private StatusMesa status;
    private boolean ocupado;

    public StatusMesa getStatus() {
        return status;
    }
}
