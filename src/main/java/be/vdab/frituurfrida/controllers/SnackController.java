package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.services.SnackService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("snacks")
class SnackController {

    private final SnackService snackService;
    private final char[] alfabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    public SnackController(SnackService snackService) {
        this.snackService = snackService;
    }

    @GetMapping("alfabet")
    public ModelAndView alfabet() {
        return new ModelAndView("snackAlfabet", "alfabet", alfabet);

    }

    @GetMapping("alfabet/{letter}")
    public ModelAndView eersteLetter(@PathVariable char letter) {
        return new ModelAndView("snackAlfabet", "snacksMetEersteLetter", snackService.findByBeginNaam(String.valueOf(letter)))
                .addObject("alfabet", alfabet);

    }

}
