package com.saga.order.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.UUID;
@Entity(name = "ProdutoDTO")
public class ProdutoDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID codProduto;

    public com.saga.order.model.OrderDTO getOrderDTO() {
        return OrderDTO;
    }

    public void setOrderDTO(com.saga.order.model.OrderDTO orderDTO) {
        OrderDTO = orderDTO;
    }

    @ManyToOne
    @JoinColumn(name="OrderDTO_codPedido")
    @JsonProperty
    private OrderDTO OrderDTO;

    public ProdutoDTO() {
    }

    @Column
    @JsonProperty("descricao")
    private  String descProduto;

    @Column
    private float preco;

    @Column
    private int quantidade;

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public UUID getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(UUID codProduto) {
        this.codProduto = codProduto;
    }

    public String getDescProduto() {
        return descProduto;
    }

    public void setDescProduto(String descProduto) {
        this.descProduto = descProduto;
    }


}
