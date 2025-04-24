package org.example.backendoportuniabravo.entities

import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "admin")
data class Admin(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "user_id", nullable = false)
    var userId: Int,

    ){

}
