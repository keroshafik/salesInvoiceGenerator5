
package mainPackage.model;

import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
        public class invoiceHeader {
                private int num;
                private String customer;
                private Date date;
                private ArrayList<invoiceLine>lines;
                private DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

public  invoiceHeader() {

}

    public invoiceHeader(int num, String customer, Date date) {
        this.num = num;
        this.customer = customer;
        this.date = date;
        }
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
  public ArrayList<invoiceLine> getLines() {
        if (lines == null) {
            lines = new ArrayList<>();
        }
        return lines;
    }

    public void setLines(ArrayList<invoiceLine> lines) {
        this.lines = lines;
    }
    
    public double getItemTotal(){
        double total = 0.0;
        
        for (int i = 0; i < getLines().size(); i++) {
            total += getLines().get(i).getLineTotal();
        }
        
        return total;
    }
    @Override
    public String toString() {
        return  + num + ","+ df.format(date) + ", "+ customer ;
    }
        }

  


    
