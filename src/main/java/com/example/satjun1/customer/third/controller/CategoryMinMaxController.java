package com.example.satjun1.customer.third.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.satjun1.customer.third.controller.v1.Response;
import com.example.satjun1.customer.third.service.CategoryMinMaxService;
import com.example.satjun1.storage.keyvalue.BrandLowHighPricePair;

@RestController
public class CategoryMinMaxController {
    private final CategoryMinMaxService service;

    public CategoryMinMaxController(CategoryMinMaxService service) {
        this.service = service;
    }

    @GetMapping("v1/category/minmax")
    public Response getMinMax(@RequestParam String name) {
        final BrandLowHighPricePair data = service.execute(name);
        return Response.from(data, name);
    }
}
