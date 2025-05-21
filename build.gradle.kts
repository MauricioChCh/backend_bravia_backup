plugins {
	application
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("plugin.jpa") version "1.9.25"
	kotlin("kapt") version "1.9.25"
}

group = "org.example"
version = "1.0"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}


repositories {
	mavenCentral()
}

dependencies {
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-mustache")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	//implentacionde de la IA
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

	implementation("org.springframework.boot:spring-boot-starter-security")
	testImplementation("org.springframework.security:spring-security-test")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("org.postgresql:postgresql:42.7.3")

	testImplementation("org.testcontainers:postgresql:1.19.3")
	testImplementation("org.testcontainers:junit-jupiter")

	implementation ("org.mapstruct:mapstruct:1.6.3")
//	annotationProcessor ("org.mapstruct:mapstruct-processorimplementation("org.postgresql:postgresql:42.7.3")  // ¡Versión reciente!:1.6.3")
	kapt("org.mapstruct:mapstruct-processor:1.6.3")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		jvmTarget = "21"
		freeCompilerArgs = listOf("-Xjsr305=strict")
	}
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
	launchScript()
}
