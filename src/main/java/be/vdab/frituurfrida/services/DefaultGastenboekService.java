package be.vdab.frituurfrida.services;

import be.vdab.frituurfrida.domain.GastenboekEntry;
import be.vdab.frituurfrida.repositories.GastenboekRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DefaultGastenboekService implements GastenboekService {

    private final GastenboekRepository gastenboekRepository;

    public DefaultGastenboekService(GastenboekRepository gastenboekRepository) {
        this.gastenboekRepository = gastenboekRepository;
    }

    @Override
    public long create(GastenboekEntry entry) {
        return gastenboekRepository.create(entry);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GastenboekEntry> findAll() {
        return gastenboekRepository.findAll();
    }

    @Override
    public void delete(Long[] ids) {
        gastenboekRepository.delete(ids);
    }
}
