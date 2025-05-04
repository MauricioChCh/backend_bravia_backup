package org.example.backendoportuniabravo.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import jakarta.persistence.*

@Entity
@Table(name= "location")
data class Location (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

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
    @JsonDeserialize(contentAs = Internship::class)
    val internships: Set<Internship> = emptySet(),

    @OneToOne(mappedBy = "location", fetch = FetchType.LAZY)
    @JsonBackReference
    var company: Company? = null

){

}


@Entity
@Table(name= "province")
data class Province (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "name")
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

    @Column(name = "name")
    var name: String,

){

}
