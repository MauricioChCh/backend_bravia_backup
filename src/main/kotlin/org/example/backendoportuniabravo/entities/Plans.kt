package org.example.backendoportuniabravo.entities

import jakarta.persistence.*

@Entity
@Table(name = "plans")
data class Plans(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    var price: Double,

    @Column(name = "duration_days", nullable = false)
    var durationDays: Int,

    @Column(name = "benefits", columnDefinition = "TEXT")
    var benefits: String,

){

}