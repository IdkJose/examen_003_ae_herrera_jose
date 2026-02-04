package com.example.inventoryconfig.service

import com.example.inventoryconfig.dto.InventoryRuleRequest
import com.example.inventoryconfig.dto.InventoryRuleResponse
import com.example.inventoryconfig.exception.NotFoundException
import com.example.inventoryconfig.mapper.InventoryRuleMapper
import com.example.inventoryconfig.repository.InventoryRuleRepository
import com.example.inventoryconfig.security.CurrentUserProvider
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InventoryRuleService(
    private val repository: InventoryRuleRepository,
    private val currentUserProvider: CurrentUserProvider
) {

    private val logger = LoggerFactory.getLogger(InventoryRuleService::class.java)

    @Transactional(readOnly = true)
    fun list(): List<InventoryRuleResponse> {
        return repository.findAll().map { InventoryRuleMapper.toResponse(it) }
    }

    @Transactional(readOnly = true)
    fun getById(id: Long): InventoryRuleResponse {
        val entity = repository.findById(id).orElseThrow {
            NotFoundException("InventoryRule $id no existe")
        }
        return InventoryRuleMapper.toResponse(entity)
    }

    @Transactional
    fun create(request: InventoryRuleRequest): InventoryRuleResponse {
        val userId = currentUserProvider.getUserId()
        val entity = InventoryRuleMapper.toEntity(request, userId)
        val saved = repository.save(entity)
        return InventoryRuleMapper.toResponse(saved)
    }

    @Transactional
    fun update(id: Long, request: InventoryRuleRequest): InventoryRuleResponse {
        val entity = repository.findById(id).orElseThrow {
            NotFoundException("InventoryRule $id no existe")
        }
        val userId = currentUserProvider.getUserId()
        InventoryRuleMapper.updateEntity(entity, request, userId)
        return InventoryRuleMapper.toResponse(entity)
    }

    @Transactional
    fun toggle(id: Long): InventoryRuleResponse {
        val entity = repository.findById(id).orElseThrow {
            NotFoundException("InventoryRule $id no existe")
        }
        val userId = currentUserProvider.getUserId()
        entity.isActive = !entity.isActive
        entity.updatedBy = userId
        return InventoryRuleMapper.toResponse(entity)
    }

    @Transactional
    fun delete(id: Long) {
        val entity = repository.findById(id).orElseThrow {
            NotFoundException("InventoryRule $id no existe")
        }
        val userId = currentUserProvider.getUserId()
        logger.info("Intento de eliminación InventoryRule id={} por userId={}", id, userId)
        repository.delete(entity)
    }
}
