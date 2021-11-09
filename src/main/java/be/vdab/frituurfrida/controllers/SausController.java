package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Saus;
import be.vdab.frituurfrida.forms.BeginLetterForm;
import be.vdab.frituurfrida.forms.RaadLetterForm;
import be.vdab.frituurfrida.services.SausService;
import be.vdab.frituurfrida.sessions.RaadDeSaus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Random;

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

    //sessie
    private final RaadDeSaus raadDeSaus;

    SausController(SausService sausService, RaadDeSaus raadDeSaus) {
        this.sausService = sausService;
        this.raadDeSaus = raadDeSaus;
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

    public String randomSaus() {
        var sauzen = sausService.findAll();
        var random = new Random();
        var randomIndex = random.nextInt(sauzen.size());
        return sauzen.get(randomIndex).getNaam();
    }

    //gebruik een form om de input van de client te valideren, een simpel record met bean validators
    @GetMapping("raaddesaus/form")
    public ModelAndView raadDeSaus() {
        //als je hier naar redirect wordt het spel ge-reset
        raadDeSaus.reset(randomSaus());
        return new ModelAndView("raadDeSaus")
                .addObject("saus", raadDeSaus)
                .addObject(new RaadLetterForm(null));
    }

    //je kan niet gewoon verwijzen naar raaddesaus/form in je html, dat is een @GetMapping, code is echter identiek
    @PostMapping("raaddesaus/nieuwspel")
    public ModelAndView nieuwSpel() {
        raadDeSaus.reset(randomSaus());
        return new ModelAndView("raadDeSaus")
                .addObject("saus", raadDeSaus)
                .addObject(new RaadLetterForm(null));
    }

    @PostMapping("doeeengok")
    public ModelAndView doeEenGok(@Valid RaadLetterForm form, Errors errors) {
        if (errors.hasErrors()) {
            return new ModelAndView("raadDeSaus").addObject("saus", raadDeSaus);
        }
        raadDeSaus.doeEenGok(form.letter());
        //redirecten naar een nieuwe pagina is overzichtelijker, je kan in principe echter ook gewoon terug raadDeSaus teruggeven zoals ik eerder deed
        return new ModelAndView("redirect:/sauzen/raaddesaus/volgendegok");
    }

    @GetMapping("raaddesaus/volgendegok")
    public ModelAndView volgendeGok() {
        return new ModelAndView("raadDeSaus")
                //geupdate data van sessie, vergeet je bean geen naam te geven als je dit al eerder deed, anders kan je die niet aanspreken in HTML!
                .addObject("saus", raadDeSaus)
                //geef een nieuwe lege form mee
                .addObject(new RaadLetterForm(null));
    }



}
