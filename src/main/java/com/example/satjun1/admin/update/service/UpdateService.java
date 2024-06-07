package com.example.satjun1.admin.update.service;

import org.springframework.stereotype.Service;

import com.example.satjun1.Brand;
import com.example.satjun1.admin.add.service.AddService;
import com.example.satjun1.admin.delete.service.DeleteService;

@Service
public class UpdateService {
    private final DeleteService deleteService;
    private final AddService addService;

    public UpdateService(DeleteService deleteService, AddService addService) {
        this.deleteService = deleteService;
        this.addService = addService;
    }

    /**
     * 업데이트는 삭제 후 삽입.
     * @param targetBrand
     */
    public void execute(Brand targetBrand) {
        deleteService.execute(targetBrand.brand());
        addService.execute(targetBrand);
    }
}
