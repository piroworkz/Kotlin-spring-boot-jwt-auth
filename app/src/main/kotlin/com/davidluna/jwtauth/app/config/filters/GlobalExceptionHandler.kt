package com.davidluna.jwtauth.app.config.filters

import com.davidluna.jwtauth.app.controller.buildFailResponse
import com.davidluna.jwtauth.app.controller.toJson
import com.davidluna.jwtauth.domain.AppError
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.servlet.http.HttpServletResponse

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(AppError::class)
    fun handleJwtException(ex: AppError, response: HttpServletResponse) {
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        response.writer.write(ex.buildFailResponse().toJson())
    }

}