package pro.sky.recipesapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.recipesapp.services.CounterService;

@RestController
public class FirstController {

    private CounterService counterService;

    public FirstController(CounterService counterService) {
        this.counterService = counterService;
    }

    @GetMapping
    public String helloWeb() {
        return "hello web!";
    }
    @GetMapping("/counter")

    public String showCounter() {
        return "Количество запросов: " + counterService.getRequestCount();
    }
}
