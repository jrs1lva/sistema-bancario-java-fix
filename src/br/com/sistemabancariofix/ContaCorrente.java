package br.com.sistemabancariofix;

public class ContaCorrente extends Conta {
	
	protected double limite;
	protected double saldoTotal;
	
	public ContaCorrente(Usuario usuario) {
		super(usuario);
		this.limite = 500.00;
		this.saldoTotal = limite + saldo;
	}
	
	// saldo = 0 | limite = 500
	// solicitacao de 200
	// se o valor > saldo, se o valor for menor saldoTotal, saldo = 0, limite =  (resto = saldo - valor) + limite
	
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
	
	

}
