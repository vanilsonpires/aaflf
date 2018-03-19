/**
 * @author Vanilson Pires
 * 11 de mar de 2018 2018-03-11
 *
 */
package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import nucleo.Engine;

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
		this.setLayout(new GridLayout(1, 2, 10, 10));
		this.add(createPanelEsquerdo());
		this.add(createPanelDireito());
		Border border = getBorder();
		Border margin = new EmptyBorder(10,10,10,10);
		setBorder(new CompoundBorder(border, margin));
	}
	
	private JPanel createPanelEsquerdo(){
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new BorderLayout());
		jPanel.setBorder(BorderFactory.createTitledBorder("Dados de Entrada:"));	
		JTable jTable = new JTable(Engine.getInstance().getListaEsquerda());
		jTable.setTableHeader(null);
		jPanel.add(new JScrollPane(jTable) );
		return jPanel;
	}
	
	private JPanel createPanelDireito(){
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new BorderLayout());
		jPanel.setBorder(BorderFactory.createTitledBorder("Dados de Saida:"));
		JTable jTable = new JTable(Engine.getInstance().getListaDireita());
		jTable.setTableHeader(null);
		jPanel.add(new JScrollPane(jTable));
		return jPanel;
	}
}
