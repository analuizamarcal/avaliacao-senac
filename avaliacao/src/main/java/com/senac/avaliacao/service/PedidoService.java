package com.senac.avaliacao.service;

import com.senac.avaliacao.model.*;
import com.senac.avaliacao.repository.*;
import com.senac.avaliacao.dto.PedidoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public List<Pedido> buscarTodosPedidos() {
        return pedidoRepository.findAll();
    }
    public Pedido salvarPedido(PedidoRequest pedidoRequest) {
        try {
            // Recupera o cliente
            Cliente cliente = clienteRepository.findById(pedidoRequest.getClienteId())
                    .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

            // Criando o Pedido
            Pedido pedido = new Pedido();
            pedido.setCliente(cliente);
            pedido.setFoiFiado(pedidoRequest.getFoiFiado());

            BigDecimal total = BigDecimal.ZERO; // Usando BigDecimal para o total

            // Salvando o pedido antes de adicionar os itens
            pedido = pedidoRepository.save(pedido); // Aqui o pedido é persistido e recebe o ID

            // Iterando sobre os itens do pedido no DTO
            for (Map.Entry<Integer, Integer> entry : pedidoRequest.getItensPedido().entrySet()) {
                Integer produtoId = entry.getKey();
                Integer quantidade = entry.getValue();

                if (quantidade <= 0) {
                    throw new IllegalArgumentException("Quantidade inválida para o produto ID: " + produtoId);
                }

                // Recupera o produto
                Produto produto = produtoRepository.findById(produtoId)
                        .orElseThrow(() -> new IllegalArgumentException("Produto com ID " + produtoId + " não encontrado"));

                // Cria o ItemPedido e define o preço unitário
                ItemPedido itemPedido = new ItemPedido();
                itemPedido.setProduto(produto);
                itemPedido.setQuantidade(quantidade);
                itemPedido.setPrecoUnitario(produto.getPreco()); // Preenchendo o preço unitário
                itemPedido.setPedido(pedido); // Associando o pedido ao item

                // Adiciona o ItemPedido ao pedido
                pedido.addItemPedido(itemPedido);

                // Calcula o preço total do item
                BigDecimal precoTotalItem = produto.getPreco().multiply(BigDecimal.valueOf(quantidade));

                // Soma ao total
                total = total.add(precoTotalItem);
            }

            pedido.setTotal(total);

            if (pedido.getFoiFiado()) {
                BigDecimal novoDebito = cliente.getDebito().add(total);
                cliente.setDebito(novoDebito);
                clienteRepository.save(cliente); // Salva a atualização do débito no cliente
            }

            // Salvando novamente o pedido com os itens agora associados
            return pedidoRepository.save(pedido); // Aqui garantimos que o pedido e os itens sejam salvos

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao processar o pedido: " + e.getMessage());
        }
    }

    public List<Pedido> consultarPedidosPorData(LocalDateTime dataInicio, LocalDateTime dataFim) {
        // Ajusta a dataInicio e dataFim para começar e terminar no início e no fim do dia
        LocalDateTime inicio = dataInicio.truncatedTo(ChronoUnit.DAYS);
        LocalDateTime fim = dataFim.plusDays(1).truncatedTo(ChronoUnit.DAYS);

        return pedidoRepository.findByDataHoraBetween(inicio, fim);
    }
}
