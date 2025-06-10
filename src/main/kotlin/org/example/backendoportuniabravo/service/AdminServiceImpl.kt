package org.example.backendoportuniabravo.service

import org.example.backendoportuniabravo.dto.AdminResponseDTO
import org.example.backendoportuniabravo.dto.CompanyUserResponse
import org.example.backendoportuniabravo.dto.ReportActionRequestDTO
import org.example.backendoportuniabravo.entity.Admin
import org.example.backendoportuniabravo.entity.ReportAction
import org.example.backendoportuniabravo.mapper.AdminMapper
import org.example.backendoportuniabravo.mapper.CompanyMapper
import org.example.backendoportuniabravo.mapper.ReportActionMapper
import org.example.backendoportuniabravo.repository.AdminRepository
import org.example.backendoportuniabravo.repository.CompanyRepository
import org.example.backendoportuniabravo.repository.ProfileRepository
import org.example.backendoportuniabravo.repository.ReportActionRepository
import org.example.backendoportuniabravo.repository.UserReportRepository
import org.example.backendoportuniabravo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminServiceImpl(
    @Autowired
    private val adminRepository: AdminRepository,
    @Autowired
    private val adminMapper: AdminMapper,
    @Autowired
    private val profileRepository: ProfileRepository,
    @Autowired
    private val userRepository: UserRepository, // Asegúrate de tener el repositorio del User
    @Autowired
    private val companyRepository: CompanyRepository,
    @Autowired
    private val companyMapper: CompanyMapper


): AdminService {
    override fun getAdmins(): List<AdminResponseDTO>? {
        return try {
            val adminList = adminRepository.findAll()

            adminList.map {
                val user = userRepository.findByProfileId(it.profile?.id) // Asumiendo que el repositorio tiene un método así
                adminMapper.adminToAdminResponseDTOWithFullName(it, user) // Pasamos el User al Mapper
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException("Error al obtener la lista de administradores", e)
        }
    }

    override fun getAdminById(id: Long): AdminResponseDTO? {
        return try {
            val adminFound = adminRepository.findById(id)

            if (adminFound.isPresent) {
                val admin = adminFound.get()
                val user = userRepository.findByProfileId(admin.profile?.id)
                adminMapper.adminToAdminResponseDTOWithFullName(admin, user)
            } else {
                null
            }
        } catch (e: Exception) {
            throw RuntimeException("Error al obtener el administrador", e)
        }
    }

    @Transactional
    override fun addAdmin(id: Long): Long? {
        return try {
            // Buscar el perfil por ID
            val profile = profileRepository.findById(id).orElse(null) ?: return null

            // Validar si ya existe un Admin con ese perfil
            if (adminRepository.existsByProfileId(id)) return null

            // Crear y guardar el nuevo admin
            val newAdmin = Admin(profile = profile)
            val savedAdmin = adminRepository.save(newAdmin)

            // Retornar el ID del admin guardado
            savedAdmin.id
        } catch (ex: Exception) {
            ex.printStackTrace() // Para debug; opcionalmente loguear el error
            null // Retorna null en caso de error
        }
    }

    override fun deleteAdmin(profileId: Long): Boolean {
        return try {
            val admin = adminRepository.findByProfileId(profileId) ?: return false

            adminRepository.delete(admin)
            true
        } catch (ex: Exception) {
            ex.printStackTrace()
            false
        }
    }

    override fun getAllCompanies(): List<CompanyUserResponse> {
        return try {
            val companyList = companyRepository.findAll()

            companyList.map {
                    company -> companyMapper.companyToCompanyUserResponse(company)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException("Error al obtener la lista de administradores", e)
        }
    }

}

@Service
class ReportActionServiceImpl(
    private val reportActionRepository: ReportActionRepository,
    private val mapper: ReportActionMapper,
    private val adminRepository: AdminRepository,
    private val userReportRepository: UserReportRepository,
) : ReportActionService {

    override fun getAllActions(): List<ReportAction> =
        reportActionRepository.findAll()

    override fun getActionById(id: Long): ReportAction? =
        reportActionRepository.findById(id).orElse(null)

    override fun getActionsByAdminId(adminId: Long): List<ReportAction> =
        reportActionRepository.findByAdminId(adminId)

    override fun existsByAdminId(adminId: Long): Boolean =
        reportActionRepository.existsByAdminId(adminId)

    override fun getActionByUserReportId(userReportId: Long): ReportAction? =
        reportActionRepository.findByUserReportId(userReportId)

    override fun existsByUserReportId(userReportId: Long): Boolean =
        reportActionRepository.existsByUserReportId(userReportId)

    override fun searchByActionText(action: String): List<ReportAction> =
        reportActionRepository.findByActionContainingIgnoreCase(action)

    @Transactional
    override fun createAction(action: ReportActionRequestDTO): ReportAction {
        val admin = adminRepository.findById(action.adminId)
            .orElseThrow { RuntimeException("Admin no encontrado con id: ${action.adminId}") }

        val userReport = userReportRepository.findById(action.userReportId)
            .orElseThrow { RuntimeException("UserReport no encontrado con id: ${action.userReportId}") }

        val reportAction = mapper.toEntity(action, admin, userReport)

        return reportActionRepository.save(reportAction)
    }

    @Transactional
    override fun deleteAction(id: Long) {
        if (reportActionRepository.existsById(id)) {
            reportActionRepository.deleteById(id)
        }
    }
}

