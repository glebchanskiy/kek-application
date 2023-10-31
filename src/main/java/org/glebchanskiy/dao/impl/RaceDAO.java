package org.glebchanskiy.dao.impl;

import org.glebchanskiy.dao.CrudDAO;
import org.glebchanskiy.mapper.CharClassMapper;
import org.glebchanskiy.mapper.RaceMapper;
import org.glebchanskiy.model.Class;
import org.glebchanskiy.model.Race;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RaceDAO implements CrudDAO<Race> {
    private final JdbcTemplate jdbcTemplate;

    public RaceDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(Race race) {
        this.jdbcTemplate.update(
                "INSERT INTO race (name, description) VALUES (?,?)", race.getName(), race.getDescription());
    }

    public void deleteByName(String name) {
        this.jdbcTemplate.update(
                "DELETE FROM race WHERE name = ?", name);
    }

    public void updateByName(String name, String description) {
        this.jdbcTemplate.update(
                "UPDATE race SET description = ?  WHERE name = ?", description, name);
    }

    public void update(Race race) {
        this.jdbcTemplate.update(
                "UPDATE race SET description = ?, name = ?  WHERE id = ?", race.getDescription(), race.getName(), race.getId());
    }

    public Race findById(int id) {
        return this.jdbcTemplate.queryForObject("SELECT * FROM race WHERE id = ?", new RaceMapper(), id);
    }

    public Race findByName(String name) {
        return this.jdbcTemplate.queryForObject("SELECT * FROM race WHERE name = ?", new Object[] {name}, new RaceMapper());
    }


    public List<Race> findAll() {
        return this.jdbcTemplate.query(
                "SELECT * FROM race", new RaceMapper());
    }

    public void deleteById(int id) {
        this.jdbcTemplate.update(
                "DELETE FROM race WHERE id = ?", id);
    }
}
