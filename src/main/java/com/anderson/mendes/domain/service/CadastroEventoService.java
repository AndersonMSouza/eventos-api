package com.anderson.mendes.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.anderson.mendes.domain.exceptions.EntidadeEmUsoException;
import com.anderson.mendes.domain.exceptions.EventoNaoEncontradoException;
import com.anderson.mendes.domain.model.Evento;
import com.anderson.mendes.domain.repository.EventoRepository;

@Service
public class CadastroEventoService {
	
	private static final String MSG_EVENTO_EM_USO 
	= "Evento de código %d não pode ser removido, pois está em uso";

	@Autowired
	private EventoRepository eventoRepository;
	
	public Evento salvar(Evento evento) {
		return eventoRepository.save(evento);
	}
	
	public void excluir(Long eventoId) {
		try {
			eventoRepository.deleteById(eventoId);
			
		} catch (EmptyResultDataAccessException e) {
			throw new EventoNaoEncontradoException(eventoId);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format(MSG_EVENTO_EM_USO, eventoId));
		}
	}
	
	public Evento buscarOuFalhar(@PathVariable Long eventoId) {
		return eventoRepository.findById(eventoId)
			.orElseThrow(() -> new EventoNaoEncontradoException(eventoId));
	}
	
}
