package com.example.satjun1;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String main() {
        return "main";
    }

    @GetMapping("/resources/app.js")
    public ResponseEntity<Resource> getAppJs() {
        Resource appJs = new ClassPathResource("static/app.js");
        return ResponseEntity.ok()
                             .contentType(MediaType.parseMediaType("application/javascript"))
                             .body(appJs);
    }
}
