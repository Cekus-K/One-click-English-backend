package pl.cekus.oneclickenglish.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ApiController {

    @GetMapping("/api/test")
    public String testMethod(){
        return "test completed";
    }
}
