package br.com.sistemabancariofix.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.sistemabancariofix.model.ContaPoupanca;
import br.com.sistemabancariofix.model.Usuario;

public class ContaPoupancaTest {
	
	private Usuario usuario;
	private ContaPoupanca conta;
	
	@BeforeEach
	public void setUp() {
		LocalDate data = LocalDate.now().minusYears(22);
		this.usuario = new Usuario("Adailton", "jr", data, "86431700546");
		this.conta = new ContaPoupanca(usuario);
	}
	
	@Test
	public void deveSacarComSucesso() {
		conta.depositar(50);
		assertTrue(conta.sacar(50));
		conta.depositar(50);
		assertTrue(conta.sacar(49));
	}
	
	@Test
	public void naoDevePermitirSaqueAcimaDoSaldoTotal() {
		conta.depositar(100);
		assertThrows(IllegalArgumentException.class, () -> {
			conta.sacar(103);
		});
	}
	
	@Test
	public void naoDevePermitirSaqueNegativo() {
		conta.depositar(100);
		assertThrows(IllegalArgumentException.class, () -> {
			conta.sacar(-10);
		});
	}
	
	@Test
	public void deveDepositarComSucesso() {
		assertTrue(conta.depositar(2000000000));
		assertEquals((2000000000 * 1.02), conta.getSaldo());
	}
	
	@Test
    public void naoDevePermitirDepositoNegativoOuZerado() {
        assertThrows(IllegalArgumentException.class, () -> conta.depositar(0));
        assertThrows(IllegalArgumentException.class, () -> conta.depositar(-1));
    }
}
