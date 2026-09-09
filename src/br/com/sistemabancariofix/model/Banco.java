package br.com.sistemabancariofix.model;

import java.util.ArrayList;
import java.util.List;

public class Banco {
	
	private final String NOME;
    private final String AGENCIA;

    private List<Usuario> usuarios;
    private List<Conta> contas;
    
	public Banco(String nome, String agencia) {
		this.NOME = nome;
		this.AGENCIA = agencia;
		this.usuarios = new ArrayList<Usuario>();
		this.contas = new ArrayList<Conta>();
	}
	
    public boolean cadastrarUsuario(Usuario usuario) {
    	if (existeUsuario(usuario.getCPF())) {
    		throw new IllegalArgumentException("[ERRO] CPF já cadastrado!");
    	}
    	
    	usuarios.add(usuario);
    	return true;
    }
	
    public Conta criarConta(String cpf, Tipo tipo) {
    	Usuario usuario = buscarUsuario(cpf);
    	
    	if (usuario == null) {
    		throw new IllegalArgumentException("Usuário não encontrado.");
    	} 
    	
    	Conta conta;
    	
    	if (tipo == Tipo.CORRENTE) {
    		conta = new ContaCorrente(usuario);
    	} else {
    		conta = new ContaPoupanca(usuario);
    	}
    	
    	contas.add(conta);
    	
    	return conta;
    }
    
    
    // alterar metodos com lista utilizando comandos sql nas classes DAO
    
    public boolean existeUsuario(String cpf) {
		for (Usuario usuario : usuarios) {
			if (usuario.getCPF().equals(cpf)) {
				return true;
			}
		} return false;
    }
    
    public Usuario buscarUsuario(String cpf) {
		for (Usuario usuario : usuarios) {
			if (usuario.getCPF().equals(cpf)) {
				return usuario;
			}
		} return null;
    }
    
    public Conta buscarConta(long id) {
    	for (Conta conta : contas) {
			if (conta.getId() == id) {
				return conta;
			}
		} return null;
    }
    
    public void listarUsuarios() {
		for (Usuario usuario : this.usuarios) {
			System.out.println(usuario.getNome());
			System.out.println(usuario.getApelido());
			System.out.println(usuario.getCPF());
			System.out.println(usuario.getIdade());
			System.out.println();
		}
    }
	
    public void listarContas() {
    	for (Conta conta : contas) {
			System.out.println(conta.getUsuario().getNome());
			System.out.println(conta.getId());
			System.out.println(conta.getClass().getName());
			System.out.println(conta.getSaldo());
			System.out.println();
		}
    }

	public String getNOME() {
		return NOME;
	}

	public String getAGENCIA() {
		return AGENCIA;
	}

	public String detalhesDoBanco() {
		return "Nome do Banco:" + NOME + "\n Agência:" + AGENCIA;
	}
    
}