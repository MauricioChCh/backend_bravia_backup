package org.example.backendoportuniabravo.entities

import jakarta.persistence.*

@Entity
@Table(name = "student_carrer")
data class StudentCarrrer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @ManyToOne(cascade = [(CascadeType.MERGE)])
    @JoinColumn(name = "student_id", nullable = false)
    var student: Student,

    @Column(name = "carrer", nullable = false)
    var carrer: String,
)
