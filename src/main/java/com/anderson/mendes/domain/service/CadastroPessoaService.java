package com.anderson.mendes.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.anderson.mendes.domain.exceptions.EntidadeEmUsoException;
import com.anderson.mendes.domain.exceptions.EntidadeNaoEncontradaException;
import com.anderson.mendes.domain.model.Pessoa;
import com.anderson.mendes.domain.repository.PessoaRepository;

@Service
public class CadastroPessoaService {
	
	private static final String MSG_PESSOA_EM_USO 
	= "Pessoa de código %d não pode ser removida, pois está em uso";

	private static final String MSG_PESSOA_NAO_ENCONTRADA 
	= "Não existe um cadastro de pessoa com código %d";

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
				String.format(MSG_PESSOA_NAO_ENCONTRADA, pessoaId));
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format(MSG_PESSOA_EM_USO, pessoaId));
		}
	}
	
	public Pessoa buscarOuFalhar(@PathVariable Long pessoaId) {
		return pessoaRepository.findById(pessoaId)
			.orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format(MSG_PESSOA_NAO_ENCONTRADA,pessoaId)));
	}
	
}
