package modelo.servicos;

import java.time.Duration;

import modelo.entidades.Aluguel;
import modelo.entidades.Fatura;

public class ServicoAluguel {
	private Double precoDia;
	private Double precoHora;

	private ServicoImpostoBr servicoImpostoBr;

	public ServicoAluguel(Double precoDia, Double precoHora, ServicoImpostoBr servicoImpostoBr) {
		this.precoDia = precoDia;
		this.precoHora = precoHora;
		this.servicoImpostoBr = servicoImpostoBr;
	}
	
	public void processarFatura(Aluguel aluguel) {
		// pega a duração em minutos entre duas datas
		double minutos = Duration.between(aluguel.getInicio(),aluguel.getFim()).toMinutes();
		double horas = minutos / 60.0;
		double pagamentoBasico;
		
		if (horas <= 12.0) {
			pagamentoBasico = precoHora * Math.ceil(horas);
		}
		else {					// Math.ceil arredonda para cima
			pagamentoBasico = precoDia * Math.ceil(horas / 24);
		}

		double imposto = servicoImpostoBr.calculaImposto(pagamentoBasico);
		
		aluguel.setFatura(new Fatura(pagamentoBasico, imposto)); 
	}
}
