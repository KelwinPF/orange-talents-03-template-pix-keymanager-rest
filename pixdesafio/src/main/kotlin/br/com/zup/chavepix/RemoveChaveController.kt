package br.com.zup.chavepix

import br.com.zup.KeymanagerRemoveServiceGrpc
import br.com.zup.RemoveKeyRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.PathVariable
import io.micronaut.validation.Validated

@Validated
@Controller
class RemoveChaveController(
    private val grpcClient:KeymanagerRemoveServiceGrpc.KeymanagerRemoveServiceBlockingStub) {

    @Delete(value = "/api/v1/clientes/{clienteId}/pix/{pixId}")
    fun delete(@PathVariable clienteId:String,@PathVariable pixId:String):HttpResponse<Any>{

        grpcClient.remove(
            RemoveKeyRequest.newBuilder()
                .setClientId(clienteId)
                .setPixId(pixId).build())

        return HttpResponse.ok()
    }
}