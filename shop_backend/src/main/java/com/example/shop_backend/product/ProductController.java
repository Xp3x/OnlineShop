package com.example.shop_backend.product;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController // Sagt Spring: "Diese Klasse beantwortet Web-Anfragen"
@RequestMapping("/api/products") // Basis-URL für alles in diesem Controller

public class ProductController {

    private final ProductRepository repo;

    // Konstruktor-Injektion: Spring gibt uns automatisch ein Repository-Objekt
    public ProductController(ProductRepository repo) {
        this.repo = repo;
    }

    // GET-Anfrage: /api/products
    @GetMapping
    public List<Product> getAllProducts() {
        return repo.findAll(); // holt alle Produkte aus der DB
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product save(@Valid @RequestBody Product product) {
        return repo.save(product);
    }


    @GetMapping("/{id}")
    public Product findById(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    @PutMapping("/{id}")
    public Product update(@PathVariable Long id,@Valid  @RequestBody Product product) {
        return repo.findById(id)
                .map(product1 -> {
                    product1.setName(product.getName());
                    product1.setPrice(product.getPrice());
                    product1.setBrand(product.getBrand());
                    return repo.save(product1); })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
   @DeleteMapping("/{id}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        if(!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        repo.deleteById(id);
   }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<Product> patch(@PathVariable Long id,
                                         @RequestBody Product dto) {

        return repo.findById(id)
                .map(p -> {
                    if (dto.getName()!= null){
                        p.setName(dto.getName());
                    }
                    if (dto.getBrand() != null) {
                        p.setBrand(dto.getBrand());
                    }
                    if (dto.getPrice() != null) {
                        p.setPrice(dto.getPrice());
                    }
                    if (dto.getImageUrl() != null) {
                        p.setImageUrl(dto.getImageUrl());
                    }
                    if (dto.getSurface() != null) {
                        p.setSurface(dto.getSurface());
                    }
                    if (dto.getGroesse() != null) {
                        p.setGroesse(dto.getGroesse());
                    }
                    if (dto.getBeschreibung() != null) {
                        p.setBeschreibung(dto.getBeschreibung());
                    }
                    if (dto.getAngebot() != null) {
                        p.setAngebot(dto.getAngebot());
                    }

                    p.setStack_heel_mm(dto.getStack_heel_mm());
                    p.setStack_forefoot_mm(dto.getStack_forefoot_mm());
                    p.setDrop_mm(dto.getDrop_mm());
                    p.setWeight_g(dto.getWeight_g());



                    return ResponseEntity.ok(repo.save(p));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
