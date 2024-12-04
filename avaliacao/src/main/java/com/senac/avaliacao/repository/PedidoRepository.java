package com.senac.avaliacao.repository;

import com.senac.avaliacao.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Método para buscar pedidos entre duas datas
    List<Pedido> findByDataHoraBetween(LocalDateTime dataInicio, LocalDateTime dataFim);
}
