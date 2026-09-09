package br.com.sistemabancariofix.model;

import java.time.LocalDate;
import java.time.Period;

public class ContaCorrente extends Conta {

	protected double limite;
	protected double saldoTotal;

	public ContaCorrente(Usuario usuario) {
		super(usuario);
		this.limite = 500.00;
		this.saldoTotal = limite + saldo; 
	}
	//	08007700051 08007512121 08007708510
	// saldo = 0 | limite = 500
	// solicitacao de 200
	// se o valor > saldo, se o valor for menor saldoTotal, saldo = 0, limite =
	// (resto = saldo - valor) + limite

	@Override
	public boolean sacar(double valor) {

		if (valor > (getSaldoTotal())) {
			throw new IllegalArgumentException("O valor de saque deve ser menor ou igual a: " + getSaldoTotal());
		} else if (valor <= 0) {
			throw new IllegalArgumentException("O valor de saque deve ser maior do que 0.");
		} else if (valor > getSaldo() && valor <= getSaldoTotal()) {
 			double resto = (getSaldo() - valor);
			setSaldo(0);
			setLimite(getLimite() + resto);
			setSaldoTotal(limite);
			extrato.add(String.format("Saque: R$ %.2f\nSaldo: R$ %.2f\nLimite: R$ %.2f", valor, saldo, limite));
			return true;
		} else {
			setSaldo(getSaldo() - valor);
			extrato.add(String.format("Saque: R$ %.2f\nSaldo: R$ %.2f\nLimite: R$ %.2f", valor, saldo, limite));
			return true;
		}
	}

	public double getLimite() {
		return limite;
	}

	public void setLimite(double limite) {
		this.limite = limite;
	}

	public double getSaldoTotal() {
		return saldoTotal;
	}

	public void setSaldoTotal(double saldoTotal) {
		this.saldoTotal = saldoTotal;
	}

	@Override
	public void mostrarComprovante(String operacao, Conta conta, double valor, Banco banco) {
		System.out.println("\n===== COMPROVANTE =====\n");
		System.out.println("Banco: " + banco.getNOME());
		System.out.println("Operação: " + operacao);
		System.out.printf("Valor: R$ %.2f%n", valor);
		System.out.println("\n-------------------");
		System.out.println("Nome: " + conta.getUsuario().getNome());
		System.out.println("CPF: " + conta.getUsuario().getCPF());
		System.out.println("Idade: " + Period.between(conta.getUsuario().getDataNascimento(), LocalDate.now()).getYears() + " Anos");
		System.out.println("\n-------------------");
		System.out.printf("Saldo Atual: %.2f%n", this.saldo);
		mostrarInfosEspecificas();
	}

	public void mostrarInfosEspecificas() {
		System.out.printf("Limite disponível: R$ %.2f%n", limite);
	}

	@Override
	public boolean depositar(double valor) {
		if (valor <= 0) {
			throw new IllegalArgumentException("O valor depositado deve ser maior do que 0.");
		}
		
		//se o limite for menor que 500, limite += valor, se limite > 500, limite = 500 e resto vai para saldo se o valor for menor que 500 
		if (getLimite() < 500) {
			setLimite(getLimite() + valor);
			
			if (getLimite() > 500) {
				double resto = getLimite() - 500;
				setLimite(500);
				setSaldo(resto);
				
			}
		} else {
			setSaldo(getSaldo() + valor);
		}
		
		setSaldoTotal(getLimite() + getSaldo());
		extrato.add(String.format("Depósito: R$ %.2f\nSaldo: R$ %.2f\nLimite: R$ %.2f", valor, saldo, limite));
		return true;
	}

}
