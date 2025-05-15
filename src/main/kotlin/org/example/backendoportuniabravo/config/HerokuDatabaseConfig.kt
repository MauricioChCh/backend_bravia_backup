//package org.example.backendoportuniabravo.config
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.context.annotation.Profile
//import javax.sql.DataSource
//import org.springframework.boot.jdbc.DataSourceBuilder
//
//@Configuration
//@Profile("heroku")
//class HerokuDatabaseConfig {
//    @Bean
//    fun dataSource(): DataSource {
//        val dbUrl = System.getenv("JDBC_DATABASE_URL")
//        val username = System.getenv("JDBC_DATABASE_USERNAME")
//        val password = System.getenv("JDBC_DATABASE_PASSWORD")
//
//        return DataSourceBuilder.create()
//            .url(dbUrl)
//            .username(username)
//            .password(password)
//
//
//    }
//
//}