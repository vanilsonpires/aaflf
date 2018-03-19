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

	private List<String> list;

	/**
	 * @author Vanilson Pires 18 de mar de 2018 2018-03-18
	 *
	 */
	public TableModel(List<String> list) {
		
		if(list==null)
			list = new ArrayList<>();
		
		this.list = list;
	}
	
	public String getColumnName(int col) {
        return "";
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return 1;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void setValueAt(String value, int row, int col) {
		list.set(row, value);
	}
	
	public void add(String value){
		list.add(value);
		fireTableRowsInserted(list.size(), list.size());
	}
	
	public void addAll(List<String> value){
		list.addAll(value);
		fireTableRowsInserted(list.size(), list.size());
	}
	
	public void update(){
		fireTableStructureChanged();
	}
	
	public List<String> getDados(){
		return list;
	}
	
	public void reset(){
		this.list = new ArrayList<>();
	}

}
