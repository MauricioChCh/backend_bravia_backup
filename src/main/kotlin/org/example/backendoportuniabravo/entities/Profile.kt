package org.example.backendoportuniabravo.entities

import jakarta.persistence.*



@Entity
@Table(name = "profile")
data class Profile(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @OneToOne(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @Column(name = "verified", nullable = false)
    var verified: Boolean,

    @OneToOne(mappedBy = "profile", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val location: Location? = null
)