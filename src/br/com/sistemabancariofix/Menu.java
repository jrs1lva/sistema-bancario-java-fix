package br.com.sistemabancariofix;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Menu {
	Scanner scanner = new Scanner(System.in);
	
	// poder criar mais de uma conta para utilizar p listar contas/usuarios (SÓ FAZ SENTIDO COM BANCO DE DADOS)
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
		pausar(500);
	    System.out.println("\nPara começar, nos informe seus dados pessoais");
	    
	    pausar(500);
	    
	    System.out.print("\nNome completo: ");
	    String nome = scanner.nextLine();
	    
	    pausar(500);
	    
	    System.out.print("\nPrefere ser chamado de: ");
	    String apelido = scanner.nextLine();
	    
	    pausar(500);
	    
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
					pausar(2000);
	            } else {
	            	dataValida = true;
	            }
	        } catch (java.time.format.DateTimeParseException e) {
	        	System.err.println("\n[ERRO] Data inválida. Por favor, digite no formato dd/MM/yyyy (ex: 04/03/2004).");
	        }
	    }
	    
	    pausar(500);
	    
	    System.out.print("\nCPF: ");
	    String cpf = scanner.nextLine();
	    
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
					pausar(1000, "Processando...");
					break;
				
				case 2:
					tipo = Tipo.POUPANCA;
					pausar(1000, "Processando...");
					break;
					
				default:
					System.err.println("\n[ERRO] Opção inválida. Escolha um número entre 1 e 2.");
					break;
			}
		} while (opcao!= 1 && opcao != 2);
		
		Conta conta = banco.criarConta(usuario.getCPF(), tipo);
		return conta;
	}
	
	//se conta for poupança passa o tipo de conta poupanca e se corrente passar corrente
	public void menuPrincipal(Conta conta, Banco banco) {
		System.out.println("\nParabéns por efetuar seu cadastro no banco, " + conta.getUsuario().getApelido());
		int opcao;
		
		pausar(2000);
		
		do {
			limparTela();
			System.out.print("\nQual funcionalidade deseja utilizar?\n[1] Depositar | [2] Sacar | [3] Mostrar Extrato | [4] Encerrar aplicativo\n\n=> ");
			opcao = scanner.nextInt();
			
			pausar(1000);
			
			switch (opcao) {
			case 1:
				try {
					System.out.print("\nValor do depósito: ");
					double valorDeposito = scanner.nextDouble();
					
					conta.depositar(valorDeposito);
					pausar(1000, "\nProcessando Operação...");
					System.out.printf("\nOperação realizada com sucesso!");
					conta.mostrarComprovante("Depósito", conta, valorDeposito, banco);
					pausar(5000); //valor depositado, nome completo de quem fez o deposito
					break;
				} catch (IllegalArgumentException e) {
					System.err.println("\n[FALHA NA OPERAÇÃO] " + e.getMessage());
					pausar(5000);
					break; 
				}
				
				
			case 2:
				try {
					System.out.print("\nValor do saque: ");
					double valorSaque = scanner.nextDouble();
					
					conta.sacar(valorSaque);
					pausar(1000, "\nProcessando Operação...");
					System.out.printf("\nOperação realizada com sucesso!");
					conta.mostrarComprovante("Saque", conta, valorSaque, banco);
					pausar(5000);
					break;
				} catch (IllegalArgumentException e) {
					System.err.println("\n[FALHA NA OPERAÇÃO] " + e.getMessage());
					pausar(5000);
					break;
				}
				
				
			case 3:
				conta.mostrarExtrato();
				pausar(10000);
				break;
				
			case 4:
				pausar(1000, "\nEncerrando aplicativo...");
				System.out.printf("\nSistema encerrado.\nSaldo Final: %.2f", conta.getSaldo());
				break;
				
			default:
				System.err.println("\n[ERRO] Opção inválida. Escolha um número entre 1 e 4.");
				pausar(5000);
				break;
				
			}
		} while (opcao != 4);
		
	}
	
	private void pausar(int milissegundos) {
	    try {
	        Thread.sleep(milissegundos);
	    } catch (InterruptedException e) {
	        // Ignora o erro, apenas garante que o sistema não quebre
	    }
	}
	
	private void pausar(int milissegundos, String mensagem) {
	    try {
	    	System.out.println(mensagem);
	        Thread.sleep(milissegundos);
	    } catch (InterruptedException e) {
	        // Ignora o erro, apenas garante que o sistema não quebre
	    }
	}
	
	private void limparTela() {
		for (int i = 0; i < 50; i++) {
			System.out.println();
		}
	}
}
