package br.com.sistemabancariofix;

import java.time.LocalDate;
import java.time.Period;

public class ContaPoupanca extends Conta{

	public ContaPoupanca(Usuario usuario) {
		super(usuario);
	}

	@Override
	public void sacar(double valor) {
		if (valor <= 0) {
			throw new IllegalArgumentException("O valor de saque deve ser maior do que 0.");
		}
		if (valor > getSaldo()) {
			throw new IllegalArgumentException("O valor de saque deve ser menor ou igual ao saldo disponível.");
		}
		
		setSaldo(this.getSaldo() - valor);
		extrato.add(String.format("Saque: R$ %.2f\nSaldo: R$ %.2f", valor, saldo));
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
	}

}
