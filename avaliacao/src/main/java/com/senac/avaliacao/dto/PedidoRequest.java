package com.senac.avaliacao.dto;

import java.util.Map;

public class PedidoRequest {

    private Integer clienteId;
    private Map<Integer, Integer> itensPedido;
    private Boolean foiFiado;

    // Getters e Setters
    public Long getClienteId() {
        return Long.valueOf(clienteId);
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Map<Integer, Integer> getItensPedido() {
        return itensPedido;
    }

    public void setItensPedido(Map<Integer, Integer> itensPedido) {
        this.itensPedido = itensPedido;
    }

    public Boolean getFoiFiado() {
        return foiFiado;
    }

    public void setFoiFiado(Boolean foiFiado) {
        this.foiFiado = foiFiado;
    }
}

