package br.senac.sp.poo.lanchonete.controller;

import br.senac.sp.poo.lanchonete.model.Categoria;
import br.senac.sp.poo.lanchonete.model.Erro;
import br.senac.sp.poo.lanchonete.repository.CategoriaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getCategoria(@PathVariable("id") Long id) {
        return repository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Iterable<Categoria>> getCategoria() {
        return ResponseEntity.ok(repository.findAll());
    }

    // @GetMapping
    // public Page<Categoria> listar(@PageableDefault(page = 0, size = 5, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable) {
    //     return repository.findAll(pageable);
    // }

    @PostMapping
    public ResponseEntity<Object> criarCategoria(@Valid @RequestBody Categoria tipo) {
        try {
            repository.save(tipo);
            return ResponseEntity.created(URI.create("/categoria/" + tipo.getId())).body(tipo);
        } catch (DataIntegrityViolationException e) {
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
    public ResponseEntity<Categoria> atualizarCategoria(@PathVariable("id") Long id, @Valid @RequestBody Categoria tipo) {
        tipo.setId(id);
        repository.save(tipo);
        return ResponseEntity.ok(tipo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable("id") Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
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
