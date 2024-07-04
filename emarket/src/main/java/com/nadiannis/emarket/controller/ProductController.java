package com.nadiannis.emarket.controller;

import com.nadiannis.emarket.model.Product;
import com.nadiannis.emarket.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepo;

    @PostMapping(path = "/add")
    public @ResponseBody String  addProduct(@RequestBody Product product) {
        productRepo.save(product);
        return "Saved";
    }

    @GetMapping(path = "/findByPrice")
    public @ResponseBody List<Product> findProduct(@RequestParam Double price, @RequestParam Integer page, @RequestParam Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepo.findAllByPrice(price, pageable);
    }

    @GetMapping(path = "/findByPriceSort")
    public @ResponseBody List<Product> findProductSort(@RequestParam Double price, @RequestParam Integer page, @RequestParam Integer size, String sortColumn) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortColumn).descending());
        return productRepo.findAllByPrice(price, pageable);
    }

}
