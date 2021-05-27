package br.com.zup.chavepix

import br.com.zup.ListaKeyResponse
import java.time.LocalDateTime
import java.time.ZoneOffset

class ListaChavePixResponse(chave: ListaKeyResponse.ChavePix) {
    val id = chave.pixId;
    val chave = chave.chave;
    val tipoChave = TipoDeChave.getEnum(chave.tipoChave.toString()).get_value()
    val tipoConta = TipoDeConta.getEnum(chave.tipoConta.toString()).get_value()
    val criadaEm = chave.criadoEm.let {
        LocalDateTime.ofEpochSecond(it.seconds,it.nanos, ZoneOffset.UTC) }
}
