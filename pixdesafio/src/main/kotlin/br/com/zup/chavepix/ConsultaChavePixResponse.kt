package br.com.zup.chavepix

import br.com.zup.ConsultaChaveResponse
import io.micronaut.core.annotation.Introspected
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Introspected
class ConsultaChavePixResponse(consultaChaveResponse:ConsultaChaveResponse) {
    
    val pixId = consultaChaveResponse.pixId
    val tipo = TipoDeChave.getEnum(consultaChaveResponse.chave.tipo).get_value()

    val chave = consultaChaveResponse.chave.chave

    val criadaEm = consultaChaveResponse.chave.criadoEm.let {
        LocalDateTime.ofEpochSecond(it.seconds,it.nanos, ZoneOffset.UTC) }

    val conta = mapOf(
        Pair("tipo",TipoDeConta.getEnum(consultaChaveResponse.chave.conta.tipo).get_value()),
        Pair("instituicao",consultaChaveResponse.chave.conta.instituicao),
        Pair("nomeTitular",consultaChaveResponse.chave.conta.nomeTitular),
        Pair("cpfTitular",consultaChaveResponse.chave.conta.cpfTitular),
        Pair("agencia",consultaChaveResponse.chave.conta.agencia),
        Pair("numero",consultaChaveResponse.chave.conta.numero)
    )
}