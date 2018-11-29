package com.algaworks.algamoneyapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "LANCAMENTO")

//lombok
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Lancamento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LANC_ID")
	private Long codigo;

	@NotNull
	@Size(max = 50)
	@Column(name = "LANC_DESCRICAO")
	private String descricao;

	@NotNull
	@Column(name = "LANC_DT_VENCIMENTO")
	private LocalDate dataVencimento;

	@Column(name = "LANC_DT_PAGAMENTO")
	private LocalDate dataPagamento;

	@NotNull
	@Column(name = "LANC_VALOR")
	private BigDecimal valor;

	@Size(max = 200)
	@Column(name = "LANC_OBSERVACAO")
	private String observacao;

	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(name = "LANC_TIPO")
	private TipoLancamento tipo;

	@ManyToOne
	@JoinColumn(name = "CAT_ID")
	@NotNull
	private Categoria categoria;

	@ManyToOne
	@JoinColumn(name = "PES_ID")
	@NotNull
	private Pessoa pessoa;

}
