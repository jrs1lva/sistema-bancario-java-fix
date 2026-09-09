package br.com.sistemabancariofix.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.sistemabancariofix.model.ContaCorrente;
import br.com.sistemabancariofix.model.Usuario;

public class ContaCorrenteTest {
	
	private Usuario usuario;
	private ContaCorrente conta;
	
	@BeforeEach
    public void setUp() {
        LocalDate data = LocalDate.now().minusYears(20);
        this.usuario = new Usuario("Adailton", "Jr", data, "86431700546");
        this.conta = new ContaCorrente(usuario);
    }
	
	@Test
	public void deveSacarComSucessoUsandoSaldoELimite() {
		assertTrue(conta.sacar(500));
		conta.depositar(500);
		assertTrue(conta.sacar(500));
	}
	
	@Test
    public void naoDevePermitirSaqueAcimaDoSaldoTotal() {
        assertThrows(IllegalArgumentException.class, () -> {
            conta.sacar(501.0);
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
	public void deveIncrementarLimiteAntesDeSaldo() {
		conta.sacar(500);
		conta.depositar(500);
		assertEquals(0, conta.getSaldo());
	}
	
	@Test
	public void deveDepositarComSucesso() {
		assertTrue(conta.depositar(2000000000));
		assertEquals(2000000000, conta.getSaldo());
	}
	
	@Test
    public void naoDevePermitirDepositoNegativoOuZerado() {
        assertThrows(IllegalArgumentException.class, () -> conta.depositar(0));
        assertThrows(IllegalArgumentException.class, () -> conta.depositar(-1));
    }
}
