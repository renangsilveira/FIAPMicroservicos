package com.fiap.microservicos.service;

import java.util.Arrays;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.util.Precision;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AnalisarService {

	public static Double analyzeByCep(Long cep) {
		int maxDistance = 20000;
		int minDistance = 0;
		Double ValorKM = 1.98;
		String[] cepsStorage = {"03071100","02404060","11608623","05508000"};
		double valor = 0;

		//iterating all elements in the array
		int[] num = new int[cepsStorage.length];
		for (int i = 0; i < cepsStorage.length; i++) {
			int distanciaCalculada = getDistanceBetweenCEPs(cep, Long.valueOf(cepsStorage[i]));
			if (distanciaCalculada != 0 ) num[i] = distanciaCalculada;
		}

		System.out.println("Valor das Distancias = ");
		System.out.println(Arrays.toString(num));
		
		minDistance = findMinDistance(num);
		
		System.out.println("Distancia minima = " + minDistance);
		
		if(minDistance<maxDistance) {
			Double minDistanceKM = (double) (minDistance/1000);
			valor = minDistanceKM * ValorKM; // Ja tem arredondamento do valor					
			valor = Math.round(valor * 100.0) / 100.0;
		}
		else valor = 0;

		
		System.out.println("Valor Calculado = " + valor);
		
		
		return valor;
	}


	private static int getDistanceBetweenCEPs(Long cepClient, Long cepStorage)
	{
		int RealDistance = 0;

		String cepClientS = StringUtils.leftPad(cepClient.toString(), 8, '0');
		String cepStorageS = StringUtils.leftPad(cepStorage.toString(), 8, '0');

		final String uri = "https://maps.googleapis.com/maps/api/directions/xml?origin=" + cepClientS + "&destination=" + cepStorageS + "&sensor=false&key=AIzaSyB7ArndI8BAQSeihsUye9twQF068Nelp20";

		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class);

		RealDistance = GetMaxValueDistance(result);

		return RealDistance;

	}


	private static int GetMaxValueDistance(String result) {
		int RealDistance = 0;
		if (result.contains("ZERO_RESULTS")) {
			return 0;
		}
		else {
			result = result.substring(result.lastIndexOf("<value>") + 1);
			result = result.substring(0, result.lastIndexOf("</value>"));
		}
		result = result.replaceAll("[^0-9]", ""); // returns 123
		RealDistance = Integer.parseInt(result);
		return RealDistance;
	}

	
	public static int findMinDistance(int[] num) {
	    int smallest = Integer.MAX_VALUE;
	    for(int i=0; i<num.length; i++) {
	        if(num[i] > 0 && num[i]<smallest) {
	            smallest = num[i];
	        }
	    }
	    return smallest;
	}


}
