package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trade")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer TradeId;
    String account;
    String type;
    Double buyQuantity;
    Double sellQuantity;
    Double buyPrice;
    Double sellPrice;
    String benchmark;
    Timestamp tradeDate;
    String security;
    String status;
    String trader;
    String book;
    String creationName;
    @CreationTimestamp
    Timestamp creationDate;
    String revisionName;
    Timestamp revisionDate;
    String dealName;
    String dealType;
    String sourceListId;
    String side;

    public Trade(String account, String type) {
        this.account = account;
        this.type = type;
    }

    public Integer getId() {
        return TradeId;
    }

    public void setId(Integer id) {
        this.TradeId = id;
    }

    public Integer getTradeId() {
        return TradeId;
    }

    public void setTradeId(Integer id) {
        this.TradeId = id;
    }
}
