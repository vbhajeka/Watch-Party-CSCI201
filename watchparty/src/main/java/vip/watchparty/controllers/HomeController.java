package vip.watchparty.controllers;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HomeController {

    @GetMapping("/")
    public ModelAndView redirect_from_index() {
        return new ModelAndView("redirect:/room/");
    }
}