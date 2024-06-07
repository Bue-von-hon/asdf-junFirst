package com.example.satjun1.admin.delete.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.satjun1.Brand;
import com.example.satjun1.admin.add.controller.v1.Response;
import com.example.satjun1.admin.delete.service.DeleteService;

@RestController
public class DeleteController {
    private final DeleteService service;
    public DeleteController(DeleteService service) {
        this.service = service;
    }

    @DeleteMapping("v1/brands")
    public Response deleteBrand(@RequestBody Brand brand) {
        try {
            service.execute(brand.brand());
            return new Response(200, "OK");
        } catch (Exception e) {
            return new Response(500, e.getMessage());
        }
    }
}
