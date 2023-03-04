package com.anderson.mendes.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Evento {
 
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
 	private String nome;
	
	@Column(nullable = false)
	private String local;
	
	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate data;
	
	@Column(nullable = false)
	private LocalTime horario;
	
	
	
}
