package com.example.inventoryconfig.repository

import com.example.inventoryconfig.model.InventoryRule
import org.springframework.data.jpa.repository.JpaRepository

interface InventoryRuleRepository : JpaRepository<InventoryRule, Long> {
    fun findByIsActiveTrue(): List<InventoryRule>
    fun findByNameContainingIgnoreCase(name: String): List<InventoryRule>
}
