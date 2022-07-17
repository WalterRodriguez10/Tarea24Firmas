package com.example.ejercicio2_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ejercicio2_4.Models.SQLiteConexion;
import com.example.ejercicio2_4.Models.Signaturess;
import com.example.ejercicio2_4.Models.Transacciones;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class ActivityListarFirma extends AppCompatActivity {

    SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
    String sql = "SELECT * FROM Signaturess";


    Button btnAtras;
    ArrayList<Signaturess> listaFirmas = new ArrayList<Signaturess>();
    ImageView imageView;
    TextView txtDesFirma;
    ListView list;
    Bitmap bitmap = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_firma);

        SQLiteDatabase db = conexion.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[] {});

        while (cursor.moveToNext()){
            listaFirmas.add(new Signaturess());
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        db.close();
        AdaptadorFirmas adaptador = new AdaptadorFirmas(this);
        list = findViewById(R.id.lista);
        list.setAdapter(adaptador);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                obtenerFoto(i);
            }
        });

        btnAtras = (Button) findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ActivityFirma.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    private void obtenerFoto( int id) {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Signaturess lista_Firmas = null;
        listaFirmas = new ArrayList<Signaturess>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Transacciones.TablaSignaturess,null);

        while (cursor.moveToNext())
        {
            lista_Firmas = new Signaturess();
            lista_Firmas.setDescripcion(cursor.getString(0));
            listaFirmas.add(lista_Firmas);
        }
        cursor.close();
        Signaturess signaturess = listaFirmas.get(id);
    }


    class AdaptadorFirmas extends ArrayAdapter<Signaturess> {

        AppCompatActivity appCompatActivity;

        AdaptadorFirmas(AppCompatActivity context) {
            super(context, R.layout.firma, listaFirmas);
            appCompatActivity = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = appCompatActivity.getLayoutInflater();
            View item = inflater.inflate(R.layout.firma, null);
            SQLiteDatabase db = conexion.getWritableDatabase();

            imageView = item.findViewById(R.id.sImgFirma);
            txtDesFirma = item.findViewById(R.id.sDescripcionFirma);

        //    String sql = "SELECT * FROM Signaturess";

            Cursor cursor = db.rawQuery(sql, new String[] {});
            if (cursor.moveToNext()){
                txtDesFirma.setText(listaFirmas.get(position).getDescripcion());
                byte[] blob = listaFirmas.get(position).getImage();
                ByteArrayInputStream bits = new ByteArrayInputStream(blob);
                bitmap = BitmapFactory.decodeStream(bits);
                imageView.setImageBitmap(bitmap);
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            db.close();

            return(item);
        }
    }
}