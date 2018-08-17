package br.com.caelum.leilao.test;

import org.junit.Test;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Avaliador;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;

public class TesteDoAvaliador {
	
	private Avaliador leiloeiro;
	private Usuario joao;
	private Usuario maria;
	private Usuario jose;
	
	@Before
	public void criaAvaliador() {
		leiloeiro = new Avaliador();
		joao = new Usuario("Joao");
		maria = new Usuario("Maria");
		jose = new Usuario("José");
	}
	
	@Test(expected=RuntimeException.class)
	public void nãoDeveAvaliarLeilãoSemNenhumLance() {
		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 novo").constroi();
		leiloeiro.avalia(leilao);
	}

	@Test
	public void deveEntenderLeilao() {
		
		
		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 novo")
				.lance(joao, 400.0)
				.lance(maria, 250.0)
				.lance(joao, 300.0)
				.constroi();
		
		leiloeiro.avalia(leilao);
		
		
		double maiorEsperado = 400;
		double menorEsperado = 250;
		
		assertThat(leiloeiro.getMaiorDeTodos(), equalTo(maiorEsperado));
		assertThat(leiloeiro.getMenorDeTodos(), equalTo(menorEsperado));
	}
	
	@Test
	public void deveEntenderLeilaoComUmUnicoLance() {
		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 novo")
				.lance(joao, 1000.0)
				.constroi();
		
		leiloeiro.avalia(leilao);
		
		assertThat(leiloeiro.getMaiorDeTodos(), equalTo(1000.0));
		assertThat(leiloeiro.getMenorDeTodos(), equalTo(1000.0));
	}
	
	@Test
	public void deveEncontrarOsTresMaioresLances() {
		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 novo")
				.lance(joao, 100.0)
				.lance(maria, 200.0)
				.lance(joao, 300.0)
				.lance(maria, 400.0)
				.constroi();
		
		leiloeiro.avalia(leilao);
		
		List<Lance> maiores = leiloeiro.getTresMaiores();
		assertEquals(3, maiores.size());
		
		//vai usar o método equals da classe Lance
		assertThat(maiores, hasItems(
				new Lance(maria, 400),
				new Lance(joao, 300),
				new Lance(maria, 200)
		));
	}
}
