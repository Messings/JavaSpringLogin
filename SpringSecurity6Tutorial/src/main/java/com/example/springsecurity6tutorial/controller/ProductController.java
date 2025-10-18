package com.example.springsecurity6tutorial.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {


    private record Product(Integer productId, String productName, double productPrice){}

        List<Product> products = new ArrayList<>(
                List.of(new Product(1, "iPhone", 999.0),
                        new Product(2, "Mac book", 1469))

        );

    @GetMapping
    public List<Product> getProducts(){
        return products;
    }

    @PostMapping
    public Product saveProduct(@RequestBody Product product){
        products.add(product);
        return product;
    }


}
