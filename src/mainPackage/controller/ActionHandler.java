package mainPackage.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import mainPackage.view.invoiceFrame;
import javax.swing.JOptionPane;
import java.util.List;
import java.util.Date;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import mainPackage.view.InvoiceHeaderDialog;
import mainPackage.view.InvoiceLineDialog;
import mainPackage.model.invoiceHeader;
import mainPackage.model.InvoiceHeaderTable;
import mainPackage.model.invoiceLine;
import mainPackage.model.InvoiceLineTable;





 public class ActionHandler implements ActionListener{
         private invoiceFrame frame;
         private InvoiceHeaderDialog headerDialog;
         private InvoiceLineDialog lineDialog;

    public ActionHandler(invoiceFrame frame) {
         
        this.frame = frame;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
      
        switch(e.getActionCommand()){
            
            case "Add Item" :

                AddItem();
            break;
    
          case "Delete Item" :

              DeleteItem();
              break;
    
              case "Save Changes" : 
         
                 SaveChanges();
              break;
    
                 case "Cancel" : 
           
                    cancel();
                  break;
             case "newInvoiceSave":
                newInvoiceDialogOK();
                break;

            case "newInvoiceCancel":
                newInvoiceDialogCancel();
                break;

            case "newLineCancel":
                newLineDialogCancel();
                break;

            case "newLineSave":
                newLineDialogOK();
                break;
    
              case "load file" : 
          
                     loadfile();
                  break;
    
                 case "save file" : 
                  System.out.println("save file");
                     savefile();
                    break;

       
}
    }
 
 
    
      private void loadfile() {
        JFileChooser fileChooser = new JFileChooser();
        try {
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fileChooser.getSelectedFile();
                Path headerPath = Paths.get(headerFile.getAbsolutePath());
                List<String> headerLines = Files.readAllLines(headerPath);
                ArrayList<invoiceHeader> invoiceHeaders = new ArrayList<>();
                for (String headerLine : headerLines) {
                    String[] arr = headerLine.split(",");
                    String str1 = arr[0];
                    String str2 = arr[1];
                    String str3 = arr[2];
                    int code = Integer.parseInt(str1);
                    Date invoiceDate = invoiceFrame.dateFormat.parse(str2);
                    invoiceHeader header = new invoiceHeader(code, str3, invoiceDate);
                    invoiceHeaders.add(header);
                }
                frame.setInvoicesArray(invoiceHeaders);

                result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fileChooser.getSelectedFile();
                    Path linePath = Paths.get(lineFile.getAbsolutePath());
                    List<String> lineLines = Files.readAllLines(linePath);
                    ArrayList<invoiceLine> invoiceLines = new ArrayList<>();
                    for (String lineLine : lineLines) {
                        String[] arr = lineLine.split(",");
                        String str1 = arr[0];    // invoice num (int)
                        String str2 = arr[1];    // item name   (String)
                        String str3 = arr[2];    // price       (double)
                        String str4 = arr[3];    // count       (int)
                        int invCode = Integer.parseInt(str1);
                        double price = Double.parseDouble(str3);
                        int count = Integer.parseInt(str4);
                        invoiceHeader inv = frame.getInvObject(invCode);
                        invoiceLine line = new invoiceLine(str2, price, count, inv);
                        inv.getLines().add(line);
                    }
                }
                InvoiceHeaderTable headerTable = new InvoiceHeaderTable(invoiceHeaders);
                frame.setInvoiceheaderTable(headerTable);
                frame.getheaderTable().setModel(headerTable);
                System.out.println("files read");
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void AddItem() {
        headerDialog = new InvoiceHeaderDialog(frame);
        headerDialog.setVisible(true);
    }

    private void DeleteItem() {
        int selectedInvoiceIndex = frame.getheaderTable().getSelectedRow();
        if (selectedInvoiceIndex != -1) {
            frame.getInvoicesArray().remove(selectedInvoiceIndex);
            frame.getInvoiceheaderTable().fireTableDataChanged();

            frame.getlineTable().setModel(new InvoiceLineTable(null));
            frame.setLinesArray(null);
            frame.getCustNameLbl().setText("");
            frame.getInvNumLbl().setText("");
            frame.getInvTotalIbl().setText("");
            frame.getDateLbl().setText("");
        }
    }

    private void SaveChanges() {
        lineDialog = new InvoiceLineDialog(frame);
        lineDialog.setVisible(true);
    }

    private void cancel() {
        int selectedLineIndex = frame.getlineTable().getSelectedRow();
        int selectedInvoiceIndex = frame.getheaderTable().getSelectedRow();
        if (selectedLineIndex != -1) {
            frame.getLinesArray().remove(selectedLineIndex);
            InvoiceLineTable lineTableModel = (InvoiceLineTable) frame.getlineTable().getModel();
            lineTableModel.fireTableDataChanged();
            frame.getInvTotalIbl().setText("" + frame.getInvoicesArray().get(selectedInvoiceIndex).getItemTotal());
            frame.getInvoiceheaderTable().fireTableDataChanged();
            frame.getheaderTable().setRowSelectionInterval(selectedInvoiceIndex, selectedInvoiceIndex);
        }
    }

    private void savefile() {
        ArrayList<invoiceHeader> invoicesArray = frame.getInvoicesArray();
        JFileChooser fc = new JFileChooser();
        try {
            int result = fc.showSaveDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                FileWriter hfw = new FileWriter(headerFile);
                String headers = "";
                String lines = "";
                for (invoiceHeader invoice : invoicesArray) {
                    headers += invoice.toString();
                    headers += "\n";
                    for (invoiceLine line : invoice.getLines()) {
                        lines += line.toString();
                        lines += "\n";
                    }
                }
                
                headers = headers.substring(0, headers.length()-1);
                lines = lines.substring(0, lines.length()-1);
                result = fc.showSaveDialog(frame);
                File lineFile = fc.getSelectedFile();
                FileWriter lfw = new FileWriter(lineFile);
                hfw.write(headers);
                lfw.write(lines);
                hfw.close();
                lfw.close();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void  newLineDialogCancel() {
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }
  

    private void newInvoiceDialogOK() {
        headerDialog.setVisible(false);

        String custName = headerDialog.getCustNameField().getText();
        String str = headerDialog.getDateField().getText();
        Date d = new Date();
        try {
             d = invoiceFrame.dateFormat.parse(str);
        } 
        catch (ParseException ex) {
            JOptionPane.showMessageDialog(frame, "Cannot parse date, resetting to today.", "Invalid date format", JOptionPane.ERROR_MESSAGE);
        }

        int NUM = 0;
        for (invoiceHeader inv : frame.getInvoicesArray()) {
            if (inv.getNum() > NUM) {
                NUM = inv.getNum();
            }
        }
        NUM++;
        invoiceHeader newInv = new invoiceHeader(NUM, custName, d);
        frame.getInvoicesArray().add(newInv);
        frame.getInvoiceheaderTable().fireTableDataChanged();
        headerDialog.dispose();
        headerDialog = null;
    }

    private void newInvoiceDialogCancel() {
        headerDialog.setVisible(false);
        headerDialog.dispose();
        headerDialog = null;
    }

    private void newLineDialogOK() {
        lineDialog.setVisible(false);

        String name = lineDialog.getItemNameField().getText();
        String str1 = lineDialog.getItemCountField().getText();
        String str2 = lineDialog.getItemPriceField().getText();
        int count = 1;
        double price = 1;
        try {
            count = Integer.parseInt(str1);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Cannot convert number", "Invalid number format", JOptionPane.ERROR_MESSAGE);
        }

        try {
            price = Double.parseDouble(str2);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Cannot convert price", "Invalid number format", JOptionPane.ERROR_MESSAGE);
        }
        int selectedInvHeader = frame.getheaderTable().getSelectedRow();
                 if (selectedInvHeader != -1) {
            invoiceHeader invHeader = frame.getInvoicesArray().get(selectedInvHeader);
            invoiceLine line = new invoiceLine(name, price, count, invHeader);
            //invHeader.getLines().add(line);
            frame.getLinesArray().add(line);
            InvoiceLineTable lineTable = (InvoiceLineTable) frame.getlineTable().getModel();
            lineTable.fireTableDataChanged();
            frame.getInvoiceheaderTable().fireTableDataChanged();
        }
        frame.getheaderTable().setRowSelectionInterval(selectedInvHeader, selectedInvHeader);
        lineDialog.dispose();
        lineDialog = null;
    }

    

  

}