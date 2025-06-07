package org.example.backendoportuniabravo.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "Languages")
data class Language(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "name", nullable = false)
    var name: String,

    @ManyToMany(mappedBy = "languages", fetch = FetchType.LAZY)
    @JsonIgnore // También evita bucles infinitos en JSON
    var students: MutableSet<Student> = mutableSetOf()
) {
    override fun toString(): String {
        return "Language(id=$id, name='$name')"
    }

    override fun equals(other: Any?): Boolean {
        return other is Language && this.id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
