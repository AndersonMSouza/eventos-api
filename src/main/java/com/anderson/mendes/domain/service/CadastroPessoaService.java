package com.anderson.mendes.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.anderson.mendes.domain.exceptions.EntidadeEmUsoException;
import com.anderson.mendes.domain.exceptions.EntidadeNaoEncontradaException;
import com.anderson.mendes.domain.model.Pessoa;
import com.anderson.mendes.domain.repository.PessoaRepository;

@Service
public class CadastroPessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Pessoa salvar(Pessoa pessoa) {
		return pessoaRepository.save(pessoa);
	}
	
	public void excluir(Long pessoaId) {
		try {
			pessoaRepository.deleteById(pessoaId);
			
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
				String.format("Não existe um cadastro de pessoa com código %d", pessoaId));
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format("Pessoa de código %d não pode ser removida, pois está em uso", pessoaId));
		}
	}
	
}
