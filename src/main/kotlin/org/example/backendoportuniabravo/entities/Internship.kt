package org.example.backendoportuniabravo.entities

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "internship")
data class Internship(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "company_id")
    var companyId: Int,

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "company", nullable = false)
    var company: String,

    @Column(name = "imageurl")
    var imageUrl: String? = null,

    @Column(name = "location")
    var location: String,

    @Column(name = "publication_date")
    @Temporal(TemporalType.TIMESTAMP)
    var publicationDate: Date,

    @Column(name = "duration")
    var duration: String,

    @Column(name = "salary")
    var salary: Double? = null,

    @Column(name = "modality")
    var modality: String,

    @Column(name = "schedule")
    var schedule: String,

    @Column(name = "requirements", columnDefinition = "TEXT")
    var requirements: String,

    @Column(name = "percentage")
    var percentage: String? = null,

    @Column(name = "activities", columnDefinition = "TEXT")
    var activities: String,

    @Column(name = "contact")
    var contact: String,

    @Column(name = "link")
    var link: String,

    // Relación con áreas de negocio
    @ManyToMany
    @JoinTable(
        name = "company_business_area",
        joinColumns = [JoinColumn(name = "company_id", referencedColumnName = "company_id")],
        inverseJoinColumns = [JoinColumn(name = "business_area_id", referencedColumnName = "id")]
    )
    var businessAreas: MutableSet<BusinessArea> = mutableSetOf(),

    // Campo calculado/transitorio para el frontend (no se persiste) creo que ni siquiera va aca, esto deberia dser otra tabla
    @Transient
    var isBookmarked: Boolean = false
) {

    @Transient
    var description: String = ""
        get() = "$title at $company - $location"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Internship) return false
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Internship(id=$id, title='$title', company='$company', location='$location')"
    }
}