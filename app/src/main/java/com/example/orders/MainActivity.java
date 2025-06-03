package com.example.orders;
import com.example.orders.help.FilePickerHelper;
import com.example.orders.model.OrderMapper;

import android.os.Bundle;
import android.net.Uri;

import java.util.Collections;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.orders.model.ProdusComanda;

import java.util.List;
import java.util.concurrent.Executors;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Uri uriTxt, uriXlsx;
    private FilePickerHelper txtPicker, xlsxPicker;

    /// cod de dinainte facut standard de IDE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    /////////final cod standard IDE


        /// listener pentru buton de incarcare stoc
        txtPicker  = new FilePickerHelper(this, uri -> { uriTxt  = uri; maybeBuildOrder(); },
                "text/plain");

        xlsxPicker = new FilePickerHelper(this, uri -> { uriXlsx = uri; maybeBuildOrder(); },
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        findViewById(R.id.pickTxtBtn).setOnClickListener(v -> txtPicker.launch());
        findViewById(R.id.pickXlsxBtn).setOnClickListener(v -> xlsxPicker.launch());
    }


    private void maybeBuildOrder() {
        if (uriTxt == null || uriXlsx == null) return; // așteptăm ambele fișiere

        Executors.newSingleThreadExecutor().execute(() -> {
            List<ProdusComanda> lista =
                    OrderMapper.build(uriTxt, uriXlsx, getApplicationContext());
            afiseazaLogListaProdusComanda(TAG, lista);
        });
    }



    /// afisari in Log
    private void afiseazaLogString(String TAG,String str){
        Log.d(TAG, str);
    }

    private void afiseazaLogListString(String TAG, List<String> listString){
        Log.d(TAG,""+ listString);
    }

    private void afiseazaLogListListString(String TAG, List<List<String>> strings){
        Log.d(TAG,"Randuri citite: " + strings.size());
        for(int i = 0; i< strings.size(); i++){
        Log.d(TAG,""+i+"->"+strings.get(i));
    }}

    private void afiseazaLogListaProdusComanda(String TAG, List<ProdusComanda> lista){
        for(int i=0; i <=4;i++){
            Log.d(TAG,"Afisare nesortata:");
        }
        Log.d(TAG, "Nr. produse: "+lista.size());
        for(int i = 0; i <lista.size();i++){
            Log.d(TAG,i + "-> "+lista.get(i));
        }
        for(int i=0; i <=4;i++){
            Log.d(TAG,"Afisare sortata:");
        }

        Collections.sort(lista);

        Log.d(TAG, "Nr. produse: "+lista.size());
        for(int i = 0; i <lista.size();i++){
            Log.d(TAG,i + "-> "+lista.get(i));
        }
    }

    /// ///final afisari in log



}