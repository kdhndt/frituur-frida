package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.GastenboekEntry;

import java.util.List;

public interface GastenboekRepository {
    long create(GastenboekEntry entry);
    List<GastenboekEntry> findAll();
    //"long" kan je gebruiken om het datatype van een member variable te declareren, als je het datatype van je parameter echter wil omschrijven gebruik je "Long" (hoofdletter)
    //de oranje kleur van "long" moet een belletje doen rinkelen dat dit hier foutief is
    void delete(Long[] ids);
}
