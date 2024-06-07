package com.example.satjun1.admin.update.controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.satjun1.Brand;
import com.example.satjun1.admin.add.controller.v1.Response;
import com.example.satjun1.admin.update.service.UpdateService;

@RestController
public class UpdateController {
    private final UpdateService service;
    public UpdateController(UpdateService service) {
        this.service = service;
    }

    @PutMapping("v1/brands")
    public Response updateBrand(@RequestBody Brand brand) {
        try {
            service.execute(brand);
            return new Response(200, "OK");
        } catch (Exception e) {
            return new Response(500, e.getMessage());
        }
    }
}
