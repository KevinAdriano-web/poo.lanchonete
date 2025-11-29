package br.senac.sp.poo.lanchonete.controller;

import br.senac.sp.poo.lanchonete.model.Erro;
import br.senac.sp.poo.lanchonete.model.Funcionario;
import br.senac.sp.poo.lanchonete.repository.FuncionarioRepository;
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
@RequestMapping("/funcionario")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> getFuncionario(@PathVariable("id") Long id) {
        return repository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Iterable<Funcionario>> getFuncionarios() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<Object> criarFuncionario(@Valid @RequestBody Funcionario funcionario) {
        try {
            repository.save(funcionario);
            return ResponseEntity.created(URI.create("/funcionario/" + funcionario.getId())).body(funcionario);
        } catch (DataIntegrityViolationException e) {
            Erro erro = Erro.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .mensagem("Possível registro duplicado ou violação de integridade.")
                    .exception(e.getClass().getName())
                    .build();
            return new ResponseEntity<>(erro, erro.getStatus());
        } catch (Exception e) {
            Erro erro = Erro.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .mensagem("Ocorreu um erro ao criar o funcionário.")
                    .exception(e.getClass().getName())
                    .build();
            return new ResponseEntity<>(erro, erro.getStatus());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> atualizarFuncionario(@PathVariable("id") Long id, @Valid @RequestBody Funcionario funcionario) {
        funcionario.setId(id);
        repository.save(funcionario);
        return ResponseEntity.ok(funcionario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFuncionario(@PathVariable("id") Long id) {
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
