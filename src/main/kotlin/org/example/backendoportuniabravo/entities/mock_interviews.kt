package org.example.backendoportuniabravo.entities

import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "mock_interviews")
data class MockInterviews(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "student_id", nullable = false)
    var firstName: Int,

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    var date: Date,

    @Column(name = "result", nullable = false)
    var Result: String,

    @Column(name = "transcription", nullable = false)
    var email: String,
    ){

}
