package org.example.backendoportuniabravo.config

import org.example.backendoportuniabravo.Security.JwtAuthenticationFilter
import org.example.backendoportuniabravo.Security.JwtAuthorizationFilter
import org.example.backendoportuniabravo.service.AppUserDetailsService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

//Cualquier perfil que no sea el desarrollador local va a tener segguridad
@Profile("!local")
//@Profile("!heroku")
//////@Profile("!dev")
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class JwtSecurityConfiguration (
    private val userDetailsService: AppUserDetailsService,
    @Value("\${url.signup}") val urlSignup: String,
    @Value("\${url.login}") val urlLogin: String,
    @Value("\${url.company}") val urlCompanies: String,
    @Value("\${url.student}") val urlStudents: String,
    @Value("\${url.admin}") val urlAdmin: String,
){
    @Bean
    @Throws(java.lang.Exception::class)
    fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager? {
        return authConfig.authenticationManager
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider? {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService)
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    //la version del profe ya era obsoleta
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .cors { it.configurationSource(corsConfigurationSource()) }
            .authorizeHttpRequests {
                it
                    .requestMatchers(HttpMethod.POST, "$urlSignup/**").permitAll()
                    .requestMatchers(HttpMethod.POST, urlLogin).permitAll()

                    .requestMatchers("$urlCompanies/**").hasRole("COMPANY")
                    .requestMatchers("$urlStudents/**").hasRole("STUDENT")
                    .requestMatchers("$urlAdmin/**").hasRole("ADMIN")

                    .anyRequest().authenticated()
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authenticationProvider(authenticationProvider())
            .with(AppCustomDsl()) { customDsl -> customDsl.configure(http) }

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration().apply {
            allowCredentials = true
            addAllowedOrigin("http://localhost:3000")
            addAllowedHeader("*")
            addAllowedMethod("*")
        }
        source.registerCorsConfiguration("/**", config)
        return source
    }

}

class AppCustomDsl : AbstractHttpConfigurer<AppCustomDsl?, HttpSecurity?>() {
    override fun configure(http: HttpSecurity?) {
        super.configure(http)

        val authenticationManager = http?.getSharedObject(AuthenticationManager::class.java)
        if (authenticationManager != null) {
            http.addFilter(JwtAuthenticationFilter(authenticationManager))
            http.addFilter(JwtAuthorizationFilter(authenticationManager))
        }
    }

    companion object {
        fun customDsl(): AppCustomDsl {
            return AppCustomDsl()
        }
    }
}
