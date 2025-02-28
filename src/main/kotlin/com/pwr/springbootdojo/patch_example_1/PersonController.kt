package com.pwr.springbootdojo.patch_example_1

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.json.JsonMergePatch
import javax.json.JsonValue

@RestController
@RequestMapping("/person")
class PersonController(
    private val personService: PersonService,
    private val objectMapper: ObjectMapper,
    private val jsonMergePatchService: JsonMergePatchService
) {

    @GetMapping("/hello")
    fun hello(): String {
        return "hello world"
    }

    @GetMapping("/")
    fun getAll(): ResponseEntity<List<Person>> {
        return ResponseEntity.ok(personService.findAll())
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: Long): ResponseEntity<Person> {
        val p = personService.findById(id)
        return ResponseEntity.ok(p)
    }

    @PostMapping("/")
    fun post(@RequestBody person: Person): ResponseEntity<Person> {
        val p = personService.save(person)
        return ResponseEntity.ok(p)
    }

    @PutMapping("/{id}")
    fun updateById(@PathVariable("id") id: Long, @RequestBody requestBody: Person): ResponseEntity<Person> {
        val person = personService.findById(id) ?: return ResponseEntity.notFound().build()

        var updatedPerson = objectMapper.convertValue(requestBody, Person::class.java)
        updatedPerson.id = person.id

        updatedPerson = personService.save(updatedPerson)
        return ResponseEntity.ok(updatedPerson)
    }

    @PatchMapping("/{id}", consumes = ["application/merge-patch+json"])
    fun patch(
        @PathVariable("id") id: Long,
        @RequestBody patchBody: JsonNode
    ): ResponseEntity<Person> {
        val person = personService.findById(id) ?: return ResponseEntity.notFound().build()
        val patched = jsonMergePatchService.applyMergePatch(patchBody, person, Person::class.java)
        val saved = personService.save(patched)

        return ResponseEntity.ok(saved)
    }
}