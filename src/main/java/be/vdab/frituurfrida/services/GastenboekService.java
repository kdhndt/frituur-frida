package be.vdab.frituurfrida.services;

import be.vdab.frituurfrida.domain.GastenboekEntry;

import java.util.List;

public interface GastenboekService {
    public long create(GastenboekEntry entry);
    public List<GastenboekEntry> findAll();
    public void delete(Long[] ids);
}
