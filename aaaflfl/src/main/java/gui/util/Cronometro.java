/**
 * @author Vanilson Pires
 * @date 2018-03-04
 */
package gui.util;

import java.util.Observable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Representa um cronômetro convencional
 * @author Vanilson Pires
 * @date 2018-03-04
 */
public class Cronometro extends Observable implements Runnable{

	private String timer;
	private boolean zerado;
	private boolean runing;
	private Executor executor;
	private boolean ativo;

	/**
	 * Construtor padrão do cronômetro
	 * @author Vanilson Pires
	 * @date 2018-03-04
	 */
	public Cronometro() {   
        this.timer = "00:00:00:0000";   
        this.zerado = true;
        this.runing = false;
    }
	
	/**
	 * Inicia o cronômetro
	 * @author Vanilson Pires
	 * @date 2018-03-04
	 */
	public void start(){
		
		if(executor==null){
			executor = Executors.newSingleThreadExecutor();
			this.ativo = true;
			executor.execute(this);
		}	
		
		runing = true;
		ativo = true;
	}
	
	public void pause(){
		this.runing = false;
	}
	
	/**
	 * Reseta o cronômetro
	 * @author Vanilson Pires
	 * @date 2018-03-04
	 */
	public void reset(){
		zerado = true;
		this.timer = "00:00:00:0000";   
	}
	
	/**
	 * Para o serviço
	 * @author Vanilson Pires
	 * @date 2018-03-04
	 */
	public void stop(){
		this.ativo = false;
		this.executor = null;
	}
	
	public synchronized void run() {
		try {
			int segundo = 0;
			int hora = 0;
			int minuto = 0;
			int milessegundos = 0;
			while (ativo) {
				if (runing) {
					
					if (zerado) {
						segundo = 0;
						hora = 0;
						minuto = 0;
						zerado = false;
					}
					
					if(milessegundos>=999){
						if (segundo == 59) {
							segundo = 00;
							minuto = minuto + 1;
						}
						if (minuto == 59) {
							minuto = 00;
							hora = hora + 1;
						}
						segundo++;
						milessegundos=0;
					}
					
					milessegundos++;
					setTimer(completaComZero(hora) + ":" + completaComZero(minuto) + ":"
							+ completaComZero(segundo)+ ":"+ completaComZeroMills(milessegundos));
				}
				wait(1L);// aguardando 1 milisegundos
			}
			executor = null;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	private String completaComZero(Integer i) {
		String retorno = null;
		if (i < 10) {
			retorno = "0" + i;
		} else {
			retorno = i.toString();
		}
		return retorno;
	}
	
	private String completaComZeroMills(Integer i) {
		String retorno = completaComZero(i);
		if(i<100){
			retorno = "0"+retorno;
		}
		return retorno;
	}

	/**
	 * @author Vanilson Pires
	 * @date 2018-03-04
	 * @return timer
	 */
	public String getTimer() {
		return timer;
	}

	/**
	 * @author Vanilson Pires
	 * @date 2018-03-04
	 * @param O timer para set
	 */
	private void setTimer(String timer) {
		setChanged();
		notifyObservers();
		this.timer = timer;
	}
}