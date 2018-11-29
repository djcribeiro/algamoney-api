package com.algaworks.algamoneyapi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algamoneyapi.model.Pessoa;
import com.algaworks.algamoneyapi.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Pessoa atualizar(Long codigo, Pessoa pPessoa) {
		
		Pessoa vPessoa = pessoaRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		BeanUtils.copyProperties(pPessoa, vPessoa, "codigo");
		return pessoaRepository.save(vPessoa);
	}

	public void atualizarPropriedadeAtivo(Long codigo, Boolean pAtivo) {
		Pessoa vPessoa = pessoaRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		vPessoa.setAtivo(pAtivo);
		pessoaRepository.save(vPessoa);
		
	}
	
}
