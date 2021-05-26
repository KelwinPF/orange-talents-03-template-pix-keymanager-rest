package br.com.zup.chavepix

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class TipoDeChaveRequestTest {

    @Nested
    inner class ChaveAleatoriaTest{
        @Test
        fun naodeveservalidoquandochavefornulaouvazia(){
            val tipo = TipoDeChave.RANDOM
            assertFalse(tipo.isValid(null))
            assertFalse(tipo.isValid(""))
        }
        @Test
        fun deveservalido(){
            val tipo = TipoDeChave.RANDOM
            assertTrue(tipo.isValid("chave"))
        }
    }
    @Nested
    inner class EmailTest{
        @Test
        fun naodeveservalidoquandochavefornulavazia(){
            val tipo = TipoDeChave.EMAIL
            assertFalse(tipo.isValid(null))
            assertFalse(tipo.isValid(""))
        }
        @Test
        fun naodeveservalidoquandonaoeemail(){
            val tipo = TipoDeChave.EMAIL
            assertFalse(tipo.isValid("teste"))
            assertFalse(tipo.isValid("aadasdasd"))
        }
        @Test
        fun deveservalido(){
            val tipo = TipoDeChave.EMAIL
            assertTrue(tipo.isValid("teste@email.com"))
        }
    }
    @Nested
    inner class CelularTest{
        @Test
        fun naodeveservalidoquandochavefornulavazia(){
            val tipo = TipoDeChave.CELULAR
            assertFalse(tipo.isValid(null))
            assertFalse(tipo.isValid(""))
        }
        @Test
        fun naodeveservalidoquandonaoecelular(){
            val tipo = TipoDeChave.CELULAR
            assertFalse(tipo.isValid("123132"))
            assertFalse(tipo.isValid("aadasdasd"))
        }
        @Test
        fun deveservalido(){
            val tipo = TipoDeChave.CELULAR
            assertTrue(tipo.isValid("+5585128764025"))
        }
    }

    @Nested
    inner class CPFTest{
        @Test
        fun naodeveservalidoquandochavefornulavazia(){
            val tipo = TipoDeChave.CPF
            assertFalse(tipo.isValid(null))
            assertFalse(tipo.isValid(""))
        }
        @Test
        fun naodeveservalidoquandonaoecelular(){
            val tipo = TipoDeChave.CPF
            assertFalse(tipo.isValid("123132"))
            assertFalse(tipo.isValid("aadasdasd"))
        }
        @Test
        fun deveservalido(){
            val tipo = TipoDeChave.CPF
            assertTrue(tipo.isValid("12345678901"))
        }
    }

    @Nested
    inner class UnknownTest(){
        @Test
        fun sempredeveserinvalido(){
            val tipo = TipoDeChave.UNKNOWN_CHAVE
            assertFalse(tipo.isValid(null))
            assertFalse(tipo.isValid("baksasdbhasd"))
        }
    }

}