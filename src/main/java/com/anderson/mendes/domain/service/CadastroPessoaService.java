package com.anderson.mendes.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anderson.mendes.domain.model.Pessoa;
import com.anderson.mendes.domain.repository.PessoaRepository;

@Service
public class CadastroPessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Pessoa salvar(Pessoa pessoa) {
		return pessoaRepository.save(pessoa);
	}
	
}
