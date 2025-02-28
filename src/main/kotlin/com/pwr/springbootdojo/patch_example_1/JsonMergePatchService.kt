package com.pwr.springbootdojo.patch_example_1

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service

@Service
class JsonMergePatchService(
    private val objectMapper: ObjectMapper
) {

    fun <T> applyMergePatch(patchBody: JsonNode, target: T, targetClass: Class<T>): T {
        // Create ObjectReader that ignores null values
        val objectReader = objectMapper.readerForUpdating(target)

        // Merge patchBody into target object
        return objectReader.readValue(patchBody, targetClass)
    }
}
