package br.senac.sp.poo.lanchonete.controller;

import br.senac.sp.poo.lanchonete.model.Categoria;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
    @PostMapping
    public ResponseEntity<Categoria> criarCategoria(@RequestBody Categoria tipo) {
        return null;
    }

}
