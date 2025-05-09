package org.example.backendoportuniabravo.entity

import jakarta.persistence.*



@Entity
@Table(name = "profile")
data class Profile(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "verified", nullable = false)
    var verified: Boolean,

    @OneToOne(mappedBy = "profile", fetch = FetchType.EAGER, cascade = [CascadeType.MERGE, CascadeType.PERSIST])
    var user: User? = null,

    @OneToOne(mappedBy = "profile", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var student: Student? = null,

    @OneToOne(mappedBy = "profile", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var admin: Admin? = null,

    @OneToOne(mappedBy = "profile", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var company: Company? = null,

) {
    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Profile) return false
        return id == other.id
    }
}