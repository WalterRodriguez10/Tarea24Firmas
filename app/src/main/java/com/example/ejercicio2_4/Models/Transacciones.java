package com.example.ejercicio2_4.Models;

public class Transacciones
{

    public static final String NameDatabase="DBPM01";

    public static final String TablaSignaturess="Signaturess";

    public static final String id = "id";
    public static final String descripcion="descripcion";
    public static final String firma="firma";


    public static final String CreateTableSignaturess ="CREATE TABLE IF NOT EXISTS "+ TablaSignaturess + "(id INCREMENTE PRIMARY KEY, descripcion TEXT,"+"firma BLOB);";

    public static final String DROPTableSignaturess ="DROP TABLE IF EXISTS " + TablaSignaturess;

}