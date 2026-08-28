package br.com.sistemabancariofix;

public class Main {

	public static void main(String[] args) {
		
		Banco Santander = new Banco("Bradesco", "4002");
		Menu banco = new Menu();
		
		banco.run(Santander);
		
		
	}

}
