package com.example.inventoryconfig.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class InventoryRuleRequest(
    @field:NotBlank(message = "name es requerido")
    val name: String,
    val description: String? = null,
    @field:NotNull(message = "isActive es requerido")
    val isActive: Boolean
)
