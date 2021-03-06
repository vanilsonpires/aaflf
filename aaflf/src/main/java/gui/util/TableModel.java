/**
 * @author Vanilson Pires
 * 18 de mar de 2018 2018-03-18
 *
 */
package gui.util;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

/**
 * @author Vanilson Pires 18 de mar de 2018 2018-03-18
 *
 */
@SuppressWarnings("serial")
public class TableModel extends DefaultTableModel {

	private List<Comparable> list;
	
	private String[] colunas = {"Valor"};

	/**
	 * @author Vanilson Pires 18 de mar de 2018 2018-03-18
	 *
	 */
	public TableModel(List<Comparable> list) {
		
		if(list==null)
			list = new ArrayList<>();
		
		this.list = list;
	}
	
	public String getColumnName(int col) {
        return colunas[col];
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return colunas.length;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return list!=null ? list.size() : 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int row, int col) {
		return list.get(row);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int col) {
		switch (col) {
		case 0:
			return String.class;
		default:
			throw new IndexOutOfBoundsException("col out of bounds");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void setValueAt(Comparable value, int row, int col) {
		list.set(row, value);
	}
	
	public void add(String value){
		list.add(value);
		fireTableRowsInserted(list.size(), list.size());
	}
	
	public void addAll(List<Comparable> value){
		list.addAll(value);
		fireTableRowsInserted(list.size(), list.size());
	}
	
	public void update(){
		fireTableStructureChanged();
	}
	
	public List<Comparable> getDados(){
		return list;
	}
	
	public void reset(){
		this.list = new ArrayList<>();
	}

}
