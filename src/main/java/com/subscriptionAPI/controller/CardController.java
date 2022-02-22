package com.subscriptionAPI.controller;

import com.subscriptionAPI.model.Card;
import com.subscriptionAPI.service.CardService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;

@RestController
public class CardController {
    @Autowired
    CardService cardService;

    @PostMapping("/addCard")
    public Card addCard(@ApiParam(value = "created_at and is_deleted they are hidden values which are not mandatory to send") @RequestBody Card card) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return cardService.saveCard(card, username);
    }

    @GetMapping("/cards")
    public List<Card> findAllCards() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return cardService.getCardsByUsername(username);
    }
    @GetMapping("/getCard/{id}")
    public Card findCardById(@PathVariable Long id) {
        return cardService.getCardByUserId(id);
    }

    @PostMapping("/addDefault")
    public Card addDefault(@PathVariable Long card_id) {
        return  cardService.saveDefault(card_id);
    }
}
