package br.senac.sp.poo.lanchonete.repository;

import br.senac.sp.poo.lanchonete.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
