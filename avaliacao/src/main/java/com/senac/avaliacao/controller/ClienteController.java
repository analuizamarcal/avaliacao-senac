package com.senac.avaliacao.controller;

import com.senac.avaliacao.model.Cliente;
import com.senac.avaliacao.repository.ClienteRepository;
import com.senac.avaliacao.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/cadastrar")
    @ResponseStatus(HttpStatus.CREATED) // Retorna 201 Created
    public Cliente cadastrarCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente); // Salva o cliente diretamente
    }

    @GetMapping("/{id}")
    public Cliente consultarCliente(@PathVariable Integer id) {
        return clienteRepository.findById(Long.valueOf(id)).orElse(null); // Consulta o cliente diretamente
    }

    @GetMapping("/debito-pendente")
    public List<Cliente> consultarDebitosPendentes() {
        return clienteService.consultarDebitosPendentes(); // Chama o método do serviço
    }

    @PostMapping("/pagamento-fiado")
    public ResponseEntity<String> registrarPagamentoFiado(@RequestParam int clienteId, @RequestParam double valorPago) {
        try {
            clienteService.registrarPagamentoFiado(clienteId, valorPago);
            return ResponseEntity.status(HttpStatus.OK).body("Pagamento fiado registrado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao registrar pagamento fiado: " + e.getMessage());
        }
    }
}

