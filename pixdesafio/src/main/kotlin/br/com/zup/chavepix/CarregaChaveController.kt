package br.com.zup.chavepix

import br.com.zup.*
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.validation.Validated

@Validated
@Controller
class CarregaChaveController(
    private val grpcConsulta: KeymanagerConsultaChaveServiceGrpc.KeymanagerConsultaChaveServiceBlockingStub,
    private val grpcLista:KeymanagerListaServiceGrpc.KeymanagerListaServiceBlockingStub) {

    @Get("/api/v1/clientes/{clienteId}/pix/{pixId}")
    fun consulta(@PathVariable clienteId:String, @PathVariable pixId:String):HttpResponse<Any>{
        val response = grpcConsulta.consulta(ConsultaChaveRequest.newBuilder()
            .setPixId(ConsultaChaveRequest.FiltroPorPixId.newBuilder()
                .setClientId(clienteId)
                .setPixId(pixId)
                .build())
            .build())
        return HttpResponse.ok(ConsultaChavePixResponse(response))
    }

    @Get("/api/v1/clientes/{clienteId}/pix/")
    fun lista(@PathVariable clienteId:String):HttpResponse<Any>{
        val pix = grpcLista.lista(ListaKeyRequest.newBuilder()
            .setClienteId(clienteId).build())
        val chaves = pix.chavesList.map { ListaChavePixResponse(it) }
        return HttpResponse.ok(chaves)
    }
}