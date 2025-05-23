package org.example.backendoportuniabravo.service

import org.example.backendoportuniabravo.dto.*
import org.example.backendoportuniabravo.entity.Admin
import org.example.backendoportuniabravo.mapper.AdminMapper
import org.example.backendoportuniabravo.repository.AdminRepository
import org.example.backendoportuniabravo.repository.ProfileRepository
import org.example.backendoportuniabravo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


interface AdminService{
    fun getAdmins() : List<AdminResponseDTO>?
    fun getAdminById(id: Long) : AdminResponseDTO?
    fun addAdmin(id: Long) : Long?
    fun deleteAdmin(profileId: Long) : Boolean

}


@Service
class AdminServiceImpl(
    @Autowired
    private val adminRepository: AdminRepository,
    @Autowired
    private val adminMapper: AdminMapper,
    @Autowired
    private val profileRepository: ProfileRepository,
    @Autowired
    private val userRepository: UserRepository // Asegúrate de tener el repositorio del User

): AdminService {
    override fun getAdmins(): List<AdminResponseDTO>? {
        return try {
            val adminList = adminRepository.findAll()
            //println("Admins: $adminList") // Debug
            //adminMapper.adminListToAdminResponseDTOList(adminList)
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
}
