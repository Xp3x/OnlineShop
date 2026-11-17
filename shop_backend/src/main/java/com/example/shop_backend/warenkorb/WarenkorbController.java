package com.example.shop_backend.warenkorb;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/warenkorb")

public class WarenkorbController {
    private final WarenkorbRepository repo;

    public WarenkorbController(WarenkorbRepository repo) {
        this.repo = repo;
    }



    @PostMapping
    public Warenkorb createItem(@RequestBody Warenkorb warenkorb, @RequestHeader("X-Client-Id") String clientId ) {
        Warenkorb w = new Warenkorb();
        w.setSize(warenkorb.getSize());
        w.setProductId(warenkorb.getProductId());
        w.setClientId(clientId);
        return  repo.save(w) ;
    }


    @GetMapping
    public List<Warenkorb> findAll(@RequestHeader("X-Client-Id") String clientId) {

        return repo.findByClientId(clientId);
    }




    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id, @RequestHeader("X-Client-Id") String clientId) {
        Warenkorb w = repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(w.getClientId() == null || !clientId.equals(w.getClientId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        repo.delete(w);
    }








}
