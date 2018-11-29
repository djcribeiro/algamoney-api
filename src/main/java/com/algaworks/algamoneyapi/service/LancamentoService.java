package com.algaworks.algamoneyapi.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algamoneyapi.model.Lancamento;
import com.algaworks.algamoneyapi.model.Pessoa;
import com.algaworks.algamoneyapi.repository.LancamentoRepository;
import com.algaworks.algamoneyapi.repository.PessoaRepository;
import com.algaworks.algamoneyapi.service.exception.PessoaInexistenteException;

@Service
public class LancamentoService {
	
	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private LancamentoRepository lancamentoRepository;

	public Lancamento salvar(@Valid Lancamento pLancamento) {
		Pessoa pessoa = pessoaRepository.findById(pLancamento.getPessoa().getCodigo()).orElseThrow(() -> new PessoaInexistenteException());
		if(pessoa.isInativo()) {
			throw new PessoaInexistenteException();
		}
		
		return lancamentoRepository.save(pLancamento);
	}

}
