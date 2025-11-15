package br.senac.sp.poo.lanchonete.repository;

import br.senac.sp.poo.lanchonete.model.Pedido;
import org.springframework.data.repository.CrudRepository;

public interface PedidoRepository extends CrudRepository<Pedido, Integer> {
}
