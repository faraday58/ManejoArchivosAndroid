package com.temas.selectos.archivosconcurrente;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    Button btnGuardar;
    Button btnLeer;
    EditText edtTexto;
    final static String nombre="ArchivoL.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGuardar= findViewById(R.id.btnGuardar);
        btnLeer= findViewById(R.id.btnLeer);
        edtTexto= findViewById(R.id.edtTexto);
        btnGuardar.setOnClickListener(onClickGuardar);
        btnLeer.setOnClickListener(onClickAbrir);

    }

    View.OnClickListener onClickGuardar= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String texto= edtTexto.getText().toString();
            FileOutputStream fos= null;

            try {
                fos= openFileOutput(nombre,MODE_PRIVATE);
                fos.write(texto.getBytes());
                edtTexto.getText().clear();
                Toast.makeText(getApplicationContext(),"Guardado en: "+getFilesDir() + "/"+nombre,Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(fos !=null)
                {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


        }
    };

    View.OnClickListener onClickAbrir= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FileInputStream fis= null;
            try {
                fis=openFileInput(nombre);
                InputStreamReader isr= new InputStreamReader(fis);
                BufferedReader br= new BufferedReader(isr);
                StringBuilder sb= new StringBuilder();
                String texto="";

                while ((texto=br.readLine())!=null)
                {
                    sb.append(texto).append("\n");
                }
                edtTexto.setText(sb.toString());
                Toast.makeText(getApplicationContext(),"Archivo Abierto",Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if(fis!=null){
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

        }
    };

}
