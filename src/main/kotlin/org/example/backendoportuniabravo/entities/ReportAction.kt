package org.example.backendoportuniabravo.entities

import jakarta.persistence.*

@Entity
@Table(name = "report_action")
data class ReportAction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @OneToOne(cascade = [(CascadeType.MERGE)])
    @JoinColumn(name = "user_report_id", nullable = false)
    var userReport: UserReports,

    @ManyToOne(cascade = [(CascadeType.MERGE)])
    @JoinColumn(name = "admin_user_id", nullable = false)
    var admin: Admin,

    @Column(name = "action", nullable = false)
    var action: String,
)