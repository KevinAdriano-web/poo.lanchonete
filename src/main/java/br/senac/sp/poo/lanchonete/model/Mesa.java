package br.senac.sp.poo.lanchonete.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Mesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numero;

    @Enumerated(EnumType.STRING)
    private StatusMesa status;

    private boolean ocupado;

}
