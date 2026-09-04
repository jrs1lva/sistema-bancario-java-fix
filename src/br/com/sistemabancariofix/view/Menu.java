package br.com.sistemabancariofix.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import br.com.sistemabancariofix.model.Banco;
import br.com.sistemabancariofix.model.Conta;
import br.com.sistemabancariofix.model.Tipo;
import br.com.sistemabancariofix.model.Usuario;
import br.com.sistemabancariofix.util.ConsoleUtils;

public class Menu {
	Scanner scanner = new Scanner(System.in);

	// poder criar mais de uma conta para utilizar p listar contas/usuarios (SÓ FAZ
	// SENTIDO COM BANCO DE DADOS)
	// acrescentar banco de dados
	// verificar se já possui uma conta antes de entrar reaproveitar os dados salvos
	// criar classes DAO
	// Não passar ID por parâmetro
	// Mudar os metodos com lista em Banco (listarUsuarios, listarContas)

	public void run(Banco banco) {
		Usuario usuario = cadastrarUsuario(banco);
		if (usuario != null) {
			Conta conta = criarConta(banco, usuario);
			if (conta != null) {
				menuPrincipal(conta, banco);
			}
		}
	}

	private Usuario cadastrarUsuario(Banco banco) {
		System.out.println("Olá, seja bem vindo ao Banco " + banco.getNOME());
		ConsoleUtils.pausar(500);
		System.out.println("\nPara começar, nos informe seus dados pessoais");
		ConsoleUtils.pausar(500);

		System.out.print("\nNome completo: ");
		String nome = scanner.nextLine();
		ConsoleUtils.pausar(500);

		System.out.print("\nPrefere ser chamado de: ");
		String apelido = scanner.nextLine();
		ConsoleUtils.pausar(500);

		LocalDate dataNascimento = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		boolean dataValida = false;

		while (!dataValida) {
			System.out.print("\nData de nascimento (dd/MM/yyyy): ");
			String data = scanner.nextLine();

			try {
				dataNascimento = LocalDate.parse(data, formatter);

				if (!Usuario.isMaiorDeIdade(dataNascimento)) {
					System.err.println("\n[ERRO] É necessário ter pelo menos 18 anos para abrir uma conta.");
					ConsoleUtils.pausar(2000);
				} else {
					dataValida = true;
				}
			} catch (java.time.format.DateTimeParseException e) {
				System.err.println("\n[ERRO] Data inválida. Por favor, digite no formato dd/MM/yyyy (ex: 04/03/2004).");
			}
		}

		ConsoleUtils.pausar(500);

		String cpf = null;
		boolean cpfValido = false;

		while (!cpfValido) {
			System.out.print("\nCPF: ");
			cpf = scanner.nextLine();

			try {
				if (Usuario.isCpfValido(cpf)) {
					cpfValido = true;
				} else {
					System.err.println("\n[ERRO] CPF inválido. Por favor, reescreva-o corretamente.");
				}
			} catch (Exception e) {
				System.err.println("\n[ERRO] CPF inválido. Por favor, reescreva-o corretamente.");
			}
		}

		Usuario usuario = new Usuario(nome, apelido, dataNascimento, cpf);
		banco.cadastrarUsuario(usuario);

		return usuario;
	}

	private Conta criarConta(Banco banco, Usuario usuario) {
		int opcao;
		Tipo tipo = null;

		do {
			System.out.print("\nQual tipo de conta se encaixa mais no seu perfil:\n[1] CORRENTE | [2] POUPANÇA\n\n=> ");
			opcao = scanner.nextInt();

			switch (opcao) {

			case 1:
				tipo = Tipo.CORRENTE;
				ConsoleUtils.pausar(1000,"Processando...");
				break;

			case 2:
				tipo = Tipo.POUPANCA;
				ConsoleUtils.pausar(1000,"Processando...");
				break;

			default:
				System.err.println("\n[ERRO] Opção inválida. Escolha um número entre 1 e 2.");
				break;
			}
		} while (opcao != 1 && opcao != 2);

		return banco.criarConta(usuario.getCPF(), tipo);
		
	}

	public void menuPrincipal(Conta conta, Banco banco) {
		System.out.println("\nParabéns por efetuar seu cadastro no banco, " + conta.getUsuario().getApelido());
		int opcao;

		ConsoleUtils.pausar(2000,"Processando...");

		do {
			ConsoleUtils.limparTela();
			
			
			System.out.print("\nQual funcionalidade deseja utilizar?\n[1] Depositar | [2] Sacar | [3] Mostrar Extrato | [4] Encerrar aplicativo\n\n=> ");
			opcao = scanner.nextInt();
			ConsoleUtils.pausar(1000);

			switch (opcao) {
			case 1:
				try {
					System.out.print("\nValor do depósito: ");
					double valorDeposito = scanner.nextDouble();

					conta.depositar(valorDeposito);
					ConsoleUtils.pausar(1000,"Processando...");
					System.out.printf("\nOperação realizada com sucesso!");
					conta.mostrarComprovante("Depósito", conta, valorDeposito, banco);
					ConsoleUtils.pausar(5000);
				} catch (IllegalArgumentException e) {
					System.err.println("\n[FALHA NA OPERAÇÃO] " + e.getMessage());
					ConsoleUtils.pausar(5000);
				}
				break;
			case 2:
				try {
					System.out.print("\nValor do saque: ");
					double valorSaque = scanner.nextDouble();

					conta.sacar(valorSaque);
					ConsoleUtils.pausar(1000,"Processando...");
					System.out.printf("\nOperação realizada com sucesso!");
					conta.mostrarComprovante("Saque", conta, valorSaque, banco);
					ConsoleUtils.pausar(5000);
				} catch (IllegalArgumentException e) {
					System.err.println("\n[FALHA NA OPERAÇÃO] " + e.getMessage());
					ConsoleUtils.pausar(5000);
				}
				break;
			case 3:
				conta.mostrarExtrato();
				ConsoleUtils.pausar(5000);
				break;

			case 4:
				ConsoleUtils.pausar(1000, "\nEncerrando aplicativo...");
				System.out.printf("\nSistema encerrado.\nSaldo Final: %.2f", conta.getSaldo());
				break;

			default:
				System.err.println("\n[ERRO] Opção inválida. Escolha um número entre 1 e 4.");
				ConsoleUtils.pausar(5000);
				break;

			}
		} while (opcao != 4);

	}

	
}
