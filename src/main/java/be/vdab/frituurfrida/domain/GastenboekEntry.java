package be.vdab.frituurfrida.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class GastenboekEntry {
    /*classes can not inherit from records, so make a regular class (domain here) so that GastenboekEntryForm can inherit from it*/

    private final long berichtid;
    //datum in short style, toon het tijd deel niet
    @DateTimeFormat (style = "S-")
    private final LocalDate datum;
    private final String naam;
    private final String bericht;

    public GastenboekEntry(long berichtid, LocalDate datum, String naam, String bericht) {
        this.berichtid = berichtid;
        this.datum = datum;
        this.naam = naam;
        this.bericht = bericht;
    }

    public long getId() {
        return berichtid;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public String getNaam() {
        return naam;
    }

    public String getBericht() {
        return bericht;
    }
}