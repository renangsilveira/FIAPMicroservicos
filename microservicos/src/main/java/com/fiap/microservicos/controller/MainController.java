package com.fiap.microservicos.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fiap.microservicos.controller.dto.ResponseDTO;
import com.fiap.microservicos.service.AnalisarService;

@RestController
@RequestMapping("/analisa_cep")
public class MainController {

	@Autowired
	private AnalisarService service;
	
	@GetMapping("/{cep}")
	public ResponseEntity<ResponseDTO> analisarCep(@PathVariable Long cep) {
		try {
		Double valor = AnalisarService.analyzeByCep(cep);
		if (valor.equals(0.0)) {
			return ResponseEntity.noContent().build();
		}
		else {
			ResponseDTO response = new ResponseDTO(cep, null, valor);
			response.setCep(cep);
			response.setMensagem(null);
			response.setValor(valor);
			return ResponseEntity.ok().body(response);
			
		}
			
		}
		catch (Exception e) {
		return ResponseEntity.notFound().build();
		}
	}
	
}
