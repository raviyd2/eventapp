package com.eventapp.eventapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/db-connection")
    public String testDbConnection() {
        try (Connection conn = dataSource.getConnection()) {
            return "Database connection successful to: " + 
                   conn.getMetaData().getDatabaseProductName();
        } catch (SQLException e) {
            return "Database connection failed: " + e.getMessage();
        }
    }
}