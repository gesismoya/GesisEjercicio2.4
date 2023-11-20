package com.example.ejercicio2_4;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ejercicio2_4.configuraciones.SQLiteConexion;
import com.example.ejercicio2_4.configuraciones.Transacciones;

import java.util.ArrayList;

public class ActivitySeeSignaturess extends AppCompatActivity {
    SQLiteConexion conexion;
    RecyclerView.Adapter adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Signaturess> signaturessList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_signaturess);
        conexion = new SQLiteConexion(this, Transacciones.NameDatabase,null,1);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        signaturessList = new ArrayList<>();
        getSignaturess();
        adapter = new Adapter(signaturessList);
        recyclerView.setAdapter(adapter);
    }

    private void getSignaturess(){
        SQLiteDatabase db = conexion.getReadableDatabase();
        Signaturess firmas = null;
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Transacciones.tablasignatures,null);
        while (cursor.moveToNext()){
            firmas = new Signaturess();
            firmas.setId(cursor.getInt(0));
            firmas.setDescripcion(cursor.getString(1));
            firmas.setImagen(cursor.getString(2));
            signaturessList.add(firmas);
        }
    }
}