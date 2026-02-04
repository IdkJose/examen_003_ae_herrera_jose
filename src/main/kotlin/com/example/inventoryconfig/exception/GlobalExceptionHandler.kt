package com.example.inventoryconfig.exception

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFound(ex: NotFoundException, request: HttpServletRequest): ResponseEntity<ApiError> {
        return buildError(HttpStatus.NOT_FOUND, ex.message ?: "No encontrado", request)
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequest(ex: BadRequestException, request: HttpServletRequest): ResponseEntity<ApiError> {
        return buildError(HttpStatus.BAD_REQUEST, ex.message ?: "Solicitud inválida", request)
    }

    @ExceptionHandler(UnauthorizedActionException::class)
    fun handleUnauthorized(ex: UnauthorizedActionException, request: HttpServletRequest): ResponseEntity<ApiError> {
        return buildError(HttpStatus.UNAUTHORIZED, ex.message ?: "No autorizado", request)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class, BindException::class)
    fun handleValidation(ex: Exception, request: HttpServletRequest): ResponseEntity<ApiError> {
        val message = when (ex) {
            is MethodArgumentNotValidException -> ex.bindingResult.fieldErrors.firstOrNull()?.defaultMessage
            is BindException -> ex.bindingResult.fieldErrors.firstOrNull()?.defaultMessage
            else -> null
        } ?: "Validación fallida"
        return buildError(HttpStatus.BAD_REQUEST, message, request)
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneric(ex: Exception, request: HttpServletRequest): ResponseEntity<ApiError> {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno", request)
    }

    private fun buildError(status: HttpStatus, message: String, request: HttpServletRequest): ResponseEntity<ApiError> {
        val body = ApiError(
            status = status.value(),
            error = status.reasonPhrase,
            message = message,
            path = request.requestURI
        )
        return ResponseEntity.status(status).body(body)
    }
}
