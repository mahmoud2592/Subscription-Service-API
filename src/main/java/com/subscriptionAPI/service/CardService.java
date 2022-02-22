package com.subscriptionAPI.service;

import com.subscriptionAPI.model.Card;
import com.subscriptionAPI.model.IssuerEnum;
import com.subscriptionAPI.repository.CardRepository;
import com.subscriptionAPI.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.subscriptionAPI.exception.ResourceNotFoundException;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class CardService {
    @Autowired
    private CardRepository repository;

    @Autowired
    private CustomerRepository customerRepository;

    public Card saveDefault(long card_id){
        Card card = repository.findById(card_id);
        List<Card> cards = repository.findByCustomerId(card.getCustomer().getId());
        int k = 0;
        for(int i=0;i<cards.size();i++){
            if(cards.get(i).isIs_default()==true){
                cards.get(i).setIs_default(false);
            }
            if(cards.get(i).getId()==card.getId()){
                cards.get(i).setIs_default(true);
                k = i;
            }
        }
        repository.saveAll(cards);
        return cards.get(k);
    }

    public Card saveCard(Card card, String username){
        List<Card> cards = repository.findByCustomerId(customerRepository.findByUsername(username).getId());
        card.setIssuer(card.getIssuer().toLowerCase(Locale.ROOT));
        if(!card.getIssuer().equals(IssuerEnum.type.MASTERCARD.name().toLowerCase(Locale.ROOT))){
            throw new ResourceNotFoundException("only accepts Mastercard issuer's cards" );
        }else if(cards.size()>=3){
            throw new ResourceNotFoundException("you have maximum number of cards" );
        }else{
            if(card.isIs_default()==true){
                for(int i=0;i<cards.size();i++){
                    if(cards.get(i).isIs_default()==true){
                        cards.get(i).setIs_default(false);
                    }
                    if(cards.get(i).getId()==card.getId()){
                        cards.get(i).setIs_default(true);
                    }
                }
            }
        }
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        card.setCustomer(customerRepository.findByUsername(username));
        card.setCreated_at(timestamp);
        return repository.saveAndFlush(card);
    }

    public List<Card> getCardsByUsername(String username){
        return repository.findByCustomerId(customerRepository.findByUsername(username).getId());
    }

    public Card getCardByUserId(Long id){
        return repository.findById(id).orElse(null);
    }

    public String DeleteCardById(Long id){
        repository.deleteById(id);
        return "Deleted Card Id: " + id;
    }

}
