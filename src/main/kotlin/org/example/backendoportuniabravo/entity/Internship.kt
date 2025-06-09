package org.example.backendoportuniabravo.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "internship")
data class Internship(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    // Relation con el user que  la pasantía
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    var company: Company,

    //Relacion con su localidad
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    var location: Location,

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "imageurl")
    var imageUrl: String? = null,

    @Column(name = "publication_date")
    @Temporal(TemporalType.TIMESTAMP)
    var publicationDate: Date,

    @Column(name = "duration")
    var duration: String? = null,

    @Column(name = "salary")
    var salary: Double? = null,

    @Column(name = "modality")
    var modality: String,

    @Column(name = "schedule")
    var schedule: String,

    @Column(name = "requirements", columnDefinition = "TEXT")
    var requirements: String,

    @Column(name = "activities", columnDefinition = "TEXT")
    var activities: String,

    @Column(name = "link")
    var link: String,

    @ManyToMany(mappedBy = "internships")
    var students: MutableSet<Student> = mutableSetOf(),

    @OneToMany(mappedBy = "internship", cascade = [CascadeType.ALL], orphanRemoval = true)
    var markedInternship: MutableSet<MarkedInternship> = mutableSetOf(),

    // Campo calculado/transitorio para el frontend (no se persiste) creo que ni siquiera va aca, esto deberia dser otra tabla
    @Transient
    var bookmarked: Boolean = false
) {
//
//    @Transient
//    var description: String = ""
//        get() = "$title at $company - $location"

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