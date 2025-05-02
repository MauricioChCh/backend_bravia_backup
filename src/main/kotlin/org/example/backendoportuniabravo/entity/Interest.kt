package org.example.backendoportuniabravo.entity

import jakarta.persistence.*

@Entity
@Table(name = "interests")
data class Interest(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "name", nullable = false)
    var name: String,


    @ManyToMany(mappedBy = "interests")
    var students: MutableSet<Student> = mutableSetOf()
)