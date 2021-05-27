package br.com.zup.chavepix

import br.com.zup.KeymanagerServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import javax.validation.Valid

@Validated
@Controller
class RegistraChaveController(private val grpcClient: KeymanagerServiceGrpc.KeymanagerServiceBlockingStub) {

    @Post(value = "/api/v1/clientes/{clienteId}/pix")
    fun cadastra(@PathVariable clienteId:String,@Valid @Body request:NovaChaveRequest): HttpResponse<Any> {
        val grpcResponse = grpcClient.send(request.convert(clienteId))
        return HttpResponse.created(location(clienteId,grpcResponse.message))
    }

    private fun location(clienteId: String,pixId:String) =
        HttpResponse.uri("/api/v1/clientes/$clienteId/pix/${pixId}")

}