package com.senac.avaliacao.controller;

import com.senac.avaliacao.model.Produto;
import com.senac.avaliacao.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping("/cadastrar")
    @ResponseStatus(HttpStatus.CREATED) // Retorna 201 Created
    public Produto cadastrarProduto(@RequestBody Produto produto) {
        return produtoRepository.save(produto); // Salva o produto diretamente
    }

    @GetMapping("/{id}")
    public Produto consultarProduto(@PathVariable Integer id) {
        return produtoRepository.findById(id).orElse(null); // Consulta o produto diretamente
    }
}
