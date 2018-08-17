package br.com.caelum.leilao.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Avaliador {
	
	private double maiorDeTodos = Double.NEGATIVE_INFINITY;
	private double menorDeTodos = Double.POSITIVE_INFINITY;
	private List<Lance> maiores;

	public void avalia(Leilao leilao) {
		if(leilao.getLances().size() < 1) {
			throw new RuntimeException();
		}
		
		for(Lance lance : leilao.getLances()) {
			if(lance.getValor() > maiorDeTodos) maiorDeTodos = lance.getValor();
			if(lance.getValor() < menorDeTodos ) menorDeTodos = lance.getValor();
		}
		
		maiores = new ArrayList<Lance>(leilao.getLances());
		maiores.sort((v1, v2) -> Double.compare(v2.getValor(), v1.getValor()));
		maiores = maiores.subList(0, maiores.size() > 3 ? 3 : maiores.size());
	}
	
	public double getMaiorDeTodos() {
		return this.maiorDeTodos;
	}
	
	public double getMenorDeTodos() {
		return this.menorDeTodos;
	}
	
	public List<Lance> getTresMaiores() {
		return maiores;
	}
}
