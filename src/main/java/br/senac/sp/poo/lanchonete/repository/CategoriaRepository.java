package br.senac.sp.poo.lanchonete.repository;

import br.senac.sp.poo.lanchonete.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CategoriaRepository extends CrudRepository<Categoria, Long> {
}
