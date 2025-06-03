package com.example.orders.export;

import com.example.orders.model.Comanda;
import com.example.orders.model.ProdusComanda;

public class CsvExport {
    private CsvExport(){}

    public static String comandaToCsv(Comanda cmd){
        StringBuilder sb = new StringBuilder();
        /// EAN,Cod Intern, Nume, CantitateScanata

        for(ProdusComanda pc: cmd.getProduse()){
            /// /aici trebuie sa fac logica ca sa pun appenduri cu virgula in sb
        }

        return sb.toString();
    }

}
