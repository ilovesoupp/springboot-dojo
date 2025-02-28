package com.pwr.springbootdojo.patch_example_1

import org.springframework.stereotype.Service

@Service
class PersonService(private val personRepository: PersonRepository) {

    fun findAll(): List<Person> = personRepository.findAll()

    fun findById(id: Long): Person? {
        return personRepository.findById(id).orElse(null)
    }

    fun save(person: Person): Person {
        return personRepository.save(person)
    }
}