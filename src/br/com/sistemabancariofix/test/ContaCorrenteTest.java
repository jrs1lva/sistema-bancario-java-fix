package br.com.sistemabancariofix.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import br.com.sistemabancariofix.model.ContaCorrente;
import br.com.sistemabancariofix.model.Usuario;

public class ContaCorrenteTest {
	
	@Test
	public void deveSacarComSucessoUsandoSaldoELimite() {
		LocalDate data = LocalDate.now().minusYears(20);
		Usuario usuario = new Usuario("Adailton", "Jr", data, "86431700546");
		ContaCorrente conta = new ContaCorrente(usuario);
		
		assertTrue(conta.sacar(500));
		
		conta.depositar(500);
		assertTrue(conta.sacar(500));
	}
	
	@Test
    public void naoDevePermitirSaqueAcimaDoSaldoTotal() {
        LocalDate data = LocalDate.now().minusYears(20);
        Usuario usuario = new Usuario("Adailton", "Jr", data, "86431700546");
        ContaCorrente conta = new ContaCorrente(usuario);
        
        assertThrows(IllegalArgumentException.class, () -> {
            conta.sacar(501.0);
        });
    }
	
	
	@Test
	public void deveDepositarComSucesso() {
		LocalDate data = LocalDate.now().minusYears(20);
		Usuario usuario = new Usuario("Adailton", "Jr", data, "86431700546");
		ContaCorrente conta = new ContaCorrente(usuario);
		
		assertTrue(conta.depositar(2000000000));
		assertEquals(2000000000, conta.getSaldo());
	}
	
	@Test
    public void naoDevePermitirDepositoNegativoOuZerado() {
        LocalDate data = LocalDate.now().minusYears(20);
        Usuario usuario = new Usuario("Adailton", "Jr", data, "86431700546");
        ContaCorrente conta = new ContaCorrente(usuario);
        
        assertThrows(IllegalArgumentException.class, () -> conta.depositar(0));
        assertThrows(IllegalArgumentException.class, () -> conta.depositar(-1));
    }
}
