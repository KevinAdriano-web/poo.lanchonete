package br.senac.sp.poo.lanchonete.controller;

import br.senac.sp.poo.lanchonete.model.Erro;
import br.senac.sp.poo.lanchonete.model.Mesa;
import br.senac.sp.poo.lanchonete.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/mesa")
public class MesaController {

    @Autowired
    private MesaRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity<Mesa> getMesa(@PathVariable("id") Long id) {
        Optional<Mesa> mesa = repository.findById(id);
        return mesa.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Iterable<Mesa>> getMesas() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<Object> criarMesa(@RequestBody Mesa mesa) {
        try {
            repository.save(mesa);
            return ResponseEntity.created(URI.create("/mesa/" + mesa.getId())).body(mesa);
        } catch (DataIntegrityViolationException e) {
            Erro erro = Erro.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .mensagem("Possível registro duplicado")
                    .exception(e.getClass().getName())
                    .build();
            return new ResponseEntity<>(erro, erro.getStatus());
        } catch (Exception e) {
            Erro erro = Erro.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .mensagem("Ocorreu um erro ao criar a mesa: " + e.getMessage())
                    .exception(e.getClass().getName())
                    .build();
            return new ResponseEntity<>(erro, erro.getStatus());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mesa> atualizarMesa(@PathVariable("id") Long id, @RequestBody Mesa mesa) {
        mesa.setId(id);
        repository.save(mesa);
        return ResponseEntity.ok(mesa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMesa(@PathVariable("id") Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
