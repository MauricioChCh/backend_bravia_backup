package org.example.backendoportuniabravo.repositories

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Entity
@Table(name = "students_job_offers")
data class StudentInternship(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "student_id", nullable = false)
    var studentId: Long,

    @Column(name = "internship_id", nullable = false)
    var internshipId: Long,

    @Column(name = "applied")
    var applied: Boolean = false,

    @Column(name = "saved_date")
    @Temporal(TemporalType.TIMESTAMP)
    var savedDate: Date = Date()
)

@Repository
interface StudentInternshipRepository : JpaRepository<StudentInternship, Long> {
    fun findByStudentIdAndInternshipId(studentId: Long, internshipId: Long): Optional<StudentInternship>
    fun findByStudentId(studentId: Long): List<StudentInternship>
    fun countByInternshipId(internshipId: Long): Long
    fun deleteByStudentIdAndInternshipId(studentId: Long, internshipId: Long)
}