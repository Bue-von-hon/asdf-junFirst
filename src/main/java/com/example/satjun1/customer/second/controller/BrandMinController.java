package com.example.satjun1.customer.second.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.satjun1.storage.rank.LowestPriceListByCategory;
import com.example.satjun1.customer.second.service.BrandMinService;
import com.example.satjun1.customer.second.controller.v1.Response;

@RestController
public class BrandMinController {
    private final BrandMinService service;

    public BrandMinController(BrandMinService service) {
        this.service = service;
    }

    @GetMapping("v1/brands/cheapest-total")
    public Response getMinMax() {
        try {
            LowestPriceListByCategory data = service.execute();
            return Response.from(data);
        } catch (Exception e) {
            return Response.error();
        }
    }
}
