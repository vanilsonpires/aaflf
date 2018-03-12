/**
 * @author Vanilson Pires
 * 11 de mar de 2018 2018-03-11
 *
 */
package gui;

import java.awt.GridLayout;

import javax.swing.JPanel;

/**
 * @author Vanilson Pires
 * 11 de mar de 2018 2018-03-11
 * Representa o painel central da aplicação
 */
@SuppressWarnings("serial")
public class PanelCenter extends JPanel{
	
	/**
	 * Construtor padrão da classe
	 * @author Vanilson Pires
	 * 12 de mar de 2018 2018-03-12
	 *
	 */
	public PanelCenter() {
		this.setLayout(new GridLayout(1, 3));
	}

}
