package com.brzozowski.springpetclinic

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class SpringPetClinicApplication

fun main(args: Array<String>) {
    SpringApplication.run(SpringPetClinicApplication::class.java, *args)
}
