package br.senac.sp.poo.lanchonete.repository;

import br.senac.sp.poo.lanchonete.model.Pagamento;
import org.springframework.data.repository.CrudRepository;

public interface PagamentoRepository extends CrudRepository<Pagamento, Integer> {
}
