package be.vdab.frituurfrida.services;

import be.vdab.frituurfrida.domain.Snack;
import be.vdab.frituurfrida.repositories.SnackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DefaultSnackService implements SnackService {

    private final SnackRepository snackRepository;

    public DefaultSnackService(SnackRepository snackRepository) {
        this.snackRepository = snackRepository;
    }

    @Override
    public void update(Snack snack) {
        snackRepository.update(snack);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Snack> findByBeginNaam(String beginNaam) {
        return snackRepository.findByBeginNaam(beginNaam);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Snack> findById(long id) {
        return snackRepository.findById(id);
    }


}
