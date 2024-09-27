package com.nnk.springboot.service;

import java.util.List;

import com.nnk.springboot.domain.BidList;

public interface BidListService {

    void createBidList(BidList bid);

    void deleteBidList(Integer id);

    void updateBidList(Integer id, BidList bid);

    BidList getBidListById(Integer id);

    List<BidList> getAllBidList();

}
