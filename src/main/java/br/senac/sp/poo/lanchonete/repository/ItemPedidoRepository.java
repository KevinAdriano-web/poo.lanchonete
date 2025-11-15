package br.senac.sp.poo.lanchonete.repository;

import br.senac.sp.poo.lanchonete.model.ItemCardapio;
import org.springframework.data.repository.CrudRepository;

public interface ItemPedidoRepository extends CrudRepository<ItemCardapio, Integer> {
}
