package org.example.backendoportuniabravo.entity

import com.fasterxml.jackson.annotation.JsonIgnore
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
    @JsonIgnore
    var students: MutableSet<Student> = mutableSetOf()
){
    override fun toString(): String {
        return "Interest(id=$id, name='$name')"
    }

    override fun equals(other: Any?): Boolean {
        return other is Interest && this.id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}