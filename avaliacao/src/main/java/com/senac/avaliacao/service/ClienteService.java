package com.senac.avaliacao.service;

import com.senac.avaliacao.model.Cliente;
import com.senac.avaliacao.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Método para registrar pagamento fiado e reduzir o débito do cliente
    public void registrarPagamentoFiado(int clienteId, double valorPago) {
        Cliente cliente = clienteRepository.findById((long) clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        BigDecimal valorPagoBigDecimal = BigDecimal.valueOf(valorPago);

        // Verifica se o cliente tem débito e se o pagamento é maior que 0
        if (valorPagoBigDecimal.compareTo(BigDecimal.ZERO) > 0 && cliente.getDebito().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal debitoRestante = cliente.getDebito().subtract(valorPagoBigDecimal);

            // Se o débito não for zerado, apenas atualiza o débito restante
            if (debitoRestante.compareTo(BigDecimal.ZERO) > 0) {
                cliente.setDebito(debitoRestante);
            } else {
                // Caso o pagamento seja maior que o débito, o débito vai a zero
                cliente.setDebito(BigDecimal.ZERO);
            }

            clienteRepository.save(cliente);  // Salva as alterações no cliente
        } else {
            throw new IllegalArgumentException("Pagamento inválido ou cliente não tem débito");
        }
    }

    public List<Cliente> consultarDebitosPendentes() {
        return clienteRepository.findByDebitoGreaterThan(BigDecimal.ZERO); // Busca clientes com débito > 0
    }
}
