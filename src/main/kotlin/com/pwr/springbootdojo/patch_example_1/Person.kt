package com.pwr.springbootdojo.patch_example_1

import jakarta.persistence.*

@Entity
@Table(name = "person")
data class Person(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false)
    var name: String? = null,

    @Column(nullable = false)
    var email: String? = null,
) {
    constructor() : this(0, "", "")
}