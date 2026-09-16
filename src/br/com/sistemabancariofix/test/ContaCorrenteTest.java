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
import br.com.sistemabancariofix.model.ContaCorrente;
import br.com.sistemabancariofix.model.Usuario;

public class ContaCorrenteTest {
	
	private Usuario usuario;
	private ContaCorrente conta;
	private Banco banco;
	
	private final PrintStream saidaPadrao = System.out;
    private final ByteArrayOutputStream fluxoSaida = new ByteArrayOutputStream();
	
	@BeforeEach
    public void setUp() {
        LocalDate data = LocalDate.now().minusYears(22);
        this.usuario = new Usuario("Adailton", "Jr", data, "86431700546");
        this.conta = new ContaCorrente(usuario);
        this.banco = new Banco("Santander", "4002");
        
        System.setOut(new PrintStream(fluxoSaida));
    }
	
	@AfterEach
    public void tearDown() {
        // Restaura o console para o estado normal após o teste
        System.setOut(saidaPadrao);
    }
	
	@Test
	public void deveSacarComSucessoSemLimite() {
		conta.depositar(1000);
		assertTrue(conta.sacar(900));
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
	public void deveRestaurarLimiteEAdicionarSobraAoSaldo() {
		conta.sacar(200);
		conta.depositar(600);
		assertEquals(400, conta.getSaldo());
		assertEquals(500, conta.getLimite());
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
        assertTrue(saidaDoConsole.contains("Limite disponível: R$ 500,00"));
	}
	
	@Test
	public void deveMostrarInfosEspecificas() {
		conta.mostrarInfosEspecificas();
		String saidaDoConsole = fluxoSaida.toString();
		assertTrue(saidaDoConsole.contains("Limite disponível: R$ 500,00"));
	}
}
