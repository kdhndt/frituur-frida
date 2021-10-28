package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.Saus;

import java.io.IOException;
import java.util.List;

public interface SausRepository {
    List<Saus> findAll();
}
