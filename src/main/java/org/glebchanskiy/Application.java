package org.glebchanskiy;

import org.glebchanskiy.controller.*;
import org.glebchanskiy.dao.impl.*;
import org.glebchanskiy.kek.Configuration;
import org.glebchanskiy.kek.ConnectionsManager;
import org.glebchanskiy.kek.Server;
import org.glebchanskiy.kek.router.Router;
import org.glebchanskiy.kek.router.filters.CorsFilter;
import org.glebchanskiy.kek.utils.Mapper;
import org.glebchanskiy.model.*;
import org.glebchanskiy.model.Class;
import org.glebchanskiy.util.DataSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.concurrent.Executors;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String... args) {

        var config = Configuration.getInstance(args);
        logger.info("[configuration]:\n{}", config);

        var dataSource = new DataSourceBuilder()
                .setDriverClassName("org.postgresql.Driver")
                .setUrl("jdbc:postgresql://localhost:5432/dnd")
                .setPassword("postgres")
                .setUsername("postgres")
                .build();

        var jdbcTemplate = new JdbcTemplate(dataSource);
//
        var charDAO = new CharDAO(jdbcTemplate);
        var raceDAO = new RaceDAO(jdbcTemplate);
        var charClassDAO = new CharClassDAO(jdbcTemplate);
        var backgroundDAO = new BackgroundDAO(jdbcTemplate);
        var equipDAO = new EquipmentDAO(jdbcTemplate);


        var router = new Router();

//
        var raceController = new CrudController<>("/api/races*", raceDAO, Race.class);
        var equipController =  new CrudController<>("/api/equips*", equipDAO, Equip.class);
        var classController =  new CrudController<>("/api/classes*", charClassDAO, Class.class);
        var backController =  new CrudController<>("/api/backs*", backgroundDAO, Background.class);
        var charController =  new CrudController<>("/api/chars*", charDAO, Char.class);
        router.addController(raceController);
        router.addController(equipController);
        router.addController(classController);
        router.addController(backController);
        router.addController(charController);

//            router.addController(new RaceController("/kek*", raceDAO));
//        router.addController(new AppController("/"));
//        router.addController(new ShareFilesController("/assets/*", config));


        var server = Server.builder()
                .filter(new CorsFilter())
                .connectionsManager(new ConnectionsManager())
                .executorService(Executors.newFixedThreadPool(10))
                .mapper(new Mapper())
                .router(router)
                .build();

        server.run();
    }
}
