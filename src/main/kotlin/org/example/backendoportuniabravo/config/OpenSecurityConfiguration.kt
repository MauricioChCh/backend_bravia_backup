package org.example.backendoportuniabravo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Profile("heroku")
//@Profile("dev")
@Configuration
@EnableWebSecurity
class OpenSecurityConfiguration{


    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf{
                it.disable()
            }.cors{
                it.disable()
            }.authorizeHttpRequests {
            it.anyRequest().permitAll()
            }
//            .authorizeHttpRequests {
//                it.anyRequest().authenticated()
//            }

        return http.build()
    }

}