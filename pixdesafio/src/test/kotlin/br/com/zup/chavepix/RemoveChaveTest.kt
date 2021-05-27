package br.com.zup.chavepix

import br.com.zup.KeymanagerRemoveServiceGrpc
import br.com.zup.RemoveKeyResponse
import br.com.zup.chavepix.shared.grpc.PixDesafioGrpcFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.any
import org.mockito.Mockito
import javax.inject.Inject
import javax.inject.Singleton
import org.mockito.BDDMockito.given

@MicronautTest
internal class RemoveChaveTest {
    @field:Inject
    lateinit var removeStub: KeymanagerRemoveServiceGrpc.KeymanagerRemoveServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    internal fun deveremoverumachavepixexistente(){
        val clienteId = "c56dfef4-7901-44fb-84e2-a2cefb157890"
        val pixId = "2017"
        val respotaGrpc = RemoveKeyResponse.newBuilder().setMessage("sucesso").build()
        given(removeStub.remove(Mockito.any())).willReturn(respotaGrpc)

        val request = HttpRequest.DELETE<Any>("/api/v1/clientes/${clienteId}/pix/${pixId}")
        val response = client.toBlocking().exchange(request,Any::class.java)
        assertEquals(HttpStatus.OK,response.status)
    }


    @Factory
    @Replaces(factory = PixDesafioGrpcFactory::class)
    internal class Clients{
        @Singleton
        fun removestubMock() = Mockito.mock(KeymanagerRemoveServiceGrpc.KeymanagerRemoveServiceBlockingStub::class.java)

    }

}