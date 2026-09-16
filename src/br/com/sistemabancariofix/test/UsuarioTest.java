package br.com.sistemabancariofix.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import br.com.sistemabancariofix.model.Usuario;

public class UsuarioTest {
	
	@Test
	public void deveInstanciarUsuarioComSucesso() {
		LocalDate data = LocalDate.now().minusYears(22);
		Usuario usuario = new Usuario("Adailton", "jr", data, "86431700546");
		
		assertNotNull(usuario);
		assertEquals("Adailton", usuario.getNome());
		assertEquals(22, usuario.getIdade());
	}
	
	@Test
	public void naoDeveInstanciarUsuarioMenorDeIdade() {
		LocalDate dataInvalida = LocalDate.now().minusYears(17);
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Usuario("Adailton", "Jr", dataInvalida, "86431700546");
        });
        
        assertEquals("É necessário ser maior de idade para criar uma conta", exception.getMessage());
	}
	
	@Test
	public void naoDeveInstanciarUsuarioComCpfInvalido() {
		LocalDate data = LocalDate.now().minusYears(22);
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Usuario("Adailton", "Jr", data, "40028922572");
        });
		
		assertEquals("Erro fatal: Tentativa de instanciar Usuario com CPF inválido.", exception.getMessage());
	}
	
	@Test
	public void deveValidarCpfCorretoEIncorreto() {
		assertTrue(Usuario.isCpfValido("86431700546"));
		assertFalse(Usuario.isCpfValido("11111111111"));
		assertFalse(Usuario.isCpfValido("12345678910"));
		assertTrue(Usuario.isCpfValido("86------------4.317/005=46"));
	}
	
	@Test
	public void deveVerificarMaioridadeComSucesso() {
		LocalDate dataMaior = LocalDate.now().minusYears(19);
		LocalDate dataIgual = LocalDate.now().minusYears(18);
		LocalDate dataMenor = LocalDate.now().minusYears(17);
		
		assertTrue(Usuario.isMaiorDeIdade(dataMaior));
		assertTrue(Usuario.isMaiorDeIdade(dataIgual));
		assertFalse(Usuario.isMaiorDeIdade(dataMenor));
	}
}
