package com.senac.avaliacao.repository;

import com.senac.avaliacao.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Método para buscar clientes com débito maior que zero
    List<Cliente> findByDebitoGreaterThan(BigDecimal debito);
}
