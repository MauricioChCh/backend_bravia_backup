package org.example.backendoportuniabravo.entity

import com.fasterxml.jackson.annotation.JsonIgnore
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
    @JsonIgnore
    var students: MutableSet<Student> = mutableSetOf()
) {
    override fun toString(): String {
        return "Degree(id=$id, name='$name')"
    }

    override fun equals(other: Any?): Boolean {
        return other is Degree && this.id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}