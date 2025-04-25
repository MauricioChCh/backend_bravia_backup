package org.example.backendoportuniabravo.entities

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
    var student: MutableSet<Student> = mutableSetOf()
)