package br.com.sistemabancariofix.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.sistemabancariofix.model.Banco;
import br.com.sistemabancariofix.model.Conta;
import br.com.sistemabancariofix.model.ContaCorrente;
import br.com.sistemabancariofix.model.Tipo;
import br.com.sistemabancariofix.model.Usuario;

public class BancoTest {
	
	private Usuario usuario;
	private Conta conta;
	private Banco banco;
	
	@BeforeEach
	public void setUp() {
		this.banco = new Banco("Bradesco", "4002");
		LocalDate data = LocalDate.now().minusYears(22);
		this.usuario = new Usuario("Adailton", "Jr", data, "86431700546");
		this.conta = new ContaCorrente(usuario);
	}
	
	@Test
	public void naoDeveCadastrarUsuario() {
		LocalDate data = LocalDate.now().minusYears(22);
		Usuario usuario = new Usuario("Adailton", "Jr", data, "86431700546");
		
		this.banco.cadastrarUsuario(this.usuario);
		
		assertThrows(IllegalArgumentException.class, () -> {
			banco.cadastrarUsuario(usuario);
		});
	}
	
	@Test
	public void deveCadastrarUsuario() {
		assertTrue(banco.cadastrarUsuario(this.usuario));
	}
		
	
	@Test
	public void naoDeveCriarConta() {
		assertThrows(IllegalArgumentException.class, () -> {
			banco.criarConta("86431700547", Tipo.CORRENTE);
		});
		
	}
	
	@Test
	public void deveCriarConta() {
	    // 1. Arrange: Garante que o usuário está no banco para ser encontrado
	    banco.cadastrarUsuario(this.usuario);

	    // 2. Act: Executa o método e guarda o resultado
	    Conta contaCriada = banco.criarConta("86431700546", Tipo.CORRENTE);

	    // 3. Assert: Valida as propriedades da conta gerada
	    assertNotNull(contaCriada);
	    assertInstanceOf(ContaCorrente.class, contaCriada);
	    assertEquals(this.usuario, contaCriada.getUsuario());
	}
 }
