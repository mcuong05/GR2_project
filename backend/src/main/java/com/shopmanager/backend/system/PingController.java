package com.shopmanager.backend.system;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PingController {

    private final DataSource dataSource;

    public PingController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/ping")
    public Map<String, String> ping() {
        String database;
        try (Connection connection = dataSource.getConnection()) {
            database = connection.isValid(2) ? "up" : "down";
        } catch (SQLException e) {
            database = "down";
        }
        return Map.of("status", "ok", "database", database);
    }
}

