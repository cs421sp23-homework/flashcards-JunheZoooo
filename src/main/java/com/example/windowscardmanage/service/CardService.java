package com.example.windowscardmanage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.windowscardmanage.entity.Card;

import java.util.List;

/**
* card Service interface
*/
public interface CardService extends IService<Card> {
    /**
     * get user create card
     */
    List<Card> selectListByUserId(Integer userId);
    /**
     * get card package
     */
    List<Card> selectPackageList(Integer userId);
    /**
     * get card in card package
     */
    List<Card> selectCardByPackageId(Integer packageId);
}
