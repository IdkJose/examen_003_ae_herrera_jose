package com.example.inventoryconfig.mapper

import com.example.inventoryconfig.dto.InventoryRuleRequest
import com.example.inventoryconfig.dto.InventoryRuleResponse
import com.example.inventoryconfig.model.InventoryRule

object InventoryRuleMapper {
    fun toEntity(request: InventoryRuleRequest, updatedBy: String): InventoryRule {
        return InventoryRule(
            name = request.name,
            description = request.description,
            isActive = request.isActive,
            updatedBy = updatedBy
        )
    }

    fun updateEntity(entity: InventoryRule, request: InventoryRuleRequest, updatedBy: String) {
        entity.name = request.name
        entity.description = request.description
        entity.isActive = request.isActive
        entity.updatedBy = updatedBy
    }

    fun toResponse(entity: InventoryRule): InventoryRuleResponse {
        return InventoryRuleResponse(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            isActive = entity.isActive,
            updatedBy = entity.updatedBy,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }
}
