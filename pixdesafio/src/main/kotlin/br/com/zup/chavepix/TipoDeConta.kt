package br.com.zup.chavepix

enum class TipoDeConta(val s: String) {
    CONTA_CORRENTE("CONTA_CORRENTE"),CONTA_POUPANCA("CONTA_POUPANCA"),UNKNOWN_CONTA("UNKNOWN_CONTA");

    fun get_value():String{
        return s;
    }

    companion object {
        fun getEnum(value:String):TipoDeConta{
            for(t:TipoDeConta in values()){
                if(value.equals(t.get_value())){
                    return t;
                }
            }
            return TipoDeConta.UNKNOWN_CONTA
        }
    }
}