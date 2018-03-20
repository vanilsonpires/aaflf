/**
 * @author Vanilson Pires
 * 11 de mar de 2018 2018-03-11
 *
 */
package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import gui.Gui.Algoritmo;
import gui.Gui.Ordenacao;
import gui.Gui.TypeDado;
import nucleo.Engine;
import nucleo.Task;

/**
 * Classe que representa o painel do topo
 * @author Vanilson Pires 11 de mar de 2018 2018-03-11
 *
 */
@SuppressWarnings("serial")
public class PainelTopo extends JPanel {

	private JComboBox<String> algoritmos;
	private JComboBox<String> tiposDeDados;
	private JComboBox<String> ordenacao;
	private File arquivo;
	private JLabel fileSelected = new JLabel("");

	/**
	 * Construtor padrão
	 * @author Vanilson Pires 18 de mar de 2018 2018-03-18
	 *
	 */
	public PainelTopo() {
		this.setLayout(new BorderLayout()); //Altera o layout
		this.add(createPanel(), BorderLayout.CENTER); //Adiciona o painel central
		Border border = getBorder();  //Pega a instancia da borda deste painel
		Border margin = new EmptyBorder(10, 10, 10, 10); //Instancia uma nova margim
		setBorder(new CompoundBorder(border, margin)); //Altera a borda deste painel com a nova margim
	}

	/**
	 * Criador do painel central
	 * @author Vanilson Pires
	 * 19 de mar de 2018 2018-03-19
	 *
	 * @return
	 */
	private JPanel createPanel() {

		//Instancia um novo painel
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new BorderLayout());
		jPanel.setBorder(BorderFactory.createTitledBorder("Configurações de Processamento:"));

		//Adiciona uma label
		jPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel labelAlgoritmo = new JLabel("Algorítmo: ");
		labelAlgoritmo.setFont(new Font("Arial", Font.BOLD, 14));
		jPanel.add(labelAlgoritmo);

		//Inicializa o comboBox dos algortimos disponiveis
		algoritmos = initComboAlgoritmos();
		jPanel.add(algoritmos);

		//Adiciona uma label dos tipos de dados
		JLabel labelTypeDado = new JLabel("Tipo de dado: ");
		labelTypeDado.setFont(new Font("Arial", Font.BOLD, 14));
		jPanel.add(labelTypeDado);

		//Inicializa um comboBox dos tipos de dados
		tiposDeDados = initComboTypeDado();
		jPanel.add(tiposDeDados);
		
		//Adiciona uma label com a ordenação
		JLabel labelOrdenacao = new JLabel("Ordenação: ");
		labelOrdenacao.setFont(new Font("Arial", Font.BOLD, 14));
		jPanel.add(labelOrdenacao);

		//Inicialiaza um comboBox com as ordenações disponíveis
		ordenacao = initComboOrdenacao();
		jPanel.add(ordenacao);

		//Adiciona um botão para selecionar arquivo
		JButton selectFile = new JButton("Selecione um arquivo");
		selectFile.addActionListener(new ActionListener() {
			
			/**
			 * Ação selecionar
			 */
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selecionarArquivo();
			}
		});
		jPanel.add(selectFile);
		jPanel.add(fileSelected);

		//Adiciona um botão para inicializar os processos
		JButton initProcess = new JButton("INICIAR");
		initProcess.addActionListener(new ActionListener() {
			/**
			 * Ação de inicializar o processo
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				iniProcess();
			}
		});

		jPanel.add(initProcess);

		return jPanel;
	}

	/**
	 * Método que cria um comboBox para de algorítmos
	 * @author Vanilson Pires
	 * 19 de mar de 2018 2018-03-19
	 *
	 * @return
	 */
	private JComboBox<String> initComboAlgoritmos() {
		JComboBox<String> box = new JComboBox<>();
		box.addItem("Selecione um algorítmo");
		for (Algoritmo algoritmo : Gui.Algoritmo.values()) {
			box.addItem(algoritmo.name());
		}
		return box;
	}

	/**
	 * Método que cria um comboBox dos tipos de dados
	 * @author Vanilson Pires
	 * 19 de mar de 2018 2018-03-19
	 *
	 * @return
	 */
	private JComboBox<String> initComboTypeDado() {
		JComboBox<String> box = new JComboBox<>();
		box.addItem("Selecione um tipo de dado");
		for (TypeDado typeDado : Gui.TypeDado.values()) {
			box.addItem(typeDado.name());
		}
		return box;
	}
	
	/**
	 * Método que cria um comboBox para seleção da ordenação
	 * @author Vanilson Pires
	 * 19 de mar de 2018 2018-03-19
	 *
	 * @return
	 */
	private JComboBox<String> initComboOrdenacao() {
		JComboBox<String> box = new JComboBox<>();
		box.addItem("Selecione um tipo de ordenação");
		for (Ordenacao ordenacao : Gui.Ordenacao.values()) {
			box.addItem(ordenacao.name());
		}
		return box;
	}

	/**
	 * Método que abre um diálogo para seleção de arquivo
	 * @author Vanilson Pires
	 * 19 de mar de 2018 2018-03-19
	 *
	 */
	private void selecionarArquivo() {
	
		JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.home")));
		int returnVal = chooser.showOpenDialog(this);

		if (returnVal != JFileChooser.APPROVE_OPTION) {
			// Cancelar
			return;
		}

		this.arquivo = chooser.getSelectedFile();
		if(arquivo!=null && arquivo.exists())
			fileSelected.setText(arquivo.getName());
	
		fileSelected.repaint();
	}

	/**
	 * Método que inicializa o processo de ordenamento
	 * @author Vanilson Pires
	 * 19 de mar de 2018 2018-03-19
	 *
	 */
	private void iniProcess() {

		try {
			//Inicia as validações obrigatórias
			if (this.arquivo == null) {
				JOptionPane.showMessageDialog(this, "Necessário selecionar um arquivo!", "Operação não realizada",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			if (!this.arquivo.exists()) {
				JOptionPane.showMessageDialog(this, "O arquivo selecionado não existe!", "Operação não realizada",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			if (this.arquivo.isDirectory()) {
				JOptionPane.showMessageDialog(this, "O arquivo selecionado é um diretório!", "Operação não realizada",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			if (algoritmos.getSelectedIndex() < 1) {
				JOptionPane.showMessageDialog(this, "Necessário selecionar o algorítmo", "Operação não realizada",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			if (tiposDeDados.getSelectedIndex() < 1) {
				JOptionPane.showMessageDialog(this, "Necessário selecionar o tipo de dados", "Operação não realizada",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			if (ordenacao.getSelectedIndex() < 1) {
				JOptionPane.showMessageDialog(this, "Necessário selecionar o tipo de ordenação", "Operação não realizada",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			//Pega a instância da engine
			Engine engine = Engine.getInstance();
			
			//Adiciona os novos dados para trabalhar
			engine.setFile(arquivo);
			engine.setAlgoritmo(Algoritmo.values()[algoritmos.getSelectedIndex() - 1]);
			engine.setTypeDado(TypeDado.values()[tiposDeDados.getSelectedIndex() - 1]);
			engine.setOrdenacao(Ordenacao.values()[ordenacao.getSelectedIndex() - 1]);
			
			//Instância uma tarefa para rodar em segundo plano
			Task task = new Task() {
				@Override
				public void run() {
					try {
						engine.start();
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
				}
			};
			
			//Iniciando a execução da tarefa
			task.start();
			
			//Tratamento de erro
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}
