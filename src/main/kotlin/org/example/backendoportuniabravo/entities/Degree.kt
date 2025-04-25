package org.example.backendoportuniabravo.entities

import jakarta.persistence.*

@Entity
@Table(name = "degrees")
data class Degree(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "name", nullable = false)
    var name: String,

    @ManyToMany(mappedBy = "degrees", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var students: MutableSet<Student> = mutableSetOf()
)
