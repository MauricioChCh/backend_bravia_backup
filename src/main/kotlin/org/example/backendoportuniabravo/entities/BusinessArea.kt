package org.example.backendoportuniabravo.entities

import jakarta.persistence.*

@Entity
@Table(name = "business_area")
data class BusinessArea(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "name", nullable = false)
    var name: String,

    @ManyToMany(mappedBy = "businessAreas", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var companies: MutableSet<Company> = mutableSetOf()
) {
    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Student) return false
        return id == other.id
    }
}