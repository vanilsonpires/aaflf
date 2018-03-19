/**
 * @author Vanilson Pires
 * 18 de mar de 2018 2018-03-18
 *
 */
package nucleo;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import gui.Gui.Algoritmo;
import gui.Gui.Ordenacao;
import gui.Gui.TypeDado;
import gui.util.Cronometro;
import gui.util.TableModel;

/**
 * @author Vanilson Pires 18 de mar de 2018 2018-03-18
 *
 */
public class Engine extends Observable implements Observer {

	private JLabel cronometroLabel;
	private Cronometro cronometro;
	private JProgressBar progressBar;
	private JLabel statusBar;
	private Ordenacao ordenacao;
	
	private static Engine engine;
	private File file;
	private Algoritmo algoritmo;
	private TypeDado typeDado;
	
	private TableModel listaEsquerda;
	private TableModel listaDireita;

	/**
	 * @author Vanilson Pires 18 de mar de 2018 2018-03-18
	 *
	 */
	private Engine() {
		this.cronometro = new Cronometro();
		cronometroLabel = new JLabel(cronometro.getTimer());
		cronometroLabel.setFont(new Font("Arial", Font.PLAIN, 22));
		cronometro.addObserver(this);
		progressBar = new JProgressBar();
		statusBar = new JLabel("0%");
		statusBar.setFont(new Font("Arial", Font.PLAIN, 22));
		listaDireita = new TableModel(new ArrayList<>());
		listaEsquerda = new TableModel(new ArrayList<>());
	}

	public static Engine getInstance() {

		if (engine == null)
			engine = new Engine();

		return engine;
	}

	public void start() throws Exception {
		
		try {
			cronometro.start();
			
			//Inicia a leitura do arquivo..
			lerArquivo();
			
			switch (algoritmo) {

			case Insertion_Sort:
				orderInsertionSort();
				break;

			case Merge_Sort:
				orderMergeSort();
				break;

			case Selection_Sort:
				orderSelectonSort();
				break;

			default:
				break;
			}
			
			cronometro.stop();
		} catch (Exception e) {
			cronometro.stop();
			throw e;
		}
	}

	private void orderInsertionSort() throws Exception {
		
		statusBar.setText("Processando...");
		
		listaDireita.addAll(listaEsquerda.getDados());
		
		for (int index = 0; index < listaDireita.getDados().size(); index++)  {
			String a = listaDireita.getDados().get(index);  
		    for (int j = index - 1; j >= 0 && compareTo(listaDireita.getDados().get(j), a); j--) {
		    	listaDireita.getDados().set((j + 1), listaDireita.getDados().get(j));  
		    	listaDireita.getDados().set(j, a);  
		    }
		    statusBar.setText(new DecimalFormat("##.##").format((((double)index/(double)listaDireita.getDados().size())*100.0))+"%");
		    listaDireita.update();
		}
		
		statusBar.setText("Pronto");
	}
	
	/**
	 * Retorna a ordenação do dado
	 * @author Vanilson Pires
	 * 18 de mar de 2018 2018-03-18
	 *
	 * @param a
	 * @param b
	 * @return
	 * @throws Exception
	 */
	private boolean compareTo(String a, String b) throws Exception{
		
		//Se for crescente
		if(ordenacao== Ordenacao.CRESCENTE){
			
			switch (typeDado) {

			case String:
				
				int resultCompacao = a.compareTo(b);
				
				if(resultCompacao==1){
					//então é maior
					return true;
				}else{
					//então é menor ou igual
					return false;
				}

			case Integer:
				
				Integer aI = Integer.valueOf(a.trim()); //a como inteiro
				Integer bI = Integer.valueOf(b.trim()); //b como inteiro
				
				resultCompacao = aI.compareTo(bI);
				
				if(resultCompacao==1){
					//então é maior
					return true;
				}else{
					//então é menor ou igual
					return false;
				}

			case Date:
				
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				
				Date aD = format.parse( a.trim() );
				Date bD = format.parse( b.trim() );
				
				resultCompacao = aD.compareTo(bD);
				
				if(resultCompacao==1){
					//então é maior
					return true;
				}else{
					//então é menor ou igual
					return false;
				}

			default:
				throw new Exception(ordenacao.name()+" não implementado") ;
			}
			
		//Se não, é decrescente
		}else{
			
			switch (typeDado) {

			case String:
				
				int resultCompacao = a.compareTo(b);
				
				if(resultCompacao==1){
					//então é maior
					return false;
				}else{
					//então é menor ou igual
					return true;
				}

			case Integer:
				
				Integer aI = Integer.valueOf(a.trim()); //a como inteiro
				Integer bI = Integer.valueOf(b.trim());//b como inteiro
				
				resultCompacao = aI.compareTo(bI);
				
				if(resultCompacao==1){
					//então é maior
					return false;
				}else{
					//então é menor ou igual
					return true;
				}

			case Date:
				
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				
				Date aD = format.parse( a.trim() );
				Date bD = format.parse( b.trim() );
				
				resultCompacao = aD.compareTo(bD);
				
				if(resultCompacao==1){
					//então é maior
					return false;
				}else{
					//então é menor ou igual
					return true;
				}

			default:
				throw new Exception(ordenacao.name()+" não implementado") ;
			}
		}
		
	}

	private void orderMergeSort() {

	}

	private void orderSelectonSort() {

	}
	
