package com.alex.springbaeldung.controllers;

import org.apache.coyote.Response;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import com.alex.springbaeldung.persistence.models.Greeting;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FormController {

    @GetMapping("/form")
    public ModelAndView greetingForm() {

        return new ModelAndView("form");
    }

    @PostMapping(path="/form",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView greetingSubmit(@RequestBody MultiValueMap<String, String> formData) {

        ModelAndView mv = new ModelAndView("result");

//        mv.addObject("id", formData.get("id"));
//        mv.addObject("message", formData.get("Message"));

        System.out.println(formData);
        return mv;
    }

}
