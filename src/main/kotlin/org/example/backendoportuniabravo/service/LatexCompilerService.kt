package org.example.backendoportuniabravo.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File

@Service
class LatexCompilerService(
    @Value("\${latex.compiler.path}") private val latexCompilerPath: String
) {
    suspend fun compileToPdf(latexFile: File): File {
        return withContext(Dispatchers.IO) {
            val process = ProcessBuilder(
                latexCompilerPath,
                "-interaction=nonstopmode",
                "-output-directory=${latexFile.parent}",
                latexFile.absolutePath
            ).start()

            val exitCode = process.waitFor()
            if (exitCode != 0) {
                val error = process.errorStream.bufferedReader().readText()
                throw RuntimeException("LaTeX compilation failed: $error")
            }

            File(latexFile.absolutePath.replace(".tex", ".pdf"))
        }
    }
}