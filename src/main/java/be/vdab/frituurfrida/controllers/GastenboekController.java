package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.GastenboekEntry;
import be.vdab.frituurfrida.forms.GastenboekEntryForm;
import be.vdab.frituurfrida.services.GastenboekService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Optional;

@Controller
@RequestMapping("gastenboek")
public class GastenboekController {

    private final GastenboekService gastenboekService;

    public GastenboekController(GastenboekService gastenboekService) {
        this.gastenboekService = gastenboekService;
    }

    @GetMapping
    public ModelAndView toonPagina() {
        var mv = new ModelAndView("gastenboek");
        mv.addObject("entries", gastenboekService.findAll());
        return mv;
    }

    @GetMapping("toonform")
    public ModelAndView toonForm() {
        var mv = new ModelAndView("gastenboek");
        mv.addObject("entries", gastenboekService.findAll());
        mv.addObject("form", new GastenboekEntryForm("", ""));
        return mv;
    }

    @PostMapping("voegtoe")
    public ModelAndView voegToe(@Valid GastenboekEntryForm form, Errors errors) {
        if (errors.hasErrors()) {
            return new ModelAndView("gastenboek")
                    .addObject("entries", gastenboekService.findAll());
        }
        //je kan hier een GastenboekEntryForm datatype gebruiken in je create method, aangezien de form erft van GastenboekEntry
        gastenboekService.create(form);
        return new ModelAndView("redirect:/gastenboek");

    }

    @PostMapping("verwijderen")
    public ModelAndView delete(Optional<Long[]> id) {
        id.ifPresent(ids -> gastenboekService.delete(ids));
/*        if (id == null) {
            return new ModelAndView("gastenboek")
                    .addObject("entries", gastenboekService.findAll());
        }
        Arrays.stream(id).forEach(entry -> gastenboekService.delete(entry));*/
        return new ModelAndView("redirect:/gastenboek");
    }
}
