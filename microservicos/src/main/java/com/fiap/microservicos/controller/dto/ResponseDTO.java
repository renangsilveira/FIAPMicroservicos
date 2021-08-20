package com.fiap.microservicos.controller.dto;

public class ResponseDTO {

	private Long cep;
	private String mensagem;
	private Double valor;
	
	public ResponseDTO(Long cep, String mensagem, Double valor) {
		super();
		this.cep = cep;
		this.mensagem = mensagem;
		this.valor = valor;
	}
		
	public Long getCep() {
		return cep;
	}
	public void setCep(Long cep) {
		this.cep = cep;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
		
}
