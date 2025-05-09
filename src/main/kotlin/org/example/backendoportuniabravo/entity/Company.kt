package org.example.backendoportuniabravo.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
@Table(name = "company")
data class Company(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    var profile: Profile? = null,

    @Column(name = "description", columnDefinition = "TEXT", nullable = true)
    var description: String,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "image_url", nullable = true)
    var imageUrl: String? = null,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "company_business_area",
        joinColumns = [JoinColumn(name = "company_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "business_area_id", referencedColumnName = "id")]
    )
    var businessAreas: MutableSet<BusinessArea> = mutableSetOf(),

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "tags",
        joinColumns = [JoinColumn(name = "company_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id", referencedColumnName = "id")]
    )
    var tags: MutableSet<Tag> = mutableSetOf(),

    @OneToMany(mappedBy = "company", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    var contacts: MutableList<Contact> = mutableListOf(),

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = true)
    @JsonManagedReference
    var location: Location? = null,



    @OneToMany(mappedBy = "company", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val internships: MutableList<Internship> = mutableListOf(),

){
    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Company) return false
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

    @ManyToMany(mappedBy = "businessAreas", fetch = FetchType.LAZY)
    var companies: MutableSet<Company> = mutableSetOf()
) {
    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BusinessArea) return false
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
    var name: String,

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    var companies: MutableSet<Company> = mutableSetOf()
) {
    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Tag) return false
        return id == other.id
    }
}