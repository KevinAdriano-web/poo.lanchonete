package br.senac.sp.poo.lanchonete.controller;

import br.senac.sp.poo.lanchonete.model.Categoria;
import br.senac.sp.poo.lanchonete.model.Erro;
import br.senac.sp.poo.lanchonete.repository.CategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getCategoria(@PathVariable("id") Long id) {
        Optional<Categoria> tipo = repository.findById(id);
        if (tipo.isPresent()) {
            return ResponseEntity.ok(tipo.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<Categoria>> getCategoria() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<Object> criarCategoria(@RequestBody Categoria tipo) {
        try {
            repository.save(tipo);
            // Retorna o status 201 Created com o URI da nova categoria.
            return ResponseEntity.created(URI.create("/categoria/" + tipo.getId())).body(tipo);
        } catch (DataIntegrityViolationException e) {
            //
            Erro erro = Erro.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .mensagem("Categoria com nome duplicado não é permitida.")
                    .exception(e.getClass().getName()).build();
            return new ResponseEntity(erro, erro.getStatus());
        } catch (Exception e) {
            Erro erro = Erro.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .mensagem("Ocorreu um erro ao criar a categoria.")
                    .exception(e.getClass().getName()).build();
            return new ResponseEntity(erro, erro.getStatus());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> atualizarCategoria(@PathVariable("id") Long id, @RequestBody Categoria tipo) {
        tipo.setId(id);
        repository.save(tipo);
        return ResponseEntity.ok(tipo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable("id") Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
