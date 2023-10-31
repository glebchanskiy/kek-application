package org.glebchanskiy.dao.impl;

import org.glebchanskiy.dao.CrudDAO;
import org.glebchanskiy.mapper.EquipsMapper;
import org.glebchanskiy.mapper.RaceMapper;
import org.glebchanskiy.model.Equip;
import org.glebchanskiy.model.Race;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class EquipmentDAO implements CrudDAO<Equip> {
    private final JdbcTemplate jdbcTemplate;

    public EquipmentDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(Equip race) {
        System.out.println("EGOR");
        this.jdbcTemplate.update(
                "INSERT INTO equipment (name, description) VALUES (?,?)", race.getName(), race.getDescription());
    }

    public void update(Equip equip) {
        System.out.println("EGOR");
        this.jdbcTemplate.update(
                "UPDATE equipment SET description = ?, name = ?  WHERE id = ?", equip.getDescription(), equip.getName(), equip.getId());
    }


    public void deleteById(int id) {
        this.jdbcTemplate.update(
                "DELETE FROM equipment WHERE id = ?", id);
    }

    public void deleteByName(String name) {
        this.jdbcTemplate.update(
                "DELETE FROM equipment WHERE name = ?", name);
    }

    public void updateByName(String name, String description) {
        this.jdbcTemplate.update(
                "UPDATE equipment SET description = ?  WHERE name = ?", description, name);
    }

    public Equip findById(int id) {
        return this.jdbcTemplate.queryForObject("SELECT * FROM equipment WHERE id = ?", new EquipsMapper(), id);
    }

    @Override
    public Equip findByName(String name) {
        return this.jdbcTemplate.queryForObject("SELECT * FROM equipment WHERE name = ?", new EquipsMapper(), name);
    }


    public List<Equip> findAll() {
        return this.jdbcTemplate.query(
                "SELECT * FROM equipment", new EquipsMapper());
    }
}
