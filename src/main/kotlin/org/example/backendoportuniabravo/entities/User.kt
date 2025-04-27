package org.example.backendoportuniabravo.entities

    import jakarta.persistence.*
    import java.util.*

    @Entity
    @Table(name = "app_user") // "user" is reserved in PostgreSQL
    data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Long? = null,

        @Column(name = "create_date", nullable = false)
        @Temporal(TemporalType.TIMESTAMP)
        var createDate: Date,

        @Column(name = "first_name", nullable = false)
        var firstName: String,

        @Column(name = "last_name", nullable = false)
        var lastName: String,

        @Column(name = "email", nullable = false)
        var email: String,

        @Column(name = "password", nullable = false)
        var password: String,

        @Column(name = "token_expired", nullable = false)
        var tokenExpired: Boolean,

        @Column(name = "enabled", nullable = false)
        var enabled: Boolean,

        @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        val company: Company? = null,

        @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
        @JoinColumn(name = "profile_id")
        var profile: Profile? = null,

        @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        var reportsMade: MutableSet<UserReports> = mutableSetOf(),

        @OneToMany(mappedBy = "userReported", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        var reportsReceived: MutableSet<UserReports> = mutableSetOf(),

        ) {
        override fun hashCode(): Int {
            return id?.hashCode() ?: 0
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is User) return false
            if (id == null || other.id == null) return false
            return id == other.id
        }
    }
