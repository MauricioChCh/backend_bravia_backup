package org.example.backendoportuniabravo.entities

import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "user_reports")
data class UserReports(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "user_id", nullable = false)
    var userId: Int,

    @Column(name = "user_id_reported", nullable = false)
    var userIdReported: Int,

    @Column(name = "description", nullable = false)
    var description: String,

    ){

}

