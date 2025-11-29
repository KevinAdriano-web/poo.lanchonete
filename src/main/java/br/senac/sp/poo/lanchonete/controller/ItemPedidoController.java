
package br.senac.sp.poo.lanchonete.controller;

import br.senac.sp.poo.lanchonete.model.ItemPedido;
import br.senac.sp.poo.lanchonete.service.ItemPedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/itempedido")
public class ItemPedidoController {

    private final ItemPedidoService service;

    public ItemPedidoController(ItemPedidoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ItemPedido> create(@Valid @RequestBody ItemPedido itemPedido) {
        ItemPedido saved = service.create(itemPedido);
        URI location = URI.create(String.format("/itempedido/%d", saved.getId()));
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<ItemPedido>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemPedido> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemPedido> update(@PathVariable Long id, @Valid @RequestBody ItemPedido itemPedido) {
        ItemPedido updated = service.update(id, itemPedido);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
