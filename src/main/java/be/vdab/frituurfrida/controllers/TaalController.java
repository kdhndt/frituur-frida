package be.vdab.frituurfrida.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("taal")
class TaalController {
    @GetMapping
    public ModelAndView nederlands(@RequestHeader("Accept-Language") String taal) {
        return new ModelAndView("taal", "nederlandsTalig", taal.startsWith("nl"));
    }
}
