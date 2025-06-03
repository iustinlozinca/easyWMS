package com.example.orders.input;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TxtReader {

    public static List<List<String>> read(Uri uri, Context context) {
        List<List<String>> result = new ArrayList<>();

        try (InputStream is = context.getContentResolver().openInputStream(uri); /// incarcam in memorie fisier txt
             BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim(); //scot spatiile de la inceput si sfarsit
                if (line.isEmpty()) continue; /// sar liniile goale

                String[] tokens = line.split(",", -1);
                List<String> row = new ArrayList<>(tokens.length);
                for (String token : tokens) {
                    row.add(token.trim());
                }
                result.add(row);
            }

        } catch (Exception e) {
            Log.e("TxtReader", "Eroare la citire text", e);
        }

        return result;
    }
}
