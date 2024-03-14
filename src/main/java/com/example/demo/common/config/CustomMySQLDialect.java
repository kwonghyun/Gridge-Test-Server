package com.example.demo.common.config;

import org.hibernate.dialect.MySQL8Dialect;

import java.sql.Types;

public class CustomMySQLDialect extends MySQL8Dialect {
    public CustomMySQLDialect() {
        super();
        registerColumnType(Types.TIMESTAMP, "timestamp");
    }
}
