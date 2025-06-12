package org.example.backendoportuniabravo.webService

import org.example.backendoportuniabravo.service.FileStorageService
import org.springframework.core.io.FileSystemResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.FileNotFoundException

@RestController
@RequestMapping("/downloads")
class FileDownloadController(
    private val fileStorageService: FileStorageService
) {
    @GetMapping("/{fileName}")
    fun downloadFile(@PathVariable fileName: String): ResponseEntity<FileSystemResource> {
        return try {
            val resource = fileStorageService.serveFile(fileName)
            ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header("Content-Disposition", "attachment; filename=\"$fileName\"")
                .body(resource)
        } catch (e: FileNotFoundException) {
            ResponseEntity.notFound().build()
        }
    }
}