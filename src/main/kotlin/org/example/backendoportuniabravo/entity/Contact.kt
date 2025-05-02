package org.example.backendoportuniabravo.entity

import jakarta.persistence.*

@Entity
@Table(name= "contact")
data class Contact (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    val company: Company,


    @Column(name = "url", nullable = false)
    var url: String,

    )