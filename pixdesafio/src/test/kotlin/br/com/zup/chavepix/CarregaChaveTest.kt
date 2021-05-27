package br.com.zup.chavepix

import br.com.zup.*
import br.com.zup.chavepix.shared.grpc.PixDesafioGrpcFactory
import com.google.protobuf.Timestamp
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import javax.inject.Inject
import javax.inject.Singleton
import org.mockito.BDDMockito.given
import java.time.LocalDateTime
import java.time.ZoneId

@MicronautTest
class CarregaChaveTest {

    @field:Inject
    lateinit var consultaGrpc: KeymanagerConsultaChaveServiceGrpc.KeymanagerConsultaChaveServiceBlockingStub

    @field:Inject
    lateinit var listaGrpc: KeymanagerListaServiceGrpc.KeymanagerListaServiceBlockingStub


    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    internal fun devecarregarchave(){
        val clientId  = "c56dfef4-7901-44fb-84e2-a2cefb157890"
        val pixId = "2017"
        given(consultaGrpc.consulta(Mockito.any())).willReturn(
            ConsultaChaveResponse.newBuilder()
                .setChave(
                    ConsultaChaveResponse.ChaveResponse.newBuilder().setConta(
                        ConsultaChaveResponse.ContaResponse.newBuilder()
                            .setTipo("CONTA_CORRENTE")
                            .setNumero("291900")
                            .setNomeTitular("Rafael M C Ponte")
                            .setInstituicao("ITAÚ UNIBANCO S.A.")
                            .setCpfTitular("02467781054")
                            .setAgencia("0001").build())
                        .setTipo("CELULAR")
                        .setChave("+5583128714075")
                        .setCriadoEm(LocalDateTime.now().let {
                            val created = it.atZone(ZoneId.of("UTC")).toInstant()
                            Timestamp.newBuilder().setSeconds(created.epochSecond).setNanos(created.nano).build()
                        }).build()).setClientId(clientId).setPixId(pixId).build())
        val request = HttpRequest.GET<Any>("/api/v1/clientes/${clientId}/pix/${pixId}")
        val response = client.toBlocking().exchange(request,Any::class.java)
        Assertions.assertEquals(HttpStatus.OK,response.status())
        Assertions.assertNotNull(response.body())
    }

    @Test
    internal fun develistarchaves(){
        val clientId  = "c56dfef4-7901-44fb-84e2-a2cefb157890"
        val respostaGrpc = listagemChaves(clientId)
        given(listaGrpc.lista(Mockito.any())).willReturn(respostaGrpc)

        val request = HttpRequest.GET<Any>("/api/v1/clientes/${clientId}/pix/")
        val response = client.toBlocking().exchange(request,List::class.java)

        Assertions.assertEquals(HttpStatus.OK,response.status())
        Assertions.assertNotNull(response)
        Assertions.assertEquals(response.body()!!.size,2)
    }

    private fun listagemChaves(clientId: String): ListaKeyResponse {
        val chaveEmail = ListaKeyResponse.ChavePix.newBuilder()
            .setPixId("2017")
            .setTipoChave(TipoChave.EMAIL)
            .setTipoConta(TipoConta.CONTA_CORRENTE)
            .setChave("teste@email.com")
            .setCriadoEm(LocalDateTime.now().let {
                val created = it.atZone(ZoneId.of("UTC")).toInstant()
                Timestamp.newBuilder().setSeconds(created.epochSecond).setNanos(created.nano).build()
            })
        val chaveCPF = ListaKeyResponse.ChavePix.newBuilder()
            .setPixId("2018")
            .setTipoChave(TipoChave.CPF)
            .setTipoConta(TipoConta.CONTA_CORRENTE)
            .setChave("09875545533")
            .setCriadoEm(LocalDateTime.now().let {
                val created = it.atZone(ZoneId.of("UTC")).toInstant()
                Timestamp.newBuilder().setSeconds(created.epochSecond).setNanos(created.nano).build()
            })
        return ListaKeyResponse.newBuilder().setClientId(clientId).addChaves(chaveCPF).addChaves(chaveEmail).build()
    }


    @Factory
    @Replaces(factory = PixDesafioGrpcFactory::class)
    internal class Clients{
        @Singleton
        fun consultastubMock() = Mockito.mock(KeymanagerConsultaChaveServiceGrpc.KeymanagerConsultaChaveServiceBlockingStub::class.java)
        @Singleton
        fun listastubMock() = Mockito.mock(KeymanagerListaServiceGrpc.KeymanagerListaServiceBlockingStub::class.java)
    }
}