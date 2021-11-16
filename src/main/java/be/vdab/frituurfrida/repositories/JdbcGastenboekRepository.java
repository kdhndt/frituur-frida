package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.GastenboekEntry;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class JdbcGastenboekRepository implements GastenboekRepository {

    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;

    private final RowMapper<GastenboekEntry> entryRowMapper =
            (result, rowNum) -> new GastenboekEntry(result.getLong("berichtid"), result.getDate("datum").toLocalDate(),
                    result.getString("naam"),
                    result.getString("bericht"));

    public JdbcGastenboekRepository(JdbcTemplate template) {
        this.template = template;
        insert = new SimpleJdbcInsert(template)
                .withTableName("gastenboekentries")
                .usingGeneratedKeyColumns("berichtId");
    }

    @Override
    public long create(GastenboekEntry entry) {
        return insert.executeAndReturnKey(
                        Map.of("datum", entry.getDatum(),
                                "naam", entry.getNaam(),
                                "bericht", entry.getBericht()))
                .longValue();
    }

    @Override
    public List<GastenboekEntry> findAll() {
        var sql = """
                select berichtId, datum, naam, bericht
                from gastenboekentries
                order by datum desc
                """;
        return template.query(sql, entryRowMapper);
    }

    @Override
    public void delete(Long[] ids) {
        //update method verwacht een Long[], geen long[]
        if (ids.length != 0) {
            //error hier kan genegeerd worden
            var sql = """
                    delete from gastenboekentries
                    where berichtId in (
                    """ + "?,".repeat(ids.length - 1) + "?)";
            template.update(sql, ids);
        }
    }
}