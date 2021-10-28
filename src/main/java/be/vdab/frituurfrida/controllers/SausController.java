package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Saus;
import be.vdab.frituurfrida.services.SausService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("sauzen")
class SausController {
    private final Saus[] sauzen = {
            new Saus(1, "cocktail", new String[]{"tomaat"}),
            new Saus(2, "mayonaise", new String[]{"ei", "citroen"}),
            new Saus(3, "mosterd", new String[]{"mosterdzaad"}),
            new Saus(4, "tartare", new String[]{"ei", "bieslook"}),
            new Saus(5, "vinaigrette", new String[]{"gember", "tijm"}),

    };
    private final char[] alfabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private final SausService sausService;

    SausController(SausService sausService) {
        this.sausService = sausService;
    }

    @GetMapping
    public ModelAndView sauzen() {
        return new ModelAndView("sauzen", "sauzen", sausService.findAll());
    }

    //verwijst naar sauzen/{nummer}
    //onze hyperlinks in sauzen.html verwijzen naar dit soort pagina's (e.g. localhost:8080/sauzen/2)
    //deze suffix wordt automatisch toegevoegd aan de root van deze controller, dus de sauzen pagina
    @GetMapping("{nummer}")
    public ModelAndView saus(@PathVariable long nummer) {
        var modelAndView = new ModelAndView("saus");
        sausService.findById(nummer).ifPresent(modelAndView::addObject);
/*        Arrays.stream(sauzen).filter(saus -> saus.getNummer() == nummer).findFirst()
                .ifPresent(saus -> modelAndView.addObject(saus));*/
        return modelAndView;
    }

    @GetMapping("alfabet")
    public ModelAndView alfabet() {
        return new ModelAndView("alfabet", "alfabet", alfabet);
    }

/*    private List<Saus> sauzenDieBeginnenMet(char letter) {
        return Arrays.stream(sauzen)
                .filter(saus -> saus.getNaam().charAt(0) == letter)
                .toList();
    }*/

    @GetMapping("alfabet/{letter}")
    public ModelAndView sauzenBeginnendMet(@PathVariable char letter) {
        return new ModelAndView("alfabet", "sauzenVanEenLetter",
                sausService.findByNaamBegintMet(letter))
                .addObject("alfabet", alfabet);
    }

}
