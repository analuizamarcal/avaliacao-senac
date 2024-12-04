package com.senac.avaliacao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false) // Garantindo que o cliente seja obrigatório
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true) // Adicionando orphanRemoval
    @JsonIgnore
    private List<ItemPedido> itensPedido = new ArrayList<>();

    private BigDecimal total;

    private boolean foiFiado;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;  // Adicionado o campo de data e hora


    // Método para adicionar um item ao pedido
    public void addItemPedido(ItemPedido itemPedido) {
        itemPedido.setPedido(this); // Definir o pedido no item
        this.itensPedido.add(itemPedido); // Adicionar o item à lista
    }

    // Getters e setters
    public List<ItemPedido> getItensPedido() {
        return itensPedido;
    }

    public void setItensPedido(List<ItemPedido> itensPedido) {
        this.itensPedido = itensPedido;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public boolean getFoiFiado() {
        return foiFiado;
    }

    public void setFoiFiado(boolean foiFiado) {
        this.foiFiado = foiFiado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public boolean isFoiFiado() {
        return foiFiado;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}
