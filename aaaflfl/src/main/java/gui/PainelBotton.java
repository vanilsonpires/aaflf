/**
 * @author Vanilson Pires
 * 18 de mar de 2018 2018-03-18
 *
 */
package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import nucleo.Engine;

/**
 * Classe que representa o painel do fundo
 * @author Vanilson Pires
 * 18 de mar de 2018 2018-03-18
 *
 */
@SuppressWarnings("serial")
public class PainelBotton extends JPanel {
	
	/**
	 * Construtor da classe
	 * @author Vanilson Pires
	 * 18 de mar de 2018 2018-03-18O
	 *
	 */
	public PainelBotton() {
		
		this.setLayout(new BorderLayout());//Seta o layout deste painel
		JPanel panel = new JPanel(); //Instancia um novo painel
		panel.setLayout(new FlowLayout(FlowLayout.CENTER)); //Altera o layout do painel anterior para flow 
		JLabel jLabel = new JLabel("Cronômetro: ");// Instancia uma label
		jLabel.setFont(new Font("Arial", Font.PLAIN, 22));//Altera a fonte
		panel.add(jLabel); //Adiciona a label ao painel
		panel.add(Engine.getInstance().getCronometroLabel()); //Pega a label do cronometro na engine e add
		this.add(panel, BorderLayout.NORTH); //coloca este painel no topo
		
		JPanel panelSul = new JPanel();//Instancia um novo painel
		panelSul.setLayout(new FlowLayout(FlowLayout.CENTER)); //Altera o layout
		panelSul.add(Engine.getInstance().getStatusBar());// Pega o status da barra de progresso da engine e add
		panelSul.add(Engine.getInstance().getProgressBar()); //Adiciona a barra de progresso da engine
		this.add(panelSul,BorderLayout.SOUTH);//Adiciona o painel no fim
		
	}
}
