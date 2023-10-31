package org.glebchanskiy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glebchanskiy.dao.CrudDAO;
import org.glebchanskiy.kek.router.controllers.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.NumberUtils;

public class CrudController<T> extends RestController {

    private static final Logger log = LoggerFactory.getLogger("Server");
    private final CrudDAO<T> dao;
    private final ObjectMapper mapper = new ObjectMapper();

    private final Class<T> clazz;
    public CrudController(String route, CrudDAO<T> dao, Class<T> clazz) {
        super(route);
        this.dao = dao;
        this.clazz = clazz;
    }


    @Override
    public String get(String body, String param) {
        try {
            if (param != null) {
                if (isNumeric(param)) {
                    int id = Integer.parseInt(param);
                    T entity = dao.findById(id);
                    return mapper.writeValueAsString(entity);
                } else {
                    T race = dao.findByName(param);
                    return mapper.writeValueAsString(race);
                }
            } else {
                System.out.println("findAll");
                var kek1 = dao.findAll();
                var kek = mapper.writeValueAsString(kek1);
                System.out.println("KEK: " + kek);
                return kek;
            }
        } catch (NumberFormatException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String post(String body, String param) {
        try {
            if (param != null) {
                int id = Integer.parseInt(param);
                var entity = mapper.readValue(body, clazz);

                dao.update(entity);
            } else {
                T entity = mapper.readValue(body, clazz);
                dao.insert(entity);
            }
            return null;
        } catch (NumberFormatException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String delete(String body, String param) {
        try {
            if (isNumeric(param)) {
                int id = Integer.parseInt(param);
                dao.deleteById(id);
            } else {
                dao.deleteByName(param);
            }
            return null;
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String update(String body, String param) {
        try {
            if (param != null) {
                int id = Integer.parseInt(param);
                var entity = mapper.readValue(body, clazz);
                dao.update(entity);
                return null;
            } else {
                throw new RuntimeException("Kek");
            }
        } catch (NumberFormatException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
