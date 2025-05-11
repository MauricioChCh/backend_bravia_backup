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

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.MERGE])
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    var city: City,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.MERGE])
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    var country: Country,

    @Column(name = "address")
    var address: String,


    //Cada internship tiene una localizacion
    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JsonDeserialize(contentAs = Internship::class)
    val internships: MutableList<Internship>? = mutableListOf(),

    @OneToOne(mappedBy = "location", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JsonBackReference
    var company: Company? = null

){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Location) return false

        return id != null && id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

}


@Entity
@Table(name= "city")
data class City (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "name")
    var name: String? = null,

){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is City) return false

        return id != null && id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }


}

@Entity
@Table(name= "country")
data class Country (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "name")
    var name: String? = null

){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Country) return false

        return id != null && id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }


}
