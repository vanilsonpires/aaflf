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
 * Representa o painel central da aplicação
 * @author Vanilson Pires
 * 11 de mar de 2018 2018-03-11
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
	
	/**
	 * Método que cria o painel esquerdo
	 * @author Vanilson Pires
	 * 19 de mar de 2018 2018-03-19
	 *
	 * @return
	 */
	private JPanel createPanelEsquerdo(){
		JPanel jPanel = new JPanel();//Instancia um painel
		jPanel.setLayout(new BorderLayout());//Adiciona seu layout
		jPanel.setBorder(BorderFactory.createTitledBorder("Dados de Entrada:"));//Cria uma borda
		JTable jTable = new JTable(Engine.getInstance().getListaEsquerda()); //Instancia uma tabela com os dados da engine
		jTable.setTableHeader(null);//Retira o header da tabela
		jPanel.add(new JScrollPane(jTable) );//Adiciona rolagem se for necessário
		return jPanel;//retorna
	}
	
	/**
	 * Método que cria o painel direito
	 * @author Vanilson Pires
	 * 19 de mar de 2018 2018-03-19
	 *
	 * @return
	 */
	private JPanel createPanelDireito(){
		JPanel jPanel = new JPanel();//Instancia um painel
		jPanel.setLayout(new BorderLayout());//Adiciona seu layout
		jPanel.setBorder(BorderFactory.createTitledBorder("Dados de Saida:"));//Cria uma borda
		JTable jTable = new JTable(Engine.getInstance().getListaDireita()); //Instancia uma tabela com os dados da engine
		jTable.setTableHeader(null);//Retira o header da tabela
		jPanel.add(new JScrollPane(jTable));//Adiciona rolagem se for necessário
		return jPanel;//retorna
	}
}
