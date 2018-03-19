/**
 * @author Vanilson Pires
 * 11 de mar de 2018 2018-03-11
 *
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * Classe principal da interface
 * @author Vanilson Pires
 * 11 de mar de 2018 2018-03-11
 *
 */
@SuppressWarnings("serial")
public class Gui extends JFrame{
	
	/**
	 * Enum que representa os algorítmos implementados
	 * @author Vanilson Pires
	 * 19 de mar de 2018 2018-03-19
	 *
	 */
	public static enum Algoritmo{
		Insertion_Sort,
		Merge_Sort,
		Selection_Sort
	}
	
	/**
	 * Enum que representa os tipos de dados para possível leitura implementados
	 * @author Vanilson Pires
	 * 19 de mar de 2018 2018-03-19
	 *
	 */
	public static enum TypeDado{
		String,
		Integer,
		Date
	}
	
	/**
	 * Enum que representa os tipos de ordenação disponível
	 * @author Vanilson Pires
	 * 19 de mar de 2018 2018-03-19
	 *
	 */
	public static enum Ordenacao{
		CRESCENTE, DECRESCENTE
	}
	
	/**
	 * Construtor da interface principal
	 * @author Vanilson Pires
	 * @date 2018-03-11
	 */
	public Gui() {
		
		super("AAFLF"); // Add titulo do JFrame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Setado comando default para encerrar o programa quando fechar
		this.setExtendedState(MAXIMIZED_BOTH); // Maximizar
		this.setLocationRelativeTo(null); // Centralizar JFrame no centro da tela
		this.setLayout(new BorderLayout()); // Seta um Layout
		this.add(new PanelCabecalho(),BorderLayout.NORTH); // Add o painel no topo
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new BorderLayout()); //Seta o layout
		jPanel.add(new PanelCenter(), BorderLayout.CENTER); // Add painel do centro
		jPanel.add(new PainelTopo(), BorderLayout.NORTH); // Add painel do fundo
		this.add(jPanel, BorderLayout.CENTER);
		this.add(new PainelBotton(), BorderLayout.SOUTH);
		this.setMinimumSize(new Dimension(1024, 600));
		
		UIManager.put("TitledBorder.font", new Font("Arial", Font.BOLD, 17));
		
		setVisible(true); // Mostrar JFrame
	}

}
