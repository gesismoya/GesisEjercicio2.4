package com.example.ejercicio2_4;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ejercicio2_4.configuraciones.SQLiteConexion;
import com.example.ejercicio2_4.configuraciones.Transacciones;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity  {

    Lienzo lienzo;
    Button NuevaFirma, VerFirmas;
    Button Salvar;
    EditText Descripcion;
    SQLiteConexion conexion;
    Bitmap ima;
    boolean estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        estado=true;
        conexion = new SQLiteConexion(this, Transacciones.NameDatabase,null,1);
        NuevaFirma = (Button) findViewById(R.id.btnNuevaFirma);
        Salvar = (Button) findViewById(R.id.btnSalvar);
        VerFirmas = (Button)findViewById(R.id.btnVerFirmas);
        Descripcion = (EditText) findViewById(R.id.textDescripcion);
        lienzo = (Lienzo) findViewById(R.id.lienzo);


        NuevaFirma.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("¿Está seguro que desea crear una nueva firma?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i){
                                lienzo.nuevoDibujo();
                                Descripcion.setText("");
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i){
                            }
                        }).show();

            }
        });

        Salvar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                guardarFirmas();
            }
        });

        VerFirmas.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivitySeeSignaturess.class);
                startActivity(intent);
            }
        });

    }

    private void guardarFirmas(){
        if(lienzo.borrado){
            Toast.makeText(getApplicationContext(), "El lienzo no debe quedar vacio"
                    ,Toast.LENGTH_LONG).show();
            return;
        }else if(Descripcion.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), "Escriba una descripcion"
                    ,Toast.LENGTH_LONG).show();
            return;
        }

        SQLiteDatabase db = conexion.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Transacciones.descripcion,Descripcion.getText().toString());
        ByteArrayOutputStream bay = new ByteArrayOutputStream(10480);
        Bitmap bitmap = Bitmap.createBitmap(lienzo.getWidth(), lienzo.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        lienzo.draw(canvas);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 , bay);
        byte[] bl = bay.toByteArray();
        String img= Base64.encodeToString(bl,Base64.DEFAULT);
        values.put(Transacciones.imagen, img);
        Long result = db.insert(Transacciones.tablasignatures, Transacciones.id, values);
        Toast.makeText(getApplicationContext(), "Firma Guardada"
                ,Toast.LENGTH_LONG).show();
        lienzo.nuevoDibujo();
        Descripcion.setText("");
        db.close();
    }
}