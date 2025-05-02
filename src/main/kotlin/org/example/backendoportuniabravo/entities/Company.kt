package org.example.backendoportuniabravo.entities

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
@Table(name = "company")
data class Company(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,
    @OneToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    val profile: Profile,

    @Column(name = "description", columnDefinition = "TEXT")
    var description: String,

    @Column(name = "name", nullable = false)
    var name: String,

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "company_business_area",
        joinColumns = [JoinColumn(name = "company_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "business_area_id", referencedColumnName = "id")]
    )
    val businessAreas: MutableSet<BusinessArea> = mutableSetOf(),

    @OneToMany(mappedBy = "company", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val contacts: MutableList<Contact> = mutableListOf(),

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    @JsonManagedReference
    val location: Location,

    @ManyToMany(mappedBy = "tags", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "tags",
        joinColumns = [JoinColumn(name = "company_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id", referencedColumnName = "id")]
    )
    val tags: MutableSet<Tag> = mutableSetOf(),

    @OneToMany(mappedBy = "company", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val internships: MutableList<Internship> = mutableListOf(),

){
    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Student) return false
        return id == other.id
    }
}

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

@Entity
@Table(name = "tag")
data class Tag(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "name", nullable = false)
    var Name: String,

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    var companies: MutableSet<Company> = mutableSetOf()

)