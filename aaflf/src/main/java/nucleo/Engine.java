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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
 * Representa o motor de ordenações
 * @author Vanilson Pires 18 de mar de 2018 2018-03-18
 *
 */
public class Engine implements Observer {

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
	
	private static final String SEPARATOR = " ";

	/**
	 * Construtor padrão
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

	/**
	 * Retorna a instância do motor
	 * @author Vanilson Pires
	 * 19 de mar de 2018 2018-03-19
	 *
	 * @return
	 */
	public static Engine getInstance() {

		if (engine == null)
			engine = new Engine();

		return engine;
	}

	/**
	 * Inicializa os processos
	 * @author Vanilson Pires
	 * 19 de mar de 2018 2018-03-19
	 *
	 * @throws Exception
	 */
	public void start() throws Exception {
		
		try {
			//Starta o cronômetro
			cronometro.start();
			
			//Inicia a leitura do arquivo..
			lerArquivo();
			
			//Swith do tipo de algorítmo
			switch (algoritmo) {

			case Insertion_Sort:
				orderInsertionSort();
				break;
				
			case Merge_Sort:
				statusBar.setText("Processando...");
				progressBar.setIndeterminate(true);
				listaDireita.addAll(listaEsquerda.getDados());
				mergeSort(listaDireita.getDados());
				statusBar.setText("Pronto");
				progressBar.setIndeterminate(false);
				listaDireita.update();
				break;
				
			case Selection_Sort:
				statusBar.setText("Processando...");
				progressBar.setIndeterminate(true);
				listaDireita.addAll(listaEsquerda.getDados());
				selectionSort(listaDireita.getDados());
				statusBar.setText("Pronto");
				progressBar.setIndeterminate(false);
				listaDireita.update();
				break;

			default:
				throw new Exception(algoritmo.name()+" não implementado") ;
			}
			
			cronometro.stop();
		} catch (Exception e) {
			cronometro.stop();
			throw e;
		}
	}

