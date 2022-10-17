
package mainPackage.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import mainPackage.view.invoiceFrame;


public class InvoiceHeaderTable extends AbstractTableModel {

    private ArrayList<invoiceHeader> invoicesArray;
    private String[] columns = {"Invoice Num", "Invoice Date", "Customer Name", "Invoice Total"};
    
    public InvoiceHeaderTable(ArrayList<invoiceHeader> invoicesArray) {
        this.invoicesArray = invoicesArray;
    }

    @Override
    public int getRowCount() {
        return invoicesArray.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        invoiceHeader inv = invoicesArray.get(rowIndex);
        switch (columnIndex) {
        case 0: 
            return
                    inv.getNum();
        case 1: 
            return 
                    invoiceFrame.dateFormat.format(inv.getDate());
        case 2: 
            return 
                    inv.getCustomer();
        case 3:
            return
                    inv.getItemTotal();
        }
        return "";
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
}
