package br.com.sistemabancariofix;

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
	
	public boolean depositar(double valor) {
		if (valor <= 0) {
			throw new IllegalArgumentException("O valor depositado deve ser maior do que 0.");
		}
		
		setSaldo(getSaldo() + valor);
		
		extrato.add(String.format("Depósito: R$ %.2f\nSaldo: R$ %.2f", valor, saldo));
		return true;
	}
	
	public void mostrarExtrato() {
		System.out.println("===== EXTRATO =====\n");

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
