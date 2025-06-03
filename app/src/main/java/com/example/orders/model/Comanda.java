package com.example.orders.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Comanda {
    private List<ProdusComanda> produse;

    public Comanda(List<ProdusComanda> produse){
        this.produse = produse;
        Collections.sort(this.produse);
    }


    public void sorteaza(){
        Collections.sort(produse);
    }

    public int gasesteIndexEan(String EAN){
        ProdusComanda key = new ProdusComanda(EAN,"","",0,0,0);
        return Collections.binarySearch(produse,key);

    }

    public void eliminaProdusIndex(int index){
        if (index >= 0 && index < produse.size()) {
            produse.remove(index);
        }
    }

    public void eliminaProdusEan(String EAN){
        eliminaProdusIndex(gasesteIndexEan(EAN));
    }



    public void adaugaProdus(String EAN,int cantitate){
        /// /momentan aceasta metoda este aici doar ca sa nu uit de ea
        /// dar ideea ar fii sa poti scana un produs, sa introduci cantitatea
        /// si sa preia restul informatiilor din excell
        /// si dupa sa sorteze si asa sa adauge la comanda
    }



    public void modificaCantitateScanata(String EAN, int cantitate){
        int index = gasesteIndexEan(EAN);
        if(index <0) {
            Log.w("Comanda","Nu s-a gasit indexul cautat dupa EAN" + EAN);
            return;
        }
        ProdusComanda produs = produse.get(index);
        produs.setCantitateScanata(cantitate);
    }


    public List<ProdusComanda> getProduse(){
        return produse;
    }



}
