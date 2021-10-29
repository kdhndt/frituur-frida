package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.Snack;
import be.vdab.frituurfrida.exceptions.SnackNietGevondenException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;


@JdbcTest
@Import(JdbcSnackRepository.class)
@Sql("/insertSnacks.sql")
class JdbcSnackRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    private static final String SNACKS = "snacks";
    private final JdbcSnackRepository repository;

    JdbcSnackRepositoryTest(JdbcSnackRepository repository) {
        this.repository = repository;
    }

    private long idVanTestSnack() {
        return jdbcTemplate.queryForObject(
                "select id from snacks where naam='test'", Long.class);
    }

    @Test void update() {
        var id = idVanTestSnack();
        //we maken een snack object, maar de volgorde van de getters in de update van de repository kwam niet overeen
        //met de volgorde van de vraagtekens in het sql statement
        var snack = new Snack(id, "test", BigDecimal.TEN);
        repository.update(snack);
        assertThat(countRowsInTableWhere(SNACKS, "prijs=10 and id=" + id)).isOne();
    }

    @Test
    void updateOnbestaandeSnack() {
        assertThatExceptionOfType(SnackNietGevondenException.class)
                .isThrownBy(() -> repository.update(new Snack(-1, "test", BigDecimal.TEN)));
    }

    @Test
    void findById() {
        assertThat(repository.findById(idVanTestSnack()))
                .hasValueSatisfying(pizza -> assertThat(pizza.getNaam()).isEqualTo("test"));
    }

    @Test
    void findByBeginNaam() {
        var eersteLetter = "K";
        assertThat(repository.findByBeginNaam(eersteLetter))
                .hasSize(countRowsInTableWhere(SNACKS, "naam like 'K%'"))
                .extracting(Snack::getNaam)
                .allSatisfy(snack -> assertThat(snack).startsWith("K"))
                .isSorted();

    }
}