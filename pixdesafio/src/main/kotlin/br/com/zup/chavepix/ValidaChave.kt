package br.com.zup.chavepix

import javax.inject.Singleton
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ChaveValidator::class])
annotation class ValidaChave(
    val message: String = "Chave invalida",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = [],
)

@Singleton
class ChaveValidator:ConstraintValidator<ValidaChave,NovaChaveRequest>{
    override fun isValid(value: NovaChaveRequest?, context: ConstraintValidatorContext?): Boolean {
        if(value?.tipoChave == null){
            return false;
        }

        return TipoDeChave.getEnum(value.tipoChave).isValid(value.chave)
    }

}