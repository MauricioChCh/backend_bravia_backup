package org.example.backendoportuniabravo.entities

import jakarta.persistence.*

@Entity
@Table(name = "students")
data class Students(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "description", columnDefinition = "TEXT")
    var description: String,

    @Column(name = "academic_center", nullable = false)
    var academicCenter: String,

    @Column(name = "carrer", nullable = false)
    var carrer: String,

    @Column(name = "cv_url")
    var cvUrl: String
){

}
