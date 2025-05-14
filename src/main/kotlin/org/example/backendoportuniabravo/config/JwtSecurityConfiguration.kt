package org.example.backendoportuniabravo.config

import jakarta.annotation.Resource
import org.example.backendoportuniabravo.Security.JwtAuthenticationFilter
import org.example.backendoportuniabravo.Security.JwtAuthorizationFilter
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
@Profile("!dev")
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class JwtSecurityConfiguration {

    @Value("\${url.unsecure}")
    val URL_UNSECURE: String? = null

    @Value("\${url.user.signup}")
    val URL_SIGNUP: String? = null


//     Propio de Spring, es para inyectar el servicio de usuario
//    @Resource
//    private val userDetailsService: AppUserDetailsService? = null

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
//        authProvider.setUserDetailsService(userDetailsService)
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
                    .requestMatchers("/$URL_UNSECURE/**").permitAll()
                    .requestMatchers(HttpMethod.POST, URL_SIGNUP).permitAll()
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
        super.configure(builder)
        val authenticationManager = http?.getSharedObject(
            AuthenticationManager::class.java
        )

        http?.addFilter(JwtAuthenticationFilter(authenticationManager!!))
        http?.addFilter(JwtAuthorizationFilter(authenticationManager!!))
    }
    companion object {
        fun customDsl(): AppCustomDsl {
            return AppCustomDsl()
        }
    }

}