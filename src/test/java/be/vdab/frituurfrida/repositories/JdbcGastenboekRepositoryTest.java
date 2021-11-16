package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.GastenboekEntry;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.assertj.core.api.Assertions.*;


import java.time.LocalDate;
import java.util.Comparator;

@JdbcTest
//dummy repository to access its methods
@Import(JdbcGastenboekRepository.class)
//dummy sql to mimic a database, gebruik geen accolade als het maar om een bestand gaat
@Sql("/insertGastenboek.sql")
class JdbcGastenboekRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    //of "GASTENBOEK", dit verwijst naar de naam van je SQL tafel
    private static final String GASTENBOEKENTRIES = "gastenboekentries";
    private final JdbcGastenboekRepository repository;

    JdbcGastenboekRepositoryTest(JdbcGastenboekRepository repository) {
        this.repository = repository;
    }

    @Test
    void create() {
        var berichtId = repository.create(new GastenboekEntry(0, LocalDate.now(), "test3", "test3"));
        assertThat(berichtId).isPositive();
        assertThat(countRowsInTableWhere(GASTENBOEKENTRIES, "berichtId = " + berichtId)).isOne();
    }

    @Test
    void findAll() {
        assertThat(repository.findAll())
                .hasSize(countRowsInTable(GASTENBOEKENTRIES))
                .extracting(GastenboekEntry::getDatum)
                .isSortedAccordingTo(Comparator.reverseOrder());

    }

    private long idVanTestEntry1() {
        //"Long.class" vormt resultaat om naar een object?
        return jdbcTemplate.queryForObject("select berichtid from gastenboekentries where naam='test1'", Long.class);
    }

    private long idVanTestEntry2() {
        return jdbcTemplate.queryForObject("select berichtid from gastenboekentries where naam='test2'", Long.class);
    }

    @Test
    void delete() {
        var id1 = idVanTestEntry1();
        var id2 = idVanTestEntry2();
        repository.delete(new Long[]{id1, id2});
        assertThat(countRowsInTableWhere(GASTENBOEKENTRIES, "berichtId in (" + id1 + ',' + id2 + ')')).isZero();
    }

    @Test
    void eenLegeArrayDeletenGeeftGeenException() {
        repository.delete(new Long[]{});
    }
}