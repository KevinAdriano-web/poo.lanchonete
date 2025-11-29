package br.senac.sp.poo.lanchonete.controller;

import br.senac.sp.poo.lanchonete.model.Erro;
import br.senac.sp.poo.lanchonete.model.ItemCardapio;
import br.senac.sp.poo.lanchonete.repository.ItemCardapioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/itemcardapio")
public class ItemCardapioController {

    @Autowired
    private ItemCardapioRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity<ItemCardapio> getItem(@PathVariable("id") Integer id) {
        return repository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Iterable<ItemCardapio>> getItens() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<Object> criarItem(@Valid @RequestBody ItemCardapio item) {
        try {
            repository.save(item);
            return ResponseEntity.created(URI.create("/itemcardapio/" + item.getId())).body(item);
        } catch (DataIntegrityViolationException e) {
            Erro erro = Erro.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .mensagem("Possível violação de integridade ou dado inválido.")
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
    public ResponseEntity<ItemCardapio> atualizarItem(@PathVariable("id") Integer id, @Valid @RequestBody ItemCardapio item) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        item.setId(id);
        repository.save(item);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItem(@PathVariable("id") Integer id) {
        try {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Erro> handleValidation(MethodArgumentNotValidException ex) {
        String detalhes = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.joining("; "));
        Erro erro = Erro.builder()
                .status(HttpStatus.BAD_REQUEST)
                .mensagem("Erro de validação: " + detalhes)
                .exception(ex.getClass().getName()).build();
        return new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);
    }
}
