package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Saus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

@Controller
@RequestMapping("sauzen")
class SausController {
    private final Saus[] sauzen = {
            new Saus(1, "Cocktail", new String[] {"tomaat"}),
            new Saus(2, "Mayonaise", new String[] {"ei", "citroen"}),
            new Saus(3, "Mosterd", new String[] {"mosterdzaad"}),
            new Saus(4, "Tartare", new String[] {"ei", "bieslook"}),
            new Saus(5, "Vinaigrette", new String[] {"gember", "tijm"}),

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

}
