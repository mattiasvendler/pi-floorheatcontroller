package com.vendler.piflorheatcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;

@RestController
public class FloorheatController {
    @Autowired
    private PiPinHandler piPinHandler;
    private ExecutorService executor;

    public FloorheatController() {
        executor = Executors.newFixedThreadPool(2);
    }

    @GetMapping("/test")
    public String testGet() {
        System.out.println("testGet");
        return "Test";
    }

    @PutMapping("/test")
    public void testPut() {
        System.out.println("Test put");
    }

    @GetMapping("/{pin}/high")
    public void setPinHigh(@PathVariable int pin) {
        executor.submit(() -> {
            piPinHandler.high(pin);
        });
    }

    @GetMapping("/{pin}/low")
    public void setPinLow(@PathVariable int pin) {
        executor.submit(() -> {
            piPinHandler.low(pin);
        });
    }

}
