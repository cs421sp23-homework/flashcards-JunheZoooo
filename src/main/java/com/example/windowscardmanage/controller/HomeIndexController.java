package com.example.windowscardmanage.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.windowscardmanage.entity.Card;
import com.example.windowscardmanage.entity.User;
import com.example.windowscardmanage.service.CardService;
import com.example.windowscardmanage.utils.JsonResult;
import com.example.windowscardmanage.utils.ObjectUtils;
import com.example.windowscardmanage.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HomeIndexController {
    @Autowired
    private CardService cardService;

    /**
     * view the data in the card package
     */
    @PostMapping("/selectPackageCardList")
    public JsonResult<Map<String,Object>> selectPackageCardList(@RequestBody JSONObject params) {
        // card id
        Integer id = Integer.valueOf(params.get("packageId").toString());
        Map<String,Object> map = new HashMap<>();
        List<Card> list = cardService.selectCardByPackageId(id);
        map.put("packageCardList",list);
        JsonResult<Map<String,Object>> jr = new JsonResult<>(200,"get data success");
        jr.setData(map);
        return jr;
    }

    /**
     * view card
     */
    @PostMapping("/getCardByCardId")
    public JsonResult<Map<String,Object>> getCardByCardId(@RequestBody JSONObject params) {
        // card id
        Integer id = Integer.valueOf(params.get("id").toString());
        // jr
        JsonResult<Map<String,Object>> jr;
        Card card = cardService.getById(id);
        if (ObjectUtils.isNotEmpty(card)) {
            Map<String,Object> map = new HashMap<>();
            map.put("card",card);
            jr = new JsonResult<>(200,"get data success");
            jr.setData(map);
        } else {
            jr = new JsonResult<>(403,"data error");
        }
        return jr;
    }

    /**
     * delete card
     */
    @PostMapping("/deleteCardById")
    public JsonResult<Void> deleteCardById(@RequestBody JSONObject params) {
        // card id
        Integer id = Integer.valueOf(params.get("id").toString());
        // select package list
        List<Card> cards = cardService.selectCardByPackageId(id);
        if (cards.size() > 0) {
            // delete cards data
            cards.forEach(i -> cardService.removeById(i.getId()));
        }
        // 声明 jr
        JsonResult<Void> jr;
        boolean row = cardService.removeById(id);
        // 判断
        if (row) {
            jr = new JsonResult<>(200,"del success");
        } else {
            jr = new JsonResult<>(403,"del error");
        }
        return jr;
    }

    /**
     * create or update card
     */
    @PostMapping("/cardSave")
    public JsonResult<Void> cardSave(@RequestBody Card card) {
        // jr
        JsonResult<Void> jr;
        // set userId
        User user = TokenUtils.getLoginUser();
        Integer userId = user.getId();
        card.setUserId(userId);
        if (ObjectUtils.isNotEmpty(card.getId())) {
            // update time
            card.setUpdateTime(new Date());
            // update card
            boolean row = cardService.updateById(card);
            // judge
            if (row) {
                jr = new JsonResult<>(200,"update success");
            } else {
                jr = new JsonResult<>(403,"update error");
            }
        } else {
            // create time
            card.setCreateTime(new Date());
            // create card
            boolean row = cardService.save(card);
            // judge
            if (row) {
                jr = new JsonResult<>(200,"create success");
            } else {
                jr = new JsonResult<>(403,"create error");
            }
        }
        return jr;
    }

    /**
     * card package list
     */
    @GetMapping("/cardPackageList")
    public JsonResult<Map<String,Object>> cardPackageList() {
        // jr
        JsonResult<Map<String,Object>> jr = new JsonResult<>(200,"handle success");
        Map<String,Object> map = new HashMap<>();
        map.put("packageList",cardService.selectPackageList(TokenUtils.getLoginUser().getId()));
        jr.setData(map);
        return jr;
    }

    /**
     * card list
     */
    @GetMapping("/cardList")
    public JsonResult<Map<String,Object>> cardList() {
        // jr
        JsonResult<Map<String,Object>> jr = new JsonResult<>(200,"handle success");
        Map<String,Object> map = new HashMap<>();
        map.put("list",cardService.selectListByUserId(TokenUtils.getLoginUser().getId()));
        jr.setData(map);
        return jr;
    }
}