	/**
	 * Método que faz a leitura do arquivo CSV
	 * @author Vanilson Pires
	 * @date 2018-03-18
	 * @param arquivo
	 * @return
	 */
	private boolean lerArquivo() {
		
		BufferedReader br = null;
		listaDireita.reset();
		listaEsquerda.reset();
		statusBar.setText("Lendo arquivo...");
		progressBar.setIndeterminate(true);
		
		try {
			if (file != null && !file.getName().equals("")) {

				//Ler o nome do arquivo
				String sNomeArquivo = file.getName();
				
				//Ler a extensao do arquivo
				String extensao = getFileExtension(sNomeArquivo);
				
				//Faz a verificação para permitir o processamento somente de arquivos em CSV
				if (extensao.equalsIgnoreCase("csv")) {				
					
					InputStream in = new FileInputStream(file); //Cria um stream para manusear os dados do arquivo
					InputStreamReader isr = new InputStreamReader(in, "UTF-8"); //Especifica o formato em UTF-8
					br = new BufferedReader(isr); // Gera o arquivo em Buffered
					
					//Inicia a leitura do aquivo
					String s = br.readLine(); // Lendo a primeira linha e salva em string...
					
					int qtd = 0;
					while (s != null) {		
						qtd++;						
						if(qtd!=1){
							String[] dadosDaLinha = s.split(";"); // Separa cada dado da linha em um vetor	
							listaEsquerda.addAll(Arrays.asList(dadosDaLinha)); //Adiciona os arquivos na lista..
						}
						s = br.readLine(); // Ler a próxima linha	
					}
					return true;
				}else {
					cronometro.stop();
					JOptionPane.showMessageDialog(null, "Somente arquivos 'CSV' podem ser lidos. Por favor tente novamente!", "Arquivo inválido", JOptionPane.WARNING_MESSAGE);
					return false;
				}				
			} else {
				cronometro.stop();
				JOptionPane.showMessageDialog(null, "Arquivo não selecionado ou inválido. Favor tente novamente!", "Operação não realizada", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		} catch (Exception e) {
			cronometro.stop();
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}finally {
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			statusBar.setText("Leitura completa");
			progressBar.setIndeterminate(false);
			listaEsquerda.update();
		}
	}
	
	/**
	 * Retorna a extensão do arquivo
	 * @author Vanilson Pires
	 * @date 2018-03-18
	 * @param fileName
	 * @return
	 */
	private static String getFileExtension(String fileName) {
		int index = fileName.lastIndexOf('.');
		if (index > 0 && index < fileName.length() - 1) {
			return fileName.substring(index + 1).toLowerCase();
		} else {
			return "";
		}
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @author Vanilson Pires 18 de mar de 2018 2018-03-18
	 * @param file
	 *            the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @return the algoritmo
	 */
	public Algoritmo getAlgoritmo() {
		return algoritmo;
	}

	/**
	 * @author Vanilson Pires 18 de mar de 2018 2018-03-18
	 * @param algoritmo
	 *            the algoritmo to set
	 */
	public void setAlgoritmo(Algoritmo algoritmo) {
		this.algoritmo = algoritmo;
	}

	/**
	 * @return the typeDado
	 */
	public TypeDado getTypeDado() {
		return typeDado;
	}

	/**
	 * @author Vanilson Pires 18 de mar de 2018 2018-03-18
	 * @param typeDado
	 *            the typeDado to set
	 */
	public void setTypeDado(TypeDado typeDado) {
		this.typeDado = typeDado;
	}

	/**
	 * @return the cronometroLabel
	 */
	public JLabel getCronometroLabel() {
		return cronometroLabel;
	}

	/**
	 * @author Vanilson Pires 18 de mar de 2018 2018-03-18
	 * @param cronometroLabel
	 *            the cronometroLabel to set
	 */
	public void setCronometroLabel(JLabel cronometroLabel) {
		this.cronometroLabel = cronometroLabel;
	}

	/**
	 * @return the cronometro
	 */
	public Cronometro getCronometro() {
		return cronometro;
	}

	/**
	 * @author Vanilson Pires 18 de mar de 2018 2018-03-18
	 * @param cronometro
	 *            the cronometro to set
	 */
	public void setCronometro(Cronometro cronometro) {
		this.cronometro = cronometro;
	}

	/**
	 * @return the progressBar
	 */
	public JProgressBar getProgressBar() {
		return progressBar;
	}

	/**
	 * @return the statusBar
	 */
	public JLabel getStatusBar() {
		return statusBar;
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		cronometroLabel.setText(cronometro.getTimer());
		cronometroLabel.revalidate();
	}

	/**
	 * @return the listaEsquerda
	 */
	public TableModel getListaEsquerda() {
		return listaEsquerda;
	}

	/**
	 * @author Vanilson Pires
	 * 18 de mar de 2018 2018-03-18
	 * @param listaEsquerda the listaEsquerda to set
	 */
	public void setListaEsquerda(TableModel listaEsquerda) {
		this.listaEsquerda = listaEsquerda;
	}

	/**
	 * @return the listaDireita
	 */
	public TableModel getListaDireita() {
		return listaDireita;
	}

	/**
	 * @author Vanilson Pires
	 * 18 de mar de 2018 2018-03-18
	 * @param listaDireita the listaDireita to set
	 */
	public void setListaDireita(TableModel listaDireita) {
		this.listaDireita = listaDireita;
	}

	/**
	 * @return the ordenacao
	 */
	public Ordenacao getOrdenacao() {
		return ordenacao;
	}

	/**
	 * @author Vanilson Pires
	 * 18 de mar de 2018 2018-03-18
	 * @param ordenacao the ordenacao to set
	 */
	public void setOrdenacao(Ordenacao ordenacao) {
		this.ordenacao = ordenacao;
	}

}