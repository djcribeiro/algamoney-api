package com.algaworks.algamoneyapi.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoneyapi.api.v1.events.CreatedResourceEvent;
import com.algaworks.algamoneyapi.exceptionhandler.AlgaMoneyExceptionHandler.Erro;
import com.algaworks.algamoneyapi.model.Lancamento;
import com.algaworks.algamoneyapi.repository.LancamentoRepository;
import com.algaworks.algamoneyapi.service.LancamentoService;
import com.algaworks.algamoneyapi.service.exception.PessoaInexistenteException;

@RestController
@RequestMapping("/lancamento")
public class LancamentoResource {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private MessageSource  messageSource;
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Lancamento> list() {
		List<Lancamento> lancamentos = lancamentoRepository.findAll();
		return lancamentos;
	}
//	@GetMapping
//	public ResponseEntity<?> list() {
//		List<Categoria> categorias = categoriaRepository.findAll();
//		return categorias.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(categorias);
//	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Lancamento> create(@Valid @RequestBody Lancamento pLancamento, HttpServletResponse response) {
		Lancamento pessoa = lancamentoService.salvar(pLancamento);
		
		publisher.publishEvent(new CreatedResourceEvent(this, response, pessoa.getCodigo()));

		return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);

		
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> find(@PathVariable Long codigo) throws NotFoundException {
		Lancamento vLancamento = lancamentoRepository.findById(codigo).orElse(null);
		return vLancamento == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(vLancamento);
	}

//	@DeleteMapping("/{codigo}")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void delete(@PathVariable Long codigo) throws NotFoundException {
//		lancamentoRepository.deleteById(codigo);
//	}
	
	
//	@PutMapping("/{codigo}")
//	public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo, @Valid @RequestBody Pessoa pPessoa, HttpServletResponse response) throws NotFoundException {
//		Pessoa vPessoa = pessoaService.atualizar(codigo, pPessoa);
//		return ResponseEntity.ok(vPessoa);
//	}
//
//	@PutMapping("/{codigo}/ativo")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void atualizarStatusPessoa(@PathVariable Long codigo, @RequestBody Boolean pAtivo) {
//		pessoaService.atualizarPropriedadeAtivo(codigo, pAtivo);
//		
//	}
	
	@ExceptionHandler({PessoaInexistenteException.class})
	public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteException ex){
		String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
		 
	}

}
