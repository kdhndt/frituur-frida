package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Adres;
import be.vdab.frituurfrida.domain.Gemeente;
import be.vdab.frituurfrida.domain.Snack;
import be.vdab.frituurfrida.services.SnackService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Optional;

@Controller
/*@RestController*/
@RequestMapping("/")
class IndexController {
    @GetMapping
    public ModelAndView index(@CookieValue Optional<Integer> aantalBezoeken, HttpServletResponse response) {
        var openGesloten = LocalDate.now().getDayOfWeek().toString().equals("MONDAY") ?
                "gesloten" : "open";
        var modelAndView = new ModelAndView("index", "openGesloten", openGesloten);
        //object een naam geven hoeft niet? het neemt automatisch de naam object die je meegeeft
        modelAndView.addObject(
                new Adres("Gezelleplein", "4GLVA",
                        new Gemeente("Ieper", 8900)));

        var nieuweAantalBezoeken = aantalBezoeken.orElse(0) + 1;
        var cookie = new Cookie("aantalBezoeken", String.valueOf(nieuweAantalBezoeken));
        cookie.setMaxAge(31_536_000);
        cookie.setPath("/");
        response.addCookie(cookie);
        modelAndView.addObject("aantalBezoeken", nieuweAantalBezoeken);
        return modelAndView;
    }

/*    @GetMapping
    public ModelAndView index() {
        var openGesloten = LocalDate.now().getDayOfWeek().toString().equals("MONDAY") ?
                "gesloten" : "open";
        return new ModelAndView("index", "openGesloten", openGesloten);
    }*/
/*    @GetMapping
    public String index() {
        var openGesloten = LocalDate.now().getDayOfWeek().toString().equals("MONDAY") ?
                "gesloten" : "open";
        return "<!doctype html><html><title>Hallo</title><body>" + openGesloten + "</body></html>";
    }*/
}
