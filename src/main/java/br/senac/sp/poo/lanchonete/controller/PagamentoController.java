package br.senac.sp.poo.lanchonete.controller;

import br.senac.sp.poo.lanchonete.model.Erro;
import br.senac.sp.poo.lanchonete.model.Pagamento;
import br.senac.sp.poo.lanchonete.model.Pedido;
import br.senac.sp.poo.lanchonete.repository.PagamentoRepository;
import br.senac.sp.poo.lanchonete.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {

    @Autowired
    private PagamentoRepository repository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> getPagamento(@PathVariable("id") Integer id) {
        Optional<Pagamento> p = repository.findById(id);
        return p.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Iterable<Pagamento>> getPagamentos() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<Object> criarPagamento(@RequestBody Pagamento pagamento) {
        try {
            if (pagamento.getPedido() != null) {
                int pedidoId = pagamento.getPedido().getId();
                Optional<Pedido> pd = pedidoRepository.findById(pedidoId);
                if (pd.isPresent()) pagamento.setPedido(pd.get());
                else {
                    Erro erro = Erro.builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .mensagem("Pedido associado não encontrado.")
                            .exception("NotFound").build();
                    return new ResponseEntity<>(erro, erro.getStatus());
                }
            }
            repository.save(pagamento);
            return ResponseEntity.created(URI.create("/pagamento/" + pagamento.getId())).body(pagamento);
        } catch (DataIntegrityViolationException e) {
            Erro erro = Erro.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .mensagem("Possível registro duplicado ou violação de integridade.")
                    .exception(e.getClass().getName()).build();
            return new ResponseEntity<>(erro, erro.getStatus());
        } catch (Exception e) {
            Erro erro = Erro.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .mensagem("Ocorreu um erro ao criar o pagamento.")
                    .exception(e.getClass().getName()).build();
            return new ResponseEntity<>(erro, erro.getStatus());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pagamento> atualizarPagamento(@PathVariable("id") Integer id, @RequestBody Pagamento pagamento) {
        pagamento.setId(id);
        if (pagamento.getPedido() != null) {
            Optional<Pedido> pd = pedidoRepository.findById(pagamento.getPedido().getId());
            pd.ifPresent(pagamento::setPedido);
        }
        repository.save(pagamento);
        return ResponseEntity.ok(pagamento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPagamento(@PathVariable("id") Integer id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
