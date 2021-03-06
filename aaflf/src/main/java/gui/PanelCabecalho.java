/**
 * @author Vanilson Pires
 * 11 de mar de 2018 2018-03-11
 *
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

/**
 * Representa o painel do topo da aplicação
 * @author Vanilson Pires
 * 11 de mar de 2018 2018-03-11
 */
@SuppressWarnings("serial")
public class PanelCabecalho extends JPanel{
	
	/**
	 * Construtor da classe
	 * @author Vanilson Pires
	 * 11 de mar de 2018 2018-03-11
	 *
	 */
	public PanelCabecalho() {		
		this.setLayout(new BorderLayout());
		addLogo();		
		addTitle();
	}
	
	/**
	 * Método que adiciona a logo
	 * @author Vanilson Pires
	 * 19 de mar de 2018 2018-03-19
	 *
	 */
	private void addLogo(){
		JLabel icon = new JLabel(new ImageIcon(getClass().getResource("logo.png")));
		Border border = icon.getBorder();
		Border margin = new EmptyBorder(10, 10, 10, 10);
		icon.setBorder(new CompoundBorder(border, margin));
		icon.setHorizontalAlignment(SwingConstants.LEFT);
		add(icon,BorderLayout.LINE_START);
	}
	
	/**
	 * Método que adiciona o título
	 * @author Vanilson Pires
	 * 19 de mar de 2018 2018-03-19
	 *
	 */
	private void addTitle(){
		JLabel title = new JLabel("Métodos de Ordenamento");
		Border border = title.getBorder();
		Border margin = new EmptyBorder(10, 10, 10, 30);
		title.setBorder(new CompoundBorder(border, margin));
		title.setFont(new Font("Arial", Font.BOLD, 32));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		add(title,BorderLayout.LINE_END);
	}

}