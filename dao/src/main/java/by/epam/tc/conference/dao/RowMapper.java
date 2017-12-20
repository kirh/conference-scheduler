package by.epam.tc.conference.dao;

import by.epam.tc.conference.entity.Identifiable;

import java.sql.ResultSet;

public interface RowMapper<T extends Identifiable> {

    T handle(ResultSet result);
}
