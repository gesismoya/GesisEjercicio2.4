package com.example.ejercicio2_4.configuraciones;

public class Transacciones {
    public static final String NameDatabase = "Firmas";
    public static final String tablasignatures = "Firmas";
    public static final String id = "id";
    public static final String descripcion = "descripcion";
    public static final String imagen = "imagen";
    public static final String CreateTableSignatures = "CREATE TABLE "+tablasignatures + "(id INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "descripcion TEXT, imagen BLOB)";
    public static final String DropTableSignatures = "DROP TABLE IF EXISTS "+ tablasignatures;
}
