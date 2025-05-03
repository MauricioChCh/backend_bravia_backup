package org.example.backendoportuniabravo.service

import org.example.backendoportuniabravo.dto.*
import org.example.backendoportuniabravo.mapper.AdminMapper
import org.example.backendoportuniabravo.repository.AdminRepository
import org.example.backendoportuniabravo.repository.ProfileRepository
import org.example.backendoportuniabravo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


interface AdminService{
    fun getAdmins() : List<AdminResponseDTO>?
    fun getAdminById(id: Long) : AdminResponseDTO?
    fun addAdmin(id: Long) : Long?

}


@Service
class AdminServiceImpl(
    @Autowired
    private val adminRepository: AdminRepository,
    @Autowired
    private val adminMapper: AdminMapper,
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val profileRepository: ProfileRepository,

): AdminService {
    override fun getAdmins(): List<AdminResponseDTO>? {
        return try {
            val adminList = adminRepository.findAll()
            adminMapper.adminListToAdminResponseDTOList(adminList)
        } catch (e: Exception) {
            throw RuntimeException("Error al obtener la lista de administradores", e)
        }

    }

    override fun getAdminById(id: Long): AdminResponseDTO? {
        return try {
            val adminFound = adminRepository.findById(id)
            adminFound.map { adminMapper.adminToAdminResponseDTO(it) }.orElse(null)
        } catch (e: Exception) {
            throw RuntimeException("Error al obtener el administrador", e)
        }
    }

    override fun addAdmin(id: Long): Long? {
        TODO("Not yet implemented")
    }


}