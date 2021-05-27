package br.com.zup.chavepix.shared.grpc

import br.com.zup.KeymanagerConsultaChaveServiceGrpc
import br.com.zup.KeymanagerListaServiceGrpc
import br.com.zup.KeymanagerRemoveServiceGrpc
import br.com.zup.KeymanagerServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class PixDesafioGrpcFactory(@GrpcChannel("keyManager") val channel:ManagedChannel) {
    @Singleton
    fun registraChave() = KeymanagerServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun deletaChave() = KeymanagerRemoveServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun carregaChave() = KeymanagerConsultaChaveServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun listaChave() = KeymanagerListaServiceGrpc.newBlockingStub(channel)
}