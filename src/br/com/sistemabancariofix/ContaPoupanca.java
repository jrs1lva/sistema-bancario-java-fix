package br.com.sistemabancariofix;

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
	}

}
