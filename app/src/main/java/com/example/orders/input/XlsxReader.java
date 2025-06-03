package com.example.orders.input;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class XlsxReader {
    public static List<List<String>> read(Uri uri, Context context){
        DataFormatter fmt = new DataFormatter();
        List<List<String>> result = new ArrayList<>();
        try (InputStream is = context.getContentResolver().openInputStream(uri);
            Workbook wb = new XSSFWorkbook(is)) {

        Sheet sheet = wb.getSheetAt(0);
        for(Row row : sheet){
            List<String> rowData = new ArrayList<>();
            for(Cell cell : row){
                switch (cell.getCellType()){

                    case STRING:
                        rowData.add(cell.getStringCellValue());
                        break;

                    case NUMERIC:
                        rowData.add(fmt.formatCellValue(cell));
                        break;

                    case BOOLEAN:
                        rowData.add(String.valueOf(cell.getBooleanCellValue()));
                        break;

                    case BLANK:
                        rowData.add("@BLANK");
                        break;

                    case ERROR:
                        rowData.add("#ERROR");
                        break;


                    case FORMULA:
                        switch (cell.getCachedFormulaResultType()){
                            case STRING:
                                rowData.add(cell.getStringCellValue());
                                break;

                            case NUMERIC:
                                rowData.add(fmt.formatCellValue(cell));
                                break;

                            case BOOLEAN:
                                rowData.add(String.valueOf(cell.getBooleanCellValue()));
                                break;

                            case BLANK:
                                rowData.add("@BLANK");
                                break;

                            case ERROR:
                                rowData.add("#ERROR");
                                break;

                            default:
                                Log.w("XlsxReader", "Default case in FORMULA" + cell.getCellType());
                                rowData.add("");
                                break;
                        }
                        break;


                    default:
                        Log.w("XlsxReader", "Default case la citire celula" + cell.getCellType());
                        rowData.add("");
                        break;
                }

            }
            result.add(rowData);
        }


        } catch (Exception e){
            Log.e("XlsxReader","Problema la citire .xlsx", e);
        }

        return result;
    }
}
