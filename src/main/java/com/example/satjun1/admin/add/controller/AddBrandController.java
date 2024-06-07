package com.example.satjun1.admin.add.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.satjun1.Brand;
import com.example.satjun1.admin.add.controller.v1.Response;
import com.example.satjun1.admin.add.service.AddService;

@RestController
public class AddBrandController {
    private final AddService service;
    public AddBrandController(AddService service) {
        this.service = service;
    }

    @PostMapping("v1/brands")
    public Response addBrand(@RequestBody Brand brand) {
        try {
            service.execute(brand);
            return new Response(200, "OK");
        } catch (Exception e) {
            return new Response(500, e.getMessage());
        }
    }
}
