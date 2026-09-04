package br.com.sistemabancariofix.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Conta {
	
	protected long id;
	protected final Usuario USUARIO;
	protected double saldo;
	protected List<String> extrato;

	public Conta(Usuario usuario) {
		this.USUARIO = usuario;
		this.saldo = 0;
		this.id = 0;
		this.extrato = new ArrayList<>();
	}
	
	public abstract void sacar(double valor);
	
	public abstract boolean depositar(double valor);
	
	public void mostrarExtrato() {
		System.out.println("\n===== EXTRATO =====\n");

		for (String transacao : extrato) {
			System.out.println(transacao);
		}
		
		System.out.println("\n-------------------");
		System.out.printf("Saldo Atual: %.2f%n", this.saldo);
	}
	
	public void mostrarInfosEspecificas() {};
	
	public abstract void mostrarComprovante(String operacao, Conta conta, double valor, Banco banco);

	public Usuario getUsuario() {return USUARIO;}

	public double getSaldo() {return saldo;}
	protected void setSaldo(double saldo) {this.saldo = saldo;}
	
	public long getId() {return id;}
	
}
