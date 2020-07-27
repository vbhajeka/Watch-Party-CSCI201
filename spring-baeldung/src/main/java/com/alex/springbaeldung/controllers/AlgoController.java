package com.alex.springbaeldung.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AlgoController {

    @GetMapping("/algoviz")
    public ModelAndView algoviz() {
        return new ModelAndView("algo");
    }
}
