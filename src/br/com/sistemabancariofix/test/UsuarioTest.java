package br.com.sistemabancariofix.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import br.com.sistemabancariofix.model.Usuario;

public class UsuarioTest {
	
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
