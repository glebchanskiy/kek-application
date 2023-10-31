package org.glebchanskiy.dao.impl;

import org.glebchanskiy.dao.CrudDAO;
import org.glebchanskiy.mapper.BackgroundMapper;
import org.glebchanskiy.mapper.EquipsMapper;
import org.glebchanskiy.mapper.RaceMapper;
import org.glebchanskiy.model.Background;
import org.glebchanskiy.model.Class;
import org.glebchanskiy.model.Equip;
import org.glebchanskiy.model.Race;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class BackgroundDAO implements CrudDAO<Background> {
    private final JdbcTemplate jdbcTemplate;

    public BackgroundDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(Background background) {
        this.jdbcTemplate.update(
                "INSERT INTO background (name, description) VALUES (?,?)", background.getName(), background.getDescription());
    }

    public void update(Background background) {
        this.jdbcTemplate.update(
                "UPDATE background SET description = ?, name = ?  WHERE id = ?", background.getDescription(), background.getName(), background.getId());
    }


    public void deleteById(int id) {
        this.jdbcTemplate.update(
                "DELETE FROM background WHERE id = ?", id);
    }

    public void deleteByName(String name) {
        this.jdbcTemplate.update(
                "DELETE FROM background WHERE name = ?", name);
    }

    public void updateByName(String name, String description) {
        this.jdbcTemplate.update(
                "UPDATE background SET description = ?  WHERE name = ?", description, name);
    }

    public Background findById(int id) {
        return this.jdbcTemplate.queryForObject("SELECT * FROM background WHERE id = ?", new BackgroundMapper(), id);
    }

    public Background findByName(String name) {
        return this.jdbcTemplate.queryForObject("SELECT * FROM background WHERE name = ?", new Object[] {name}, new BackgroundMapper());
    }

    public List<Background> findAll() {
        return this.jdbcTemplate.query(
                "SELECT * FROM background", new BackgroundMapper());
    }
}
