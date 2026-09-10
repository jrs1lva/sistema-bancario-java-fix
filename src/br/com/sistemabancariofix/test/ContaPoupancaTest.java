package br.com.sistemabancariofix.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.sistemabancariofix.model.Banco;
import br.com.sistemabancariofix.model.ContaPoupanca;
import br.com.sistemabancariofix.model.Usuario;

public class ContaPoupancaTest {
	
	private Usuario usuario;
	private ContaPoupanca conta;
	private Banco banco;
	
	private final PrintStream saidaPadrao = System.out;
    private final ByteArrayOutputStream fluxoSaida = new ByteArrayOutputStream();
	
	@BeforeEach
	public void setUp() {
		LocalDate data = LocalDate.now().minusYears(22);
		this.usuario = new Usuario("Adailton", "jr", data, "86431700546");
		this.conta = new ContaPoupanca(usuario);
		this.banco = new Banco("Santander", "4002");
		
		// Redireciona o System.out para a nossa variável em memória antes do teste
		System.setOut(new PrintStream(fluxoSaida));
	}
	
	@AfterEach
    public void tearDown() {
        // Restaura o console para o estado normal após o teste
        System.setOut(saidaPadrao);
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
	
	@Test
	public void deveMostrarComprovante() {
		
		conta.depositar(100.0); // O saldo vai ficar 102.0 devido ao rendimento de juros

        // Act
        conta.mostrarComprovante("Depósito", conta, 100.0, banco);

        // Assert
        String saidaDoConsole = fluxoSaida.toString();
        
        // Verifica se as informações vitais foram impressas na tela
        assertTrue(saidaDoConsole.contains("Banco: Santander"));
        assertTrue(saidaDoConsole.contains("Operação: Depósito"));
        assertTrue(saidaDoConsole.contains("Nome: Adailton"));
        assertTrue(saidaDoConsole.contains("CPF: 86431700546"));
        assertTrue(saidaDoConsole.contains("Idade: 22 Anos"));
	}
}
