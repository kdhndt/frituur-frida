package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Saus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("sauzen")
class SausController {
    private final Saus[] sauzen = {
            new Saus(1, "cocktail", new String[] {"tomaat"}),
            new Saus(2, "mayonaise", new String[] {"ei", "citroen"}),
            new Saus(3, "mosterd", new String[] {"mosterdzaad"}),
            new Saus(4, "tartare", new String[] {"ei", "bieslook"}),
            new Saus(5, "vinaigrette", new String[] {"gember", "tijm"}),

    };
    @GetMapping
    public ModelAndView sauzen() {
        return new ModelAndView("sauzen", "sauzen", sauzen);
    }

    //verwijst naar sauzen/{nummer}
    //onze hyperlinks in sauzen.html verwijzen naar dit soort pagina's (e.g. localhost:8080/sauzen/2)
    //deze suffix wordt automatisch toegevoegd aan de root van deze controller, dus de sauzen pagina
    @GetMapping("{nummer}")
    public ModelAndView saus(@PathVariable long nummer) {
        var modelAndView = new ModelAndView("saus");
        Arrays.stream(sauzen).filter(saus -> saus.getNummer() == nummer).findFirst()
                .ifPresent(saus -> modelAndView.addObject(saus));
        return modelAndView;
    }

/*    public List<String> lettersVanAlfabet() {
        List<String> alfabet = new ArrayList<>();
        char c;
        for (c = 'A'; c <= 'Z'; ++c) {
            alfabet.add(String.valueOf(c));
        }
        return alfabet;
    }*/

    private final char[] alfabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    @GetMapping("alfabet")
    public ModelAndView toonPagina() {
        return new ModelAndView("alfabet", "alfabet", alfabet);
    }

    private List<Saus> sauzenDieBeginnenMet(char letter) {
        return Arrays.stream(sauzen)
                .filter(saus -> saus.getNaam().charAt(0) == letter)
                .toList();
    }

    @GetMapping("alfabet/{letter}")
    public ModelAndView sauzenBeginnendMet(@PathVariable char letter) {
        return new ModelAndView("alfabet", "sauzenVanEenLetter", sauzenDieBeginnenMet(letter))
                .addObject("alfabet", alfabet);
    }

}
