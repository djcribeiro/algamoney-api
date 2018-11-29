package com.algaworks.algamoneyapi.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoneyapi.api.v1.events.CreatedResourceEvent;
import com.algaworks.algamoneyapi.model.Pessoa;
import com.algaworks.algamoneyapi.repository.PessoaRepository;
import com.algaworks.algamoneyapi.service.PessoaService;

@RestController
@RequestMapping("/pessoa")
public class PessoaResource {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Pessoa> list() {
		List<Pessoa> pessoas = pessoaRepository.findAll();
		return pessoas;
	}
//	@GetMapping
//	public ResponseEntity<?> list() {
//		List<Categoria> categorias = categoriaRepository.findAll();
//		return categorias.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(categorias);
//	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Pessoa> create(@Valid @RequestBody Pessoa pPessoa, HttpServletResponse response) {
		Pessoa pessoa = pessoaRepository.save(pPessoa);
		
		publisher.publishEvent(new CreatedResourceEvent(this, response, pessoa.getCodigo()));

		return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);

		
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> find(@PathVariable Long codigo) throws NotFoundException {
		Pessoa vPessoa = pessoaRepository.findById(codigo).orElse(null);
		return vPessoa == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(vPessoa);
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long codigo) throws NotFoundException {
		pessoaRepository.deleteById(codigo);
	}
	
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo, @Valid @RequestBody Pessoa pPessoa, HttpServletResponse response) throws NotFoundException {
		Pessoa vPessoa = pessoaService.atualizar(codigo, pPessoa);
		return ResponseEntity.ok(vPessoa);
	}

	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarStatusPessoa(@PathVariable Long codigo, @RequestBody Boolean pAtivo) {
		pessoaService.atualizarPropriedadeAtivo(codigo, pAtivo);
		
	}

}
