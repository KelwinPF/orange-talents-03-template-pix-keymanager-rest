package br.com.zup.chavepix

import br.com.zup.KeyRequest
import br.com.zup.TipoChave
import br.com.zup.TipoConta

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@ValidaChave
class NovaChaveRequest(@field:NotNull val tipoConta: String?
,@field:NotNull val tipoChave: String?, @field:NotBlank @Size(max=77) val chave:String?) {

    fun convert(cliendId:String): KeyRequest{
        return KeyRequest.newBuilder().setIdCliente(cliendId)
            .setTipoChave(TipoChave.valueOf(TipoDeChave.getEnum(tipoChave!!).get_value()))
            .setTipoConta(TipoConta.valueOf(TipoDeConta.getEnum(tipoConta!!).get_value()))
            .setChave(chave?:"").build()
    }
}