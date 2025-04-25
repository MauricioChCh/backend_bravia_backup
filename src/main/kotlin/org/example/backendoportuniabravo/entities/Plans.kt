package org.example.backendoportuniabravo.entities

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "plan")
data class Plan(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    var price: BigDecimal,

    @Column(name = "duration_days", nullable = false)
    var durationDays: Int,

    @Column(name = "benefits", columnDefinition = "TEXT")
    var benefits: String,

    ){

}