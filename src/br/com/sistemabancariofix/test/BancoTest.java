package br.com.sistemabancariofix.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.sistemabancariofix.model.Banco;
import br.com.sistemabancariofix.model.Conta;
import br.com.sistemabancariofix.model.ContaCorrente;
import br.com.sistemabancariofix.model.ContaPoupanca;
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
	    banco.cadastrarUsuario(this.usuario);

	    conta = banco.criarConta("86431700546", Tipo.CORRENTE);

	    assertNotNull(conta);
	    assertInstanceOf(ContaCorrente.class, conta);
	    assertEquals(this.usuario, conta.getUsuario());
	    
	    LocalDate dataItalo = LocalDate.now().minusYears(21);
		Usuario italo = new Usuario("Italo", "Cecca", dataItalo, "44121547500");
		banco.cadastrarUsuario(italo);

	    Conta contaItalo = banco.criarConta("44121547500", Tipo.POUPANCA);

	    assertNotNull(contaItalo);
	    assertInstanceOf(ContaPoupanca.class, contaItalo);
	    assertEquals(italo, contaItalo.getUsuario());
	}
	
	@Test
	public void deveExistirUsuario() {
		banco.cadastrarUsuario(this.usuario);
		
		assertTrue(banco.existeUsuario("86431700546"));
	}
	
	@Test
	public void naoDeveExistirUsuario() {
		banco.cadastrarUsuario(this.usuario);
		
		assertFalse(banco.existeUsuario("86431700548"));
	}
	
	@Test
	public void deveBuscarConta() {
		banco.cadastrarUsuario(this.usuario);
		banco.criarConta("86431700546", Tipo.CORRENTE);
		
		assertNotNull(banco.buscarConta(this.conta.getId()));
	}
	
	@Test
	public void naoDeveBuscarConta() {
		banco.cadastrarUsuario(this.usuario);
		banco.criarConta("86431700546", Tipo.CORRENTE);
		
		assertNull(banco.buscarConta(8));
	}
	
	@Test
	public void deveBuscarUsuario() {
		banco.cadastrarUsuario(this.usuario);
		
		assertNotNull(banco.buscarUsuario(this.usuario.getCPF()));
	}
	
	@Test
	public void naoDeveBuscarUsuario() {
		banco.cadastrarUsuario(this.usuario);
		
		assertNull(banco.buscarUsuario("4008922"));
	}
	
	@Test
	public void deveListarTodosUsuarios() {
		banco.cadastrarUsuario(this.usuario);
		
		LocalDate dataItalo = LocalDate.now().minusYears(21);
		Usuario italo = new Usuario("Italo", "Cecca", dataItalo, "44121547500");
		banco.cadastrarUsuario(italo);
		
		LocalDate dataLucas = LocalDate.now().minusYears(26);
		Usuario lucas = new Usuario("Lucas", "Boogie", dataLucas, "69990530572");
		banco.cadastrarUsuario(lucas);
		
		LocalDate dataMauricio = LocalDate.now().minusYears(24);
		Usuario mauricio = new Usuario("Mauricio", "MauMau", dataMauricio, "201.502.500-61");
		banco.cadastrarUsuario(mauricio);
		
		assertNotNull(banco.listarUsuarios());
		assertEquals(4, banco.listarUsuarios().size());
		
		assertTrue(banco.listarUsuarios().contains(usuario));
		assertTrue(banco.listarUsuarios().contains(italo));
		assertTrue(banco.listarUsuarios().contains(mauricio));
		assertTrue(banco.listarUsuarios().contains(lucas));
	}
	
	@Test
	public void naoDeveListarUsuarios() {
//		banco.cadastrarUsuario(this.usuario);
//		
//		LocalDate dataItalo = LocalDate.now().minusYears(21);
//		Usuario italo = new Usuario("Italo", "Cecca", dataItalo, "44121547500");
//		banco.cadastrarUsuario(italo);
//		
//		LocalDate dataLucas = LocalDate.now().minusYears(26);
//		Usuario lucas = new Usuario("Lucas", "Boogie", dataLucas, "69990530572");
//		banco.cadastrarUsuario(lucas);
//		
//		LocalDate dataMauricio = LocalDate.now().minusYears(24);
//		Usuario mauricio = new Usuario("Mauricio", "MauMau", dataMauricio, "201.502.500-61");
//		banco.cadastrarUsuario(mauricio);
		
		assertTrue(banco.listarUsuarios().isEmpty());
		assertEquals(0, banco.listarUsuarios().size());
		
		assertFalse(banco.listarUsuarios().contains(usuario));
	}
	
	@Test
	public void deveListarTodasContas() {
		banco.cadastrarUsuario(this.usuario);
		banco.criarConta(usuario.getCPF(), Tipo.CORRENTE);
		
		LocalDate dataItalo = LocalDate.now().minusYears(21);
		Usuario italo = new Usuario("Italo", "Cecca", dataItalo, "44121547500");
		banco.cadastrarUsuario(italo);
		banco.criarConta("44121547500", Tipo.CORRENTE);
		
		LocalDate dataLucas = LocalDate.now().minusYears(26);
		Usuario lucas = new Usuario("Lucas", "Boogie", dataLucas, "69990530572");
		banco.cadastrarUsuario(lucas);
		banco.criarConta("69990530572", Tipo.POUPANCA);
		
		LocalDate dataMauricio = LocalDate.now().minusYears(24);
		Usuario mauricio = new Usuario("Mauricio", "MauMau", dataMauricio, "201.502.500-61");
		banco.cadastrarUsuario(mauricio);
		banco.criarConta("201.502.500-61", Tipo.POUPANCA);
		
		assertNotNull(banco.listarContas());
		assertEquals(4, banco.listarContas().size());
		
		assertTrue(banco.listarContas().contains(banco.buscarConta(usuario.getId())));
		assertTrue(banco.listarContas().contains(banco.buscarConta(italo.getId())));
		assertTrue(banco.listarContas().contains(banco.buscarConta(mauricio.getId())));
		assertTrue(banco.listarContas().contains(banco.buscarConta(lucas.getId())));
	}
	
	@Test
	public void naoDeveListarContas() {
		assertTrue(banco.listarContas().isEmpty());
		assertEquals(0, banco.listarContas().size());
		
		assertFalse(banco.listarContas().contains(conta));
	}
	
 }
