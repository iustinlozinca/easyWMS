package com.example.orders.model;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.orders.input.TxtReader;
import com.example.orders.input.XlsxReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderMapper {
    public static List<ProdusComanda> build(Uri txtUri, Uri xlsxUri, Context ctx) {
        boolean isFirstRow = true;

        /// /citesc fisierele
        List<List<String>> txtList = TxtReader.read(txtUri, ctx);
        List<List<String>> xlsList = XlsxReader.read(xlsxUri, ctx);

        /// mapa EAN -> produs din catalog
        Map<String, Produs> catalogEan = new HashMap<>();
        for (List<String> row : xlsList) {

            if (isFirstRow){
                isFirstRow = false;
                continue;
            }

            if (row.size() < 4)
                continue; ///sar randurile invalide (probabil va trebui sa tratez mai tarziu situatia)
            String codIntern = row.get(0);
            String ean = row.get(1);
            String nume = row.get(2);
            Integer unitate = 0;
            Integer stoc = 0;

            catalogEan.put(ean, new Produs(ean, nume, codIntern, unitate, stoc));
        }

        List<ProdusComanda> comanda = new ArrayList<>();

        for (List<String> row : txtList) {
            if (row.size() < 3) continue; ///sar iar liniile incorecte
            String eanComanda = row.get(0);



            int cantCmd;
            try {
                cantCmd = Integer.parseInt(row.get(2));
            } catch (NumberFormatException nfe) {
                Log.w("OrderMapper", "Cantitate ne-numerică la EAN " + eanComanda +
                        " → '" + row.get(2) + "'. Sar linia.");
                continue;
            }

            Produs p =catalogEan.get(eanComanda);
            if (p == null){
                Log.w("OrderMapper","ean necunoscut in comanda: " + eanComanda);
                continue;
            }

            ProdusComanda pc = new ProdusComanda(
                    p.getEan(),
                    p.getNume(),
                    p.getCodIntern(),
                    p.getUnitate(),
                    p.getStoc(),
                    cantCmd
            );
            comanda.add(pc);
        }


        return comanda;
    }

}
