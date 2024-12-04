package com.senac.avaliacao.controller;

import com.senac.avaliacao.service.PedidoService;
import com.senac.avaliacao.dto.PedidoRequest;
import com.senac.avaliacao.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<Pedido>> buscarTodosPedidos() {
        List<Pedido> pedidos = pedidoService.buscarTodosPedidos();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @PostMapping
    public Pedido cadastrarPedido(@RequestBody PedidoRequest pedidoRequest) {
        return pedidoService.salvarPedido(pedidoRequest);
    }

    @GetMapping("/consultar-por-data") // Certifique-se de que o mapeamento está correto
    public List<Pedido> consultarPedidosPorData(
            @RequestParam("dataInicio") String dataInicio,
            @RequestParam("dataFim") String dataFim) {

        // Converter as strings para LocalDateTime
        LocalDateTime inicio = LocalDateTime.parse(dataInicio);
        LocalDateTime fim = LocalDateTime.parse(dataFim);

        // Chamar o serviço para consultar os pedidos
        return pedidoService.consultarPedidosPorData(inicio, fim);
    }
}
