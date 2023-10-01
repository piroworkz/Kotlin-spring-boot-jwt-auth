package com.davidluna.jwtauth.app.config

import com.davidluna.jwtauth.app.r.R
import com.davidluna.jwtauth.app.r.R.EnvVariable.DB_DRIVER
import org.jetbrains.exposed.sql.Database
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DataSourceConfig(
    @Qualifier(R.EnvVariable.DB_USER)
    private val dbUser: String,
    @Qualifier(R.EnvVariable.DB_PASSWORD)
    private val dbPassword: String,
    @Qualifier(R.EnvVariable.DB_URL)
    private val dbUrl: String
) {

    @Bean
    fun provideDataSource(): DataSource {
        return DataSourceBuilder.create()
            .url(dbUrl)
            .driverClassName(DB_DRIVER)
            .username(dbUser)
            .password(dbPassword)
            .build()
    }

    @Bean
    fun provideDatabase(dataSource: DataSource): Database =
        Database.connect(dataSource)
}