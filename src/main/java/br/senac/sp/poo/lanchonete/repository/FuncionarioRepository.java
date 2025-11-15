package br.senac.sp.poo.lanchonete.repository;

import br.senac.sp.poo.lanchonete.model.Funcionario;
import org.springframework.data.repository.CrudRepository;

public interface FuncionarioRepository extends CrudRepository<Funcionario, Long> {
}
