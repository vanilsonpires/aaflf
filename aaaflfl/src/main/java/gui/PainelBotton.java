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
 * @author Vanilson Pires
 * 18 de mar de 2018 2018-03-18
 *
 */
@SuppressWarnings("serial")
public class PainelBotton extends JPanel {
	
	/**
	 * @author Vanilson Pires
	 * 18 de mar de 2018 2018-03-18O
	 *
	 */
	public PainelBotton() {
		
		this.setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel jLabel = new JLabel("Cronômetro: ");
		jLabel.setFont(new Font("Arial", Font.PLAIN, 22));
		panel.add(jLabel);		
		panel.add(Engine.getInstance().getCronometroLabel());
		this.add(panel, BorderLayout.NORTH);
		
		JPanel panelSul = new JPanel();
		panelSul.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelSul.add(Engine.getInstance().getStatusBar());
		panelSul.add(Engine.getInstance().getProgressBar());
		this.add(panelSul,BorderLayout.SOUTH);
		
	}
}
