package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Snack;
import be.vdab.frituurfrida.exceptions.SnackNietGevondenException;
import be.vdab.frituurfrida.forms.BeginLetterForm;
import be.vdab.frituurfrida.services.SnackService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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
        return new ModelAndView("snackAlfabet", "alfabet", alfabet)
                .addObject("alleSnacks", snackService.findAll());

    }

    @GetMapping("alfabet/{letter}")
    public ModelAndView eersteLetter(@PathVariable char letter) {
        return new ModelAndView("snackAlfabet", "snacksMetEersteLetter", snackService.findByBeginNaam(String.valueOf(letter)))
                .addObject("alfabet", alfabet);

    }

    @GetMapping("dagverkopen")
    public ModelAndView dagverkopen() {
        return new ModelAndView("dagverkopen", "aantalVerkochteSnacks", snackService.findAantalVerkochteSnacks());
    }

    @GetMapping("beginletters/form")
    public ModelAndView beginLettersForm() {
        return new ModelAndView("beginletters")
                //naamgeving van model is hier niet nodig, laat dit zelf genereren, m.a.w BeginLetterForm wordt ${beginLetterForm) op je HTML pagina
                .addObject(new BeginLetterForm(""));
    }

    @GetMapping("beginletters")
    public ModelAndView beginletters(@Valid BeginLetterForm form, Errors errors) {
        var modelAndView = new ModelAndView("beginletters");
        if (errors.hasErrors()) {
            return modelAndView;
        }
        return modelAndView.addObject("snacks", snackService.findByBeginNaam(form.beginletters()))
                .addObject("alleSnacks", snackService.findAll());
    }

    @GetMapping("{id}/wijzigen/form")
    //je hebt een _BESTAANDE_ snack nodig om die te kunnen wijzigen
    //we nemen dus de id van de snack mee in onze url zodat we de snack in kwestie kunnen opzoeken
    public ModelAndView wijzigenForm(@PathVariable long id) {
        var modelAndView = new ModelAndView("wijzigSnack");
        snackService.findById(id).ifPresent(snack -> modelAndView.addObject(snack));
        return modelAndView;
    }

    @PostMapping("wijzigen")
    public String wijzigen(@Valid Snack snack, Errors errors, RedirectAttributes redirect) {
        if (errors.hasErrors()) {
            return "wijzigen";
        }
        try {
            snackService.update(snack);
            return "redirect:/";
        } catch (SnackNietGevondenException ex) {
            redirect.addAttribute("snackNietGevonden", snack.getId());
            return "redirect:/";
        }

    }
}
