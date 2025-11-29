package br.senac.sp.poo.lanchonete.service;

import br.senac.sp.poo.lanchonete.exception.ResourceNotFoundException;
import br.senac.sp.poo.lanchonete.model.ItemPedido;
import br.senac.sp.poo.lanchonete.repository.ItemPedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository repository;

    public ItemPedidoService(ItemPedidoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ItemPedido create(ItemPedido item) {
        // validar/calcular aqui se necessário
        return repository.save(item);
    }

    @Transactional(readOnly = true)
    public List<ItemPedido> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public ItemPedido findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ItemPedido não encontrado com id: " + id));
    }

    @Transactional
    public ItemPedido update(Long id, ItemPedido updated) {
        ItemPedido existing = findById(id);
        // mapear campos permitidos para atualização; se preferir use DTOs
        updated.setId(existing.getId());
        return repository.save(updated);
    }

    @Transactional
    public void delete(Long id) {
        ItemPedido existing = findById(id);
        repository.delete(existing);
    }
}
