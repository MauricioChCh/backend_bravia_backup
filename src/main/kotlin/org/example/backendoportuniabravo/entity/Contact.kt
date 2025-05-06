package org.example.backendoportuniabravo.entity

import jakarta.persistence.*

@Entity
@Table(name= "contact")
data class Contact (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "url", nullable = false)
    var url: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    var company: Company? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Contact) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Contact(id=$id, url='$url')"
    }
}