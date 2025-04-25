package org.example.backendoportuniabravo.entities

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "students")
data class Students(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @ManyToOne(cascade = [(CascadeType.MERGE)])
    @JoinColumn(name = "profile_id", nullable = false)
    var profile: Profile,

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

    @ManyToMany
    @JoinTable(
        name = "students_languages",
        joinColumns = [JoinColumn(name = "student_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "language_id", referencedColumnName = "id")]
    )
    var languages: MutableSet<Language> = mutableSetOf(),

    @ManyToMany
    @JoinTable(
        name = "students_degrees",
        joinColumns = [JoinColumn(name = "student_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "degree_id", referencedColumnName = "id")]
    )
    var degrees: MutableSet<Degree> = mutableSetOf(),

    @ManyToMany
    @JoinTable(
        name = "students_colleges",
        joinColumns = [JoinColumn(name = "student_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "college_id", referencedColumnName = "id")]
    )
    var colleges: MutableSet<College> = mutableSetOf(),

    @ManyToMany
    @JoinTable(
        name = "students_interests",
        joinColumns = [JoinColumn(name = "student_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "interest_id", referencedColumnName = "id")],
    )
    var interests: MutableSet<Interest> = mutableSetOf()
)






@Entity
@Table(name = "hobbies")
data class Hobbie(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    var student: Students,

    @Column(name = "name", nullable = false)
    var name: String,
)


@Entity
@Table(name = "certifications")
data class Certification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @ManyToOne(cascade = [(CascadeType.MERGE)], fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    var student: Students,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "date", nullable = false)
    var date: Date,

    @Column(name = "organization", nullable = false)
    var organization: String,
)


@Entity
@Table(name = "experience")
data class Experience(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @ManyToOne(cascade = [(CascadeType.MERGE)], fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    var student: Students,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "description", nullable = false)
    var description: String,
)


@Entity
@Table(name = "skills")
data class Skill(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @ManyToOne(cascade = [(CascadeType.MERGE)], fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    var student: Students,

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

    @ManyToOne(cascade = [(CascadeType.MERGE)], fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    var student: Students,

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

    @ManyToOne(cascade = [(CascadeType.MERGE)], fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    var student: Students,

    @Column(name = "url", nullable = false)
    var url: String,
)