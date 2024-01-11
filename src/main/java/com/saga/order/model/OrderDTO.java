package com.saga.order.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "OrderDTO")
public class OrderDTO implements Serializable {

    @Id
    private UUID codPedido;

    public OrderDTO() {
        // TODO document why this constructor is empty
    }

    @GeneratedValue(strategy = GenerationType.AUTO)
    private  UUID codCliente;
    //TODO CODCLIENTE NOT GENERATED

    @OneToMany(mappedBy = "OrderDTO" , cascade = CascadeType.ALL)
    @JsonIgnoreProperties("produtos")
    private List<ProdutoDTO> produtos;

    @Column
    private float totalPreco;

    @Column
    private  String statusPedido;

    public String getStatusPedido() {
            return statusPedido;
    }
    public void setStatusPedido(String statusPedido) {
        this.statusPedido = statusPedido;
    }

    public UUID getCodPedido() {
        return codPedido;
    }

    public void setCodPedido(UUID codPedido) {
        this.codPedido = codPedido;
    }

    public UUID getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(UUID codCliente) {
        this.codCliente = codCliente;
    }

    public List<ProdutoDTO> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoDTO> produtos) {
        this.produtos = produtos;
    }

    public float getTotalPreco() {
        return totalPreco;
    }

    public void setTotalPreco(float totalPreco) {
        this.totalPreco = totalPreco;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "Codpedido=" + codPedido +
                ", Codcliente=" + codCliente +
                ", produtos=" + produtos +
                ", totalPreco=" + totalPreco +
                '}';
    }
}