	/**
	 * Implementação do InsertionSort
	 * @author Vanilson Pires
	 * 19 de mar de 2018 2018-03-19
	 *
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private void orderInsertionSort() throws Exception {
		
		//Altera o texto do status bar
		statusBar.setText("Processando...");
		
		//Carrega a lista de dados
		listaDireita.addAll(listaEsquerda.getDados());
		
		//Inicia o laço
		for (int index = 0; index < listaDireita.getDados().size(); index++)  {
			
			//Pega o objeto da laçada atual
			Comparable a = listaDireita.getDados().get(index);  
			
			//Inicia a verificação dos demais objetos da lista
		    for (int j = index - 1; j > 0 && compareTo(listaDireita.getDados().get(j), a)==1; j--) {
		    	
		    	//Atualiza os objeto na lista
		    	listaDireita.getDados().set((j + 1), listaDireita.getDados().get(j));  
		    	listaDireita.getDados().set(j, a);  
		    }
		    
		    //Altera a porcentagem de progresso
		    statusBar.setText(new DecimalFormat("##.##").format((((double)index/(double)listaDireita.getDados().size())*100.0))+"%");
		    
		    //Atualiza a lista
		    listaDireita.update();
		    
		    //Atualiza o progressBar
		    progressBar.setValue((int)(((double)index/(double)listaDireita.getDados().size())*100.0));
		}
		
		//Finaliza o progressBar
		progressBar.setValue(100);
		
		//Atualiza o status do progressBar
		statusBar.setText("Pronto");
	}
	
    /**
     * Inicia o algorítmo merge sort
     * @author Vanilson Pires
     * 8 de abr de 2018 2018-04-08
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
	public void mergeSort(List<Comparable> a) throws Exception {
		List<Comparable> tmp = new ArrayList<Comparable>();
		tmp.addAll(a);
		mergeSort(a, tmp, 0, a.size() - 1);
	}

	@SuppressWarnings("rawtypes")
	private void mergeSort(List<Comparable> a, List<Comparable> tmp, int left, int right) throws Exception {
		if (left < right) {
			int center = (left + right) / 2;
			mergeSort(a, tmp, left, center);
			mergeSort(a, tmp, center + 1, right);
			merge(a, tmp, left, center + 1, right);
		}
	}

	@SuppressWarnings("rawtypes")
	private void merge(List<Comparable> a, List<Comparable> tmp, int left, int right, int rightEnd) throws Exception {
		int leftEnd = right - 1;
		int k = left;
		int num = rightEnd - left + 1;

		while (left <= leftEnd && right <= rightEnd)
			if (compareTo(a.get(left), a.get(right))<= 0)
				tmp.set(k++,a.get(left++));
			else
				tmp.set(k++, a.get(right++));

		while (left <= leftEnd) // Copie o resto do primeiro semestre
			tmp.set(k++, a.get(left++));

		while (right <= rightEnd) // Copie o resto da metade direita
			tmp.set(k++, a.get(right++));

		// Copiar tmp de volta
		for (int i = 0; i < num; i++, rightEnd--)
			a.set(rightEnd, tmp.get(rightEnd));
	}
	

	public void selectionSort(List<Comparable> a) throws Exception {
		int out, in, min;
		for (out = 0; out < a.size() - 1; out++) // laço externo
		{
			min = out; // mínimo
			for (in = out + 1; in < a.size(); in++)
				// laço interno
				if (compareTo(a.get(in), a.get(min)) == -1) // se min maior,
					min = in; // um novo min
			swap(a, out, min); // trocá-los
		}
	}

	private void swap(List<Comparable> list, int one, int two) {
		Comparable temp = list.get(one);
		list.set(one, list.get(two));
		list.set(two, temp);
	}	
	
	/**
	 * Realiza a comparação dos dados
	 * @author Vanilson Pires
	 * 18 de mar de 2018 2018-03-18
	 * @param a
	 * @param b
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private int compareTo(Comparable a, Comparable b) throws Exception{
		
		//Se for crescente
		if(ordenacao== Ordenacao.CRESCENTE){
			
			//Faz a ordenação de acordo com o tipo de dado
			switch (typeDado) {

			case String:
				
				//Compara as strings
				return a.compareTo(b);

			case Integer:
				
				Integer aI = Integer.valueOf(((String) a).trim()); //a como inteiro
				Integer bI = Integer.valueOf(((String) b).trim()); //b como inteiro
				
				return aI.compareTo(bI);

			default:
				throw new Exception(ordenacao.name()+" não implementado") ;
			}
			
		//Se não, é decrescente
		}else{
			
			switch (typeDado) {

			case String:
				
				int retorno = a.compareTo(b);
				
				if(retorno==1)
					return -1;
				
				if(retorno==-1)
					return 1;
				
				return retorno;

			case Integer:
				
				Integer aI = Integer.valueOf(((String) a).trim()); //a como inteiro
				Integer bI = Integer.valueOf(((String) b).trim());//b como inteiro
				
				retorno = aI.compareTo(bI);
				
				if(retorno==1)
					return -1;
				
				if(retorno==-1)
					return 1;
				
				return retorno;

			default:
				throw new Exception(ordenacao.name()+" não implementado") ;
			}
		}
		
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
				if (extensao.equalsIgnoreCase("csv") || extensao.equalsIgnoreCase("txt")) {				
					
					InputStream in = new FileInputStream(file); //Cria um stream para manusear os dados do arquivo
					InputStreamReader isr = new InputStreamReader(in, "UTF-8"); //Especifica o formato em UTF-8
					br = new BufferedReader(isr); // Gera o arquivo em Buffered
					
					//Inicia a leitura do aquivo
					String s = br.readLine(); // Lendo a primeira linha e salva em string...
					
					int qtd = 0;
					while (s != null) {		
						qtd++;						
						if(qtd!=1){
							String[] dadosDaLinha = s.split(SEPARATOR); // Separa cada dado da linha em um vetor	
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
