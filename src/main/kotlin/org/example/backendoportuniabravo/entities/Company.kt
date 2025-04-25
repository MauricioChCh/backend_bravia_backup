package org.example.backendoportuniabravo.entities

import jakarta.persistence.*

@Entity
@Table(name = "company")
data class Company(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,
    @OneToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    val user: User,

    @Column(name = "description", columnDefinition = "TEXT")
    var description: String,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "location", nullable = false)
    var location: String,

    @Column(name = "contact", nullable = false)
    var contact: String,



    @OneToMany(mappedBy = "company", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val contacts: List<Contact> = listOf()

){

}