package br.com.sistemabancariofix.model;

import java.time.LocalDate;
import java.time.Period;

public class Usuario {
	
	//validação de cpf
	
	private long id;
	private final String nome;
	private String apelido;
	private final LocalDate DATANASCIMENTO;
	private final String CPF;
	
	public Usuario(String nome, String apelido, LocalDate dataNascimento, String cpf) {
		
		if (!isMaiorDeIdade(dataNascimento)) {
			throw new IllegalArgumentException("É necessário ser maior de idade para criar uma conta");
		}
		
		if (!isCpfValido(cpf)) {
			throw new IllegalArgumentException("Erro fatal: Tentativa de instanciar Usuario com CPF inválido.");
		}
		
		this.nome = nome;
		this.apelido = apelido;
		DATANASCIMENTO = dataNascimento;
		CPF = cpf;
		this.id = 0;
	}
	
	public static boolean isMaiorDeIdade(LocalDate data) {
		int idade = Period.between(data, LocalDate.now()).getYears();
		return idade >= 18;
	}
	
	public static boolean isCpfValido(String cpf) {
		
		cpf = cpf.replaceAll("\\D", "");

	    // Verifica se o tamanho está incorreto ou se é uma sequência repetida (ex: 111.111.111-11)
	    if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
	        return false;
	    }

	    try {
	        // Cálculo do 1º Dígito Verificador
	        int soma = 0;
	        int peso = 10;
	        for (int i = 0; i < 9; i++) {
	            soma += (cpf.charAt(i) - '0') * peso--;
	        }
	        int resto = 11 - (soma % 11);
	        char digito1 = (resto == 10 || resto == 11) ? '0' : (char) (resto + '0');

	        // Cálculo do 2º Dígito Verificador
	        soma = 0;
	        peso = 11;
	        for (int i = 0; i < 10; i++) {
	            soma += (cpf.charAt(i) - '0') * peso--;
	        }
	        resto = 11 - (soma % 11);
	        char digito2 = (resto == 10 || resto == 11) ? '0' : (char) (resto + '0');

	        // Retorna true apenas se os dois dígitos calculados baterem com os originais
	        return (digito1 == cpf.charAt(9)) && (digito2 == cpf.charAt(10));
	        
	    } catch (Exception e) {
	        return false;
	    }
	}
	
	public String getNome() {return nome;}
	
	public String getApelido() {return apelido;}

	public void setApelido(String apelido) {this.apelido = apelido;}
	
	public LocalDate getDataNascimento() {return DATANASCIMENTO;}

	public int getIdade() {return Period.between(DATANASCIMENTO, LocalDate.now()).getYears();}

	public long getId() {return id;}
	
	public String getCPF() {return CPF;}
	
}
