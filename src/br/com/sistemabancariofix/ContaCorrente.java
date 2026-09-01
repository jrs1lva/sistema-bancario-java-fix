package br.com.sistemabancariofix;

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
	public void sacar(double valor) {

		if (valor > (getSaldoTotal())) {
			throw new IllegalArgumentException("O valor de saque deve ser menor ou igual a: " + getSaldoTotal());
		}

		if (valor <= 0) {
			throw new IllegalArgumentException("O valor de saque deve ser maior do que 0.");
		}

		if (valor > getSaldo() && valor < getSaldoTotal()) {
			setSaldo(0);
			double resto = (valor - getSaldo());
			setLimite(getLimite() + resto);
			setSaldoTotal(limite);
			extrato.add(String.format("Saque: R$ %.2f\nSaldo: R$ %.2f\nLimite: R$ %.2f", valor, saldo, limite));
		}
		setSaldo(getSaldo() - valor);
		extrato.add(String.format("Saque: R$ %.2f\nSaldo: R$ %.2f\nLimite: R$ %.2f", valor, saldo, limite));

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
		System.out.println("===== COMPROVANTE =====\n");
		System.out.println("Banco: " + banco.getNOME());
		System.out.println("Operação: " + operacao);
		System.out.printf("\nValor: R$ %.2f%n", valor);
		System.out.println("\n-------------------");
		System.out.println("Nome: " + conta.getUsuario().getNome());
		System.out.println("CPF: " + conta.getUsuario().getCPF());
		System.out.println("Idade: " + Period.between(conta.getUsuario().getDataNascimento(), LocalDate.now()).getYears() + " Anos");
		System.out.println("\n-------------------");
		System.out.printf("\nSaldo Atual: %.2f%n", this.saldo);
		mostrarInfosEspecificas();
	}

	public void mostrarInfosEspecificas() {
		System.out.printf("\nLimite disponível: R$ %.2f%n", limite);
	};

}
