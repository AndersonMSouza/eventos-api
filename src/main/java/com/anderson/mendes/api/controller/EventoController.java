package com.anderson.mendes.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anderson.mendes.domain.exceptions.EntidadeEmUsoException;
import com.anderson.mendes.domain.exceptions.EntidadeNaoEncontradaException;
import com.anderson.mendes.domain.model.Evento;
import com.anderson.mendes.domain.repository.EventoRepository;
import com.anderson.mendes.domain.service.CadastroEventoService;

@RestController
@RequestMapping("/eventos")
public class EventoController {

	@Autowired
	private EventoRepository eventoRepository;
	
	@Autowired
	private CadastroEventoService cadastroEventoService;
	
	@GetMapping
	public List<Evento> listar() {
		return eventoRepository.findAll();
	}
	
	@GetMapping("/{eventoId}")
	public Evento buscar(@PathVariable Long eventoId) {
		return cadastroEventoService.buscarOuFalhar(eventoId);
	
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Evento evento) {
		try {
			evento = cadastroEventoService.salvar(evento);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(evento);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest()
					.body(e.getMessage());
		}
	}
	
	@PutMapping("/{eventoId}")
	public ResponseEntity<?> atualizar(@PathVariable Long eventoId, @RequestBody Evento evento) {
		try {
			Optional<Evento> eventoAtual = eventoRepository.findById(eventoId);
			
			if (eventoAtual.isPresent()) {
				BeanUtils.copyProperties(evento, eventoAtual.get(), "id");
				Evento eventoSalvo = cadastroEventoService.salvar(eventoAtual.get());
				return ResponseEntity.ok(eventoSalvo);
			}
			
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{eventoId}")
	public ResponseEntity<?> remover(@PathVariable Long eventoId) {
		try {
			cadastroEventoService.excluir(eventoId);
			return ResponseEntity.noContent().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}		
	}
	
}

