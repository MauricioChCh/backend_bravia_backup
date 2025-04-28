package org.example.backendoportuniabravo.entities

import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "admin")
data class Admin(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "profile_id", nullable = false)
    var profile: Profile? = null,

    @OneToMany(mappedBy = "admin", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var reportsAction: MutableSet<ReportAction> = mutableSetOf(),
    ){

}
