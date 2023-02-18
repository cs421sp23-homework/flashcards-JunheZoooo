package com.example.windowscardmanage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.windowscardmanage.entity.Card;
import com.example.windowscardmanage.mapper.CardMapper;
import com.example.windowscardmanage.service.CardService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl extends ServiceImpl<CardMapper, Card>
    implements CardService {

    @Override
    public List<Card> selectListByUserId(Integer userId) {
        LambdaQueryWrapper<Card> lqw = Wrappers.lambdaQuery();
        lqw.eq(Card::getUserId,userId);
        lqw.eq(Card::getCardOfIs,0);
        return list(lqw);
    }

    @Override
    public List<Card> selectPackageList(Integer userId) {
        LambdaQueryWrapper<Card> lqw = Wrappers.lambdaQuery();
        lqw.eq(Card::getUserId,userId);
        lqw.eq(Card::getCardType,1);
        return list(lqw);
    }

    @Override
    public List<Card> selectCardByPackageId(Integer packageId) {
        LambdaQueryWrapper<Card> lqw = Wrappers.lambdaQuery();
        lqw.eq(Card::getCardOfId,packageId);
        return list(lqw);
    }
}




