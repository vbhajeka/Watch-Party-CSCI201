package vip.watchparty.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NewPageController {

    @GetMapping("/newpage")
    ModelAndView new_page() {
        ModelAndView mv = new ModelAndView("newpage");

        mv.addObject("id", 100);
        mv.addObject("message", "Thank you for viewing");

        return mv;
    }


}
