package org.example.backendoportuniabravo.entities

import jakarta.persistence.*

@Entity
@Table(name = "students")
data class Students(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @ManyToOne(cascade = [(CascadeType.MERGE)])
    @JoinColumn(name = "profile_id", nullable = false)
    var profile: Profile,

    @Column(name = "description", columnDefinition = "TEXT")
    var description: String,

    @Column(name = "academic_center", nullable = false)
    var academicCenter: String,
)
