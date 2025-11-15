package br.senac.sp.poo.lanchonete.controller;

import br.senac.sp.poo.lanchonete.model.Erro;
import br.senac.sp.poo.lanchonete.model.ItemCardapio;
import br.senac.sp.poo.lanchonete.repository.ItemCardapioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/itemcardapio")
public class ItemCardapioController {

    @Autowired
    private ItemCardapioRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity<ItemCardapio> getItem(@PathVariable("id") Integer id) {
        Optional<ItemCardapio> it = repository.findById(id);
        return it.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Iterable<ItemCardapio>> getItens() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<Object> criarItem(@RequestBody ItemCardapio item) {
        try {
            repository.save(item);
            return ResponseEntity.created(URI.create("/itemcardapio/" + item.getId())).body(item);
        } catch (DataIntegrityViolationException e) {
            Erro erro = Erro.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .mensagem("Possível registro duplicado ou violação de integridade.")
                    .exception(e.getClass().getName()).build();
            return new ResponseEntity<>(erro, erro.getStatus());
        } catch (Exception e) {
            Erro erro = Erro.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .mensagem("Ocorreu um erro ao criar o item.")
                    .exception(e.getClass().getName()).build();
            return new ResponseEntity<>(erro, erro.getStatus());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemCardapio> atualizarItem(@PathVariable("id") Integer id, @RequestBody ItemCardapio item) {
        item.setId(id);
        repository.save(item);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItem(@PathVariable("id") Integer id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
