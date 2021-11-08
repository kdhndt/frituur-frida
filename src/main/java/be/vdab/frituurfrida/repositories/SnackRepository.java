package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.Snack;
import be.vdab.frituurfrida.dto.VerkochtAantalPerSnack;

import java.util.List;
import java.util.Optional;

public interface SnackRepository {
    Optional<Snack> findById(long id);
    void update(Snack snack);
    List<Snack> findByBeginNaam(String beginNaam);

    List<VerkochtAantalPerSnack> findVerkochteAantallenPerSnack();
    List<Snack> findAll();

}
