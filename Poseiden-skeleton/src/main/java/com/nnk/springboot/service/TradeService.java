package com.nnk.springboot.service;

import java.util.List;

import com.nnk.springboot.domain.Trade;

public interface TradeService {

    void createTrade(Trade trade);

    void updateTrade(Integer id, Trade trade);

    void deleteTrade(Integer id);

    Trade getTradeById(Integer id);

    List<Trade> getAllTrades();
}
