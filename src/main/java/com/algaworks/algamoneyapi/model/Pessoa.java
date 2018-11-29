package com.algaworks.algamoneyapi.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "PESSOA")

//lombok
@Data
@AllArgsConstructor
@RequiredArgsConstructor

public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PES_ID")
	private Long codigo;

	@Column(name = "PES_NOME")
	@NotBlank
	@NotNull
	@Size(min = 3, max = 255)
	private String nome;

	@Column(name = "PES_ATIVO")
	@NotNull
	private Boolean ativo;

	@Embedded
	private Endereco endereco;
	
	@Transient
	@JsonIgnore
	public boolean isInativo() {
		return !this.ativo;
	}

}
