package org.example.backendoportuniabravo.entity

import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "mock_interview")
data class MockInterview(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @ManyToOne(cascade = [(CascadeType.MERGE)])
    @JoinColumn(name = "student_id", nullable = false)
    var student: Student,

    @Column(name = "date", columnDefinition = "DATE")
    var date: Date,

    @Column(name = "result", columnDefinition = "TEXT")
    var result: String,

    @Column(name = "transcription")
    var transcription: String,

)
