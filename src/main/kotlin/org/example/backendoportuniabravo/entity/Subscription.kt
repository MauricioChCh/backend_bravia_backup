package org.example.backendoportuniabravo.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "subscriptions")
data class Subscriptions(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    var startDate: Date,

    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    var endDate: Date,

    @Column(name = "status", nullable = false)
    var status: String
){

}