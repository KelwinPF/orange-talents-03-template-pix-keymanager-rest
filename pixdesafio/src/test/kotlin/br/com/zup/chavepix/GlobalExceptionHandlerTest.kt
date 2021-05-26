package br.com.zup.chavepix

import br.com.zup.config.GlobalExceptionHandler
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.hateoas.JsonError
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

internal class GlobalExceptionHandlerTest {

    val requestgeneric = HttpRequest.GET<Any>("/")

    @Test
    fun deveretornar404quandostatusexceptionnotfound(){
        val mensagem = "nao existente"

        val notFoundException = StatusRuntimeException(Status.NOT_FOUND.withDescription(mensagem))

        val resposta = GlobalExceptionHandler().handle(requestgeneric,notFoundException)
        assertEquals(HttpStatus.NOT_FOUND,resposta.status)
        assertNotNull(resposta.body())
        assertEquals(mensagem,(resposta.body() as JsonError).message)

    }
    @Test
    fun deveretornar422quandostatusexceptionalreadyexists(){
        val mensagem = "nao existente"

        val alreadyexists = StatusRuntimeException(Status.ALREADY_EXISTS.withDescription(mensagem))

        val resposta = GlobalExceptionHandler().handle(requestgeneric,alreadyexists)
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY,resposta.status)
        assertNotNull(resposta.body())
        assertEquals(mensagem,(resposta.body() as JsonError).message)
    }

    @Test
    fun deveretornar400quandostatusexceptioninvalidargument(){
        val mensagem = "dados invalidos"

        val invalidargument = StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription(mensagem))

        val resposta = GlobalExceptionHandler().handle(requestgeneric,invalidargument)
        assertEquals(HttpStatus.BAD_REQUEST,resposta.status)
        assertNotNull(resposta.body())
        assertEquals(mensagem,(resposta.body() as JsonError).message)
    }

    @Test
    fun deveretornar500quandooutroerro(){
        val mesagem = "Não foi possivel completar a requisição"
        val outroerro = StatusRuntimeException(Status.UNKNOWN)
        val resposta = GlobalExceptionHandler().handle(requestgeneric,outroerro)

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,resposta.status)
        assertNotNull(resposta.body())
        assertEquals(mesagem,(resposta.body() as JsonError).message)
    }


}