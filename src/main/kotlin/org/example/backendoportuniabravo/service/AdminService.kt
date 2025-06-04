package org.example.backendoportuniabravo.service

import org.example.backendoportuniabravo.dto.*



interface AdminService{
    fun getAdmins() : List<AdminResponseDTO>?
    fun getAdminById(id: Long) : AdminResponseDTO?
    fun addAdmin(id: Long) : Long?
    fun deleteAdmin(profileId: Long) : Boolean

}



