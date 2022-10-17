package mainPackage.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class InvoiceHeaderDialog extends JDialog {
    private final JTextField custNameField;
    private final JTextField DateField;
    private final JLabel custNameLbl;
    private final JLabel DateLbl;
    private final JButton SaveChangesBtn;
    private final JButton CancelBtn;

    public InvoiceHeaderDialog(invoiceFrame frame) {
        custNameLbl = new JLabel("Customer Name:");
        custNameField = new JTextField(20);
        DateLbl = new JLabel(" Date:");
        DateField = new JTextField(20);
        SaveChangesBtn = new JButton("Add Item");
        CancelBtn = new JButton("Delete Item");
        
        SaveChangesBtn.setActionCommand("newInvoiceSave");
        CancelBtn.setActionCommand("newInvoiceCancel");
        
        SaveChangesBtn.addActionListener(frame.getActionHandler());
        CancelBtn.addActionListener(frame.getActionHandler());
        setLayout(new GridLayout(3, 2));
        
        add(DateLbl);
        add(DateField);
        add(custNameLbl);
        add(custNameField);
        add(SaveChangesBtn);
        add(CancelBtn);
        
        pack();
        
    }

    public JTextField getCustNameField() {
        return custNameField;
    }

    public JTextField getDateField() {
        return DateField;
    }
    
}
