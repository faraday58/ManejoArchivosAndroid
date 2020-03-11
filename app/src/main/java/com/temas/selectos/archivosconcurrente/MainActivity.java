package com.temas.selectos.archivosconcurrente;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
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




    /*Ejemplo de guardar con hilos en una secuencia que no es visible para el usuario*/
    //Método onClick para demostrar ejecución en paralelo
    public void onClick(View v){
        Toast.makeText(this,"Hilos",Toast.LENGTH_SHORT).show();
        GuardaHilo();
        LeerHilor();

    }

    public void Guarda()
    {
        String texto= "Un texto \n cualquiera\n Mi texto";
        FileOutputStream fos= null;

        try {
            fos= openFileOutput(nombre,MODE_PRIVATE);
            Thread.sleep(2000);
            fos.write(texto.getBytes());
            Log.d("Archivo","Se escribieron los archivos");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
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

    public void Leer()
    {
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
            Log.d("Archivo",""+texto);
            Log.d("Archivo","Lectura Terminada");

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



    public void GuardaHilo()  {
        //try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Guarda();

                }
            }).start();
      /*  } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    public void LeerHilor()
    {
       // try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Leer();
                }
            }).start();
     /*   } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

}
