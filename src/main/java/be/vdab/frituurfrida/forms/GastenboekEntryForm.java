package be.vdab.frituurfrida.forms;

import be.vdab.frituurfrida.domain.GastenboekEntry;

import java.time.LocalDate;

public class GastenboekEntryForm extends GastenboekEntry {
    //variabelen naam en bericht worden door gebruiker ingevuld
    public GastenboekEntryForm(String naam, String bericht) {
        //tijdelijke ID-waarde, _huidige_ datum voor de form
        super(0, LocalDate.now(), naam, bericht);
    }
}
