package com.algaworks.algamoneyapi.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


//lombok
@Data
@AllArgsConstructor
@RequiredArgsConstructor

@Embeddable
public class Endereco {

	@Column(name = "PES_END_LOGRADOURO")
	@Size(min=3, max=255)
	private String logradouro;

	@Column(name = "PES_END_NUMERO")
	@Size(max=10)
	private String numero;
	
	@Column(name = "PES_END_COMPLEMENTO")
	@Size(max=30)
	private String complemento;
	
	@Column(name = "PES_END_BAIRRO")
	@Size(max=30)
	private String bairro;
	
	@Column(name = "PES_END_CEP")
	@Size(max=9)
	private String cep;
	
	@Column(name = "PES_END_CIDADE")
	@Size(max=50)
	private String cidade;
	
	@Column(name = "PES_END_UF")
	@Size(max=2)
	private String estado;

	
}
