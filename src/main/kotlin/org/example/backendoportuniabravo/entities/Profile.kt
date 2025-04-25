package org.example.backendoportuniabravo.entities

import jakarta.persistence.*



@Entity
@Table(name = "profile")
data class Profile(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "user_id", nullable = false)
    var userId: Int,

    @Column(name = "verified", nullable = false)
    var verified: Boolean,

    @OneToOne(mappedBy = "profile", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val location: Location? = null
    ){

}