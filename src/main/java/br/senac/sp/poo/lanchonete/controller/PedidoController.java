package br.senac.sp.poo.lanchonete.controller;

import br.senac.sp.poo.lanchonete.model.Erro;
import br.senac.sp.poo.lanchonete.model.ItemPedido;
import br.senac.sp.poo.lanchonete.model.Pedido;
import br.senac.sp.poo.lanchonete.repository.PedidoRepository;
import br.senac.sp.poo.lanchonete.repository.ItemCardapioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private ItemCardapioRepository itemCardapioRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getPedido(@PathVariable("id") Integer id) {
        Optional<Pedido> p = repository.findById(id);
        return p.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Iterable<Pedido>> getPedidos() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<Object> criarPedido(@RequestBody Pedido pedido) {
        try {
            if (pedido.getItens() != null) {
                for (ItemPedido ip : pedido.getItens()) {
                    ip.setPedido(pedido);
                    // se o item for fornecido apenas como id, buscar o ItemCardapio completo para calcular o subtotal
                    if (ip.getItem() != null && ip.getItem().getId() != null) {
                        itemCardapioRepository.findById(ip.getItem().getId()).ifPresent(ip::setItem);
                    }
                    ip.setSubtotal(ip.calcularSubtotal());
                }
            }
            pedido.setTotal(pedido.calcularTotal());
            repository.save(pedido);
            return ResponseEntity.created(URI.create("/pedido/" + pedido.getId())).body(pedido);
        } catch (DataIntegrityViolationException e) {
            Erro erro = Erro.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .mensagem("Possível registro duplicado ou violação de integridade.")
                    .exception(e.getClass().getName()).build();
            return new ResponseEntity<>(erro, erro.getStatus());
        } catch (Exception e) {
            Erro erro = Erro.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .mensagem("Ocorreu um erro ao criar o pedido.")
                    .exception(e.getClass().getName()).build();
            return new ResponseEntity<>(erro, erro.getStatus());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizarPedido(@PathVariable("id") Integer id, @RequestBody Pedido pedido) {
        pedido.setId(id);
        if (pedido.getItens() != null) {
            for (ItemPedido ip : pedido.getItens()) {
                ip.setPedido(pedido);
                if (ip.getItem() != null && ip.getItem().getId() != null) {
                    itemCardapioRepository.findById(ip.getItem().getId()).ifPresent(ip::setItem);
                }
                ip.setSubtotal(ip.calcularSubtotal());
            }
        }
        pedido.setTotal(pedido.calcularTotal());
        repository.save(pedido);
        return ResponseEntity.ok(pedido);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable("id") Integer id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

