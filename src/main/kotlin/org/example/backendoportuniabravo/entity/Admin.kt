package org.example.backendoportuniabravo.entity

import jakarta.persistence.*


@Entity
@Table(name = "admin")
data class Admin(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "profile_id", nullable = false)
    var profile: Profile? = null,

    @OneToMany(mappedBy = "admin", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var reportsAction: MutableSet<ReportAction> = mutableSetOf(),
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Admin) return false
        if (id == null || other.id == null) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Admin(id=$id, profileId=${profile?.id}, reportsActionSize=${reportsAction.size})"
    }
}
