package by.epam.tc.conference.dao.sql;

import by.epam.tc.conference.dao.sql.rowmapper.RowMapper;
import by.epam.tc.conference.entity.Identifiable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ResultHandler<T extends Identifiable> {

    private final RowMapper<? extends T> rowMapper;

    public ResultHandler(RowMapper<? extends T> rowMapper) {
        this.rowMapper = rowMapper;
    }

    public Optional<T> fetchOne(ResultSet resultSet) throws SQLException {
        if (resultSet.first()) {
            T object = rowMapper.handle(resultSet);
            return Optional.of(object);
        }
        return Optional.empty();
    }

    public List<T> fetchAll(ResultSet resultSet) throws SQLException {
        List<T> objects = new ArrayList<>();
        while (resultSet.next()) {
            T object = rowMapper.handle(resultSet);
            objects.add(object);
        }
        return objects;
    }
}
