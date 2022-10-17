
package mainPackage.view;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

    public class InvoiceLineDialog extends JDialog{
    private final JTextField itemNameField;
    private final JTextField itemCountField;
    private final JTextField itemPriceField;
    private final JLabel itemNameLbl;
    private final JLabel itemCountLbl;
    private final JLabel itemPriceLbl;
    private JButton SaveChangesbutt;
    private JButton Cancelbut;
    
    public InvoiceLineDialog(invoiceFrame frame) {
        itemNameField = new JTextField(20);
        itemNameLbl = new JLabel("Item Name");
        
        itemCountField = new JTextField(20);
        itemCountLbl = new JLabel("Item Count");
        
        itemPriceField = new JTextField(20);
        itemPriceLbl = new JLabel("Item Price");
        
        SaveChangesbutt = new JButton("Save");
         Cancelbut = new JButton("Cancel");
        
        SaveChangesbutt.setActionCommand("newLineSave");
        Cancelbut.setActionCommand("newLineCancel");
        
        SaveChangesbutt.addActionListener(frame.getActionHandler());
        Cancelbut.addActionListener(frame.getActionHandler());
        setLayout(new GridLayout(4, 2));
        
        add(itemNameLbl);
        add(itemNameField);
        add(itemCountLbl);
        add(itemCountField);
        add(itemPriceLbl);
        add(itemPriceField);
        add(SaveChangesbutt);
        add(Cancelbut);
        
        pack();
    }

    public JTextField getItemNameField() {
        return itemNameField;
    }

    public JTextField getItemCountField() {
        return itemCountField;
    }

    public JTextField getItemPriceField() {
        return itemPriceField;
    }
}

    

