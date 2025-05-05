package org.example.backendoportuniabravo.entity

import jakarta.persistence.*

@Entity
@Table(name= "contact")
data class Contact (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "url", nullable = false)
    var url: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    var company: Company? = null,

    ) //TODO:  add equals, hashCode and toString methods