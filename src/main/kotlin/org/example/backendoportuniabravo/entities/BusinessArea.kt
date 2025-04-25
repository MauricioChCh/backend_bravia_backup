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

//    @ManyToMany(mappedBy = "businessAreas")
//    var internships: MutableSet<Internship> = mutableSetOf()
) {
    // Métodos equals, hashCode y toString...
}