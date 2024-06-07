package com.example.satjun1.customer.first.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.satjun1.customer.first.controller.v1.Response;
import com.example.satjun1.customer.first.service.AllCategoryService;
import com.example.satjun1.storage.keyvalue.BrandLowHighPricePair;

@RestController
public class AllCategoryController {
    private final AllCategoryService service;

    public AllCategoryController(AllCategoryService service) {
        this.service = service;
    }

    @GetMapping("v1/categories/cheapest")
    public Response getAllCategories() {
        try {
            List<BrandLowHighPricePair> pairs = service.execute();
            return Response.from(pairs);
        } catch (Exception e) {
            return Response.error();
        }
    }
}
