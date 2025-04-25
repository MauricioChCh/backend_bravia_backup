package org.example.backendoportuniabravo.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "students")
data class Student(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "profile_id", nullable = false)
    var profile: Profile? = null,

    @Column(name = "description", columnDefinition = "TEXT")
    var description: String,

    @Column(name = "academic_center", nullable = false)
    var academicCenter: String,

    @OneToMany(mappedBy = "student", cascade = [CascadeType.ALL], orphanRemoval = true)
    var hobbies: MutableList<Hobbie> = mutableListOf(),

    @OneToMany(mappedBy = "student", cascade = [CascadeType.ALL], orphanRemoval = true)
    var certifications: MutableList<Certification> = mutableListOf(),

    @OneToMany(mappedBy = "student", cascade = [CascadeType.ALL], orphanRemoval = true)
    var experiences: MutableList<Experience> = mutableListOf(),

    @OneToMany(mappedBy = "student", cascade = [CascadeType.ALL], orphanRemoval = true)
    var skills: MutableList<Skill> = mutableListOf(),

    @OneToMany(mappedBy = "student", cascade = [CascadeType.ALL], orphanRemoval = true)
    var careers: MutableList<Career> = mutableListOf(),

    @OneToMany(mappedBy = "student", cascade = [CascadeType.ALL], orphanRemoval = true)
    var cvUrls: MutableList<CVUrl> = mutableListOf(),

    @OneToMany(mappedBy = "student", cascade = [CascadeType.ALL], orphanRemoval = true)
    var mockInterviews: MutableList<MockInterview> = mutableListOf(),

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "students_languages",
        joinColumns = [JoinColumn(name = "student_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "language_id", referencedColumnName = "id")]
    )
    var languages: MutableSet<Language> = mutableSetOf(),

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "students_degrees",
        joinColumns = [JoinColumn(name = "student_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "degree_id", referencedColumnName = "id")]
    )
    var degrees: MutableSet<Degree> = mutableSetOf(),

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "students_colleges",
        joinColumns = [JoinColumn(name = "student_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "college_id", referencedColumnName = "id")]
    )
    var colleges: MutableSet<College> = mutableSetOf(),

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "students_interests",
        joinColumns = [JoinColumn(name = "student_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "interest_id", referencedColumnName = "id")],
    )
    var interests: MutableSet<Interest> = mutableSetOf()
) {
    override fun toString(): String {
        return "Student(description='$description', academicCenter='$academicCenter')"
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Student) return false
        return id == other.id
    }
}






@Entity
@Table(name = "hobbies")
data class Hobbie(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    var student: Student,

    @Column(name = "name", nullable = false)
    var name: String,
) {
    override fun toString(): String {
        return "Hobbie(name='$name')"
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Hobbie) return false
        return id == other.id
    }
}


@Entity
@Table(name = "certifications")
data class Certification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    var student: Student,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "date", nullable = false)
    var date: Date,

    @Column(name = "organization", nullable = false)
    var organization: String,
) {
    override fun toString(): String {
        return "Certification(name='$name', date=$date, organization='$organization')"
    }
}


@Entity
@Table(name = "experience")
data class Experience(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    @JsonIgnore
    var student: Student,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "description", nullable = false)
    var description: String,
) {
    override fun toString(): String {
        return "Experience(name='$name', description='$description')"
    }

}


@Entity
@Table(name = "skills")
data class Skill(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    var student: Student,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "description", nullable = false)
    var description: String,
)

@Entity
@Table(name = "carrer")
data class Career(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    var student: Student,

    @Column(name = "carrer", nullable = false)
    var carrer: String,
)

@Entity
@Table(name = "cv_url")
data class CVUrl(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    var student: Student,

    @Column(name = "url", nullable = false)
    var url: String,
)