package br.com.sistemabancariofix.util;

public class ConsoleUtils {
	public static void pausar(int milissegundos) {
		try {
			Thread.sleep(milissegundos);
		} catch (InterruptedException e) {
			// Ignora o erro, apenas garante que o sistema não quebre
		}
	}

	public static void pausar(int milissegundos, String mensagem) {
		try {
			System.out.println(mensagem);
			Thread.sleep(milissegundos);
		} catch (InterruptedException e) {
			// Ignora o erro, apenas garante que o sistema não quebre
		}
	}

	public static void limparTela() {
		for (int i = 0; i < 50; i++) {
			System.out.println();
		}
	}
}
