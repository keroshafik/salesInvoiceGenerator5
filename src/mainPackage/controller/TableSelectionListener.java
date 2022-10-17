
package mainPackage.controller;
    
import mainPackage.model.invoiceHeader;
import mainPackage.model.invoiceLine;
import mainPackage.model.InvoiceLineTable;
import mainPackage.view.invoiceFrame;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TableSelectionListener implements ListSelectionListener {

    private  invoiceFrame frame;

    public TableSelectionListener(invoiceFrame frame) {
        this.frame = frame;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedInvIndex = frame.getheaderTable().getSelectedRow();
        System.out.println("Invoice selected: " + selectedInvIndex);
        if (selectedInvIndex != -1) {
            invoiceHeader selectedInv = frame.getInvoicesArray().get(selectedInvIndex);
            ArrayList<invoiceLine> lines = selectedInv.getLines();
            InvoiceLineTable lineTable = new InvoiceLineTable(lines);
            frame.setLinesArray(lines);
            frame.getlineTable().setModel(lineTable);
            frame.getCustNameLbl().setText(selectedInv.getCustomer());
            frame.getInvNumLbl().setText("" + selectedInv.getNum());
            frame.getInvTotalIbl().setText("" + selectedInv.getItemTotal());
            frame.getDateLbl().setText(invoiceFrame.dateFormat.format(selectedInv.getDate()));
        }
    }

}

    

