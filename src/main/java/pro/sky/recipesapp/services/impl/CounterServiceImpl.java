package pro.sky.recipesapp.services.impl;

import org.springframework.stereotype.Service;
import pro.sky.recipesapp.services.CounterService;
@Service
public class CounterServiceImpl implements CounterService {

    private int counter;
    @Override
    public int getRequestCount() {
        return counter++;
    }
}
