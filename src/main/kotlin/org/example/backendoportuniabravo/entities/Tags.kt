package org.example.backendoportuniabravo.entities

import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "tags")
data class Tags(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "profile_id", nullable = false)
    var profileId: Int,

    @Column(name = "name", nullable = false)
    var Name: String,

    ){

}

