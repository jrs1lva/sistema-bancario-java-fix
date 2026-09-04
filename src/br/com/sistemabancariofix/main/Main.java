package br.com.sistemabancariofix.main;

import br.com.sistemabancariofix.model.Banco;
import br.com.sistemabancariofix.view.Menu;

public class Main {

	public static void main(String[] args) {
		
		Banco Santander = new Banco("Bradesco", "4002");
		Menu banco = new Menu();
		
		banco.run(Santander);
		
		
	}

}
