package org.example.backendoportuniabravo.entities

import jakarta.persistence.*

@Entity
@Table(name= "location")
data class Location (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,


    @OneToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    val profile: Profile,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id", referencedColumnName = "id")
    val province: Province,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    val country: Country,

    @Column(name = "address", nullable = false)
    var address: String,


    //Cada internship tiene una localizacion
    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    val internships: List<Internship> = listOf()
){

}


@Entity
@Table(name= "province")
data class Province (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "name", nullable = false)
    var name: String,

){

}

@Entity
@Table(name= "country")
data class Country (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "name", nullable = false)
    var name: String,

){

}
