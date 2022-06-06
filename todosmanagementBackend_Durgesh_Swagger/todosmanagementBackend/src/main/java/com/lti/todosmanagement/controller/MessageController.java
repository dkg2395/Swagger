package com.lti.todosmanagement.controller;

import com.lti.todosmanagement.entity.message;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MessageController {

    @GetMapping("/message")
    public  String getMessage(){
        return "Welcome to Dotos management.....";
    }

    @GetMapping("/welcomeMessage")
    public message getwelcomeMessage(){
      //  throw new RuntimeException("please contact on 33445566");
       return new message("Welcome to Our Dotos management........");
    }
    @GetMapping("/welcomeMessage/{name}")
    public message getwelcomeMessage(@PathVariable String name){
        //  throw new RuntimeException("please contact on 33445566");
        return new message(String.format("Hello  %s ", name));
    }

}
