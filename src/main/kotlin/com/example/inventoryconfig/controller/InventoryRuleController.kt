package com.example.inventoryconfig.controller

import com.example.inventoryconfig.dto.InventoryRuleRequest
import com.example.inventoryconfig.dto.InventoryRuleResponse
import com.example.inventoryconfig.service.InventoryRuleService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/rules")
class InventoryRuleController(
    private val service: InventoryRuleService
) {

    @GetMapping
    fun list(): List<InventoryRuleResponse> = service.list()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): InventoryRuleResponse = service.getById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody request: InventoryRuleRequest): InventoryRuleResponse = service.create(request)

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody request: InventoryRuleRequest
    ): InventoryRuleResponse = service.update(id, request)

    @PatchMapping("/{id}/toggle")
    fun toggle(@PathVariable id: Long): InventoryRuleResponse = service.toggle(id)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) {
        service.delete(id)
    }
}
