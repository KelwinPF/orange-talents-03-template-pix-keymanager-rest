package br.com.zup.chavepix

import br.com.zup.KeyResponse
import br.com.zup.KeymanagerServiceGrpc
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
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class RegistraChaveTest {

    @field:Inject
    lateinit var registrastub:KeymanagerServiceGrpc.KeymanagerServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    internal fun deveregistrarchavepix(){
        val clienteid="c56dfef4-7901-44fb-84e2-a2cefb157890"

        val response = KeyResponse.newBuilder().setMessage("sucesso").build()

        given(registrastub.send(Mockito.any())).willReturn(response)

        val novaChave = NovaChaveRequest(tipoConta = "CONTA_CORRENTE",tipoChave = "CPF",chave = "09871153399")

        val request = HttpRequest.POST("/api/v1/clientes/${clienteid}/pix",novaChave)
        val responsee = client.toBlocking().exchange(request,NovaChaveRequest::class.java)
        assertEquals(HttpStatus.CREATED,responsee.status)

    }


    @Factory
    @Replaces(factory = PixDesafioGrpcFactory::class)
    internal class Clients{
        @Singleton
        fun stubMock() = Mockito.mock(KeymanagerServiceGrpc.KeymanagerServiceBlockingStub::class.java)

    }

}