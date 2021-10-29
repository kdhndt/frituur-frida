package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.Snack;
import be.vdab.frituurfrida.exceptions.SnackNietGevondenException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcSnackRepository implements SnackRepository {

    private final JdbcTemplate template;

    private final RowMapper<Snack> snackMapper = (result, rowNum) -> new Snack(result.getLong("id"),
            result.getString("naam"),
            result.getBigDecimal("prijs"));

    public JdbcSnackRepository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Optional<Snack> findById(long id) {
        try {
            var sql = """
                select id, naam, prijs
                from snacks
                where id = ?
                """;
            //er kan maar _1_ waarde gereturned worden, dus gebruik queryForObject
            return Optional.of(template.queryForObject(sql, snackMapper, id));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public void update(Snack snack) {
        var sql = """
                update snacks
                set naam = ?, prijs = ?
                where id = ?
                """;
        //Zorg ervoor dat de volgorde van de getters overeenkomt met de volgorde van de ? in je sql statement, zo voorkom je later problemen bij het testen
        if (template.update(sql, snack.getNaam(), snack.getPrijs(), snack.getId()) == 0) {
            throw new SnackNietGevondenException();
        }
    }

    @Override
    public List<Snack> findByBeginNaam(String beginNaam) {
        var sql = """
                select id, naam, prijs
                from snacks
                where naam like ? '%'
                order by naam
                """;
        return template.query(sql, snackMapper, beginNaam);
    }
}
