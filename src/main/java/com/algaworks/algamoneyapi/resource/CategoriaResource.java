package com.algaworks.algamoneyapi.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoneyapi.api.v1.events.CreatedResourceEvent;
import com.algaworks.algamoneyapi.model.Categoria;
import com.algaworks.algamoneyapi.repository.CategoriaRepository;

@RestController
@RequestMapping("/categoria")
public class CategoriaResource {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Categoria> list() {
		List<Categoria> categorias = categoriaRepository.findAll();
		return categorias;
	}
//	@GetMapping
//	public ResponseEntity<?> list() {
//		List<Categoria> categorias = categoriaRepository.findAll();
//		return categorias.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(categorias);
//	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Categoria> create(@Valid @RequestBody Categoria pCategoria, HttpServletResponse response) {
		Categoria categoria = categoriaRepository.save(pCategoria);

		publisher.publishEvent(new CreatedResourceEvent(this, response, categoria.getCodigo()));

		return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> find(@PathVariable Long codigo) throws NotFoundException {
		Categoria vCategoria = categoriaRepository.findById(codigo).orElse(null);
		return vCategoria == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(vCategoria);
	}

}
