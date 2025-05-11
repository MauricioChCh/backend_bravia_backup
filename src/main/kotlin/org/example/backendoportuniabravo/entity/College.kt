package org.example.backendoportuniabravo.entity

import jakarta.persistence.*

@Entity
@Table(name = "colleges")
data class College(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "name", nullable = false)
    var name: String,

    @ManyToMany(mappedBy = "colleges", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var students: MutableSet<Student> = mutableSetOf()
) //TODO:  add equals, hashCode and toString methods
