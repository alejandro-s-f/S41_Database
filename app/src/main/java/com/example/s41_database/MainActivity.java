package com.example.s41_database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Declarar los cuadros ed texto a ocupar.
    private EditText etCod, etNom, etVal, etDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCod = (EditText) findViewById(R.id.etCodigo);
        etNom = (EditText) findViewById(R.id.etNombre);
        etVal = (EditText) findViewById(R.id.etValor);
        etDesc = (EditText) findViewById(R.id.etDescripcion);

    }

    public void registrar(View view){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administrador", null, 1);

        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();

        registro.put("codigo", etCod.getText().toString());//Obteniendo el valor del cuadro de texto y seleccionar la columna donde agregar.
        registro.put("nombre", etNom.getText().toString());
        registro.put("valor", etVal.getText().toString());
        registro.put("descripcion", etDesc.getText().toString());

        db.insert("productos", null, registro);
        limpiar();

        db.close();

        Toast.makeText(this,"Registro exitoso", Toast.LENGTH_SHORT).show();
    }


    public void buscar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administrador", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        Cursor fila = db.rawQuery("select * from productos where codigo="+etCod.getText().toString(),null);
        if(fila.moveToFirst()){
            Toast.makeText(this, "Producto encontrado", Toast.LENGTH_SHORT).show();
            etNom.setText(fila.getString(1));
            etVal.setText(fila.getString(2));
            etDesc.setText(fila.getString(3));

        }else{
            Toast.makeText(this, "Producto no encontrado", Toast.LENGTH_SHORT).show();
            limpiar();
        }


    }

    public void modificar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administrador", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues cambio = new ContentValues();

        cambio.put("codigo", etCod.getText().toString());
        cambio.put("nombre", etNom.getText().toString());
        cambio.put("valor", etVal.getText().toString());
        cambio.put("descripcion", etDesc.getText().toString());

        int mod = db.update("productos", cambio, "codigo="+etCod.getText().toString(), null);
        db.close();

        if(mod < 1){
            Toast.makeText(this, "No hay cambios", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Cambios realizados", Toast.LENGTH_SHORT).show();
        }


    }

    public void eliminar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administrador", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        int del = db.delete("productos","codigo="+etCod.getText().toString(), null);
        limpiar();
        if(del < 1){
            Toast.makeText(this, "No se eliminó nada", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Producto eliminado", Toast.LENGTH_SHORT).show();
        }
    }

    public void limpiar(){

        etCod.setText("");
        etNom.setText("");
        etVal.setText("");
        etDesc.setText("");

    }



}