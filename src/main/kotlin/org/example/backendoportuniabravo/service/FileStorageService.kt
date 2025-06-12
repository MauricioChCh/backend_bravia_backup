package org.example.backendoportuniabravo.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.web.ServerProperties
import org.springframework.core.io.FileSystemResource
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@Service
class FileStorageService(
    @Value("\${file.storage.directory}") private val storageDirectory: String
) {
    suspend fun saveLatexFile(studentId: Long, content: String): File {
        val fileName = "cv_${studentId}_${System.currentTimeMillis()}.tex"
        val filePath = Paths.get(storageDirectory, fileName)

        return withContext(Dispatchers.IO) {
            Files.write(filePath, content.toByteArray())
            filePath.toFile()
        }
    }

    suspend fun savePdfFile(studentId: Long, pdfFile: File): String {
        val fileName = "cv_${studentId}_${System.currentTimeMillis()}.pdf"
        val destPath = Paths.get(storageDirectory, fileName)

        return withContext(Dispatchers.IO) {
            Files.copy(pdfFile.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING)
            "/downloads/$fileName" // URL relativa para descargar
        }
    }

    // Método para servir los archivos (añadir en otro controlador)

    fun serveFile(fileName: String): FileSystemResource {
        val file = Paths.get(storageDirectory, fileName).toFile()
        if (!file.exists()) throw FileNotFoundException()
        return FileSystemResource(file)
    }
}