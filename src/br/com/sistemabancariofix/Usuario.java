package br.com.sistemabancariofix;

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
	
	public String getNome() {return nome;}
	
	public String getApelido() {return apelido;}

	public void setApelido(String apelido) {this.apelido = apelido;}
	
	public LocalDate getDataNascimento() {return DATANASCIMENTO;}

	public int getIdade() {return Period.between(DATANASCIMENTO, LocalDate.now()).getYears();}

	public long getId() {return id;}
	
	public String getCPF() {return CPF;}
	
}
