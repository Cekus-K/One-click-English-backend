package pl.cekus.oneclickenglish.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
class ApiController {

    @GetMapping
    public String testMethod(){
        return "test completed";
    }
}
