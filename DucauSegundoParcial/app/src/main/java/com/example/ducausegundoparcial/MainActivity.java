package com.example.ducausegundoparcial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements Handler.Callback, SearchView.OnQueryTextListener {

    public static JSONArray jsonArray = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("misContactos", Context.MODE_PRIVATE);
        TextView txtCont = this.findViewById(R.id.Contactos);
        txtCont.setText(prefs.getString("Contactos", "Contactos"));

        try
        {
            jsonArray = new JSONArray(prefs.getString("Contactos", "[]"));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if(item.getItemId() == R.id.Agregar)
        {
            MiDialogo miDialogo = new MiDialogo(this, 1, null);
            miDialogo.show(super.getSupportFragmentManager(), "Confirm");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem mi = menu.findItem(R.id.buscar);
        SearchView searchView = (SearchView) mi.getActionView();
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String s)
    {
        String nombre = "";
        String telefono = "";
        boolean noEsta = true;
        Log.d("Activity", "Hago una busqueda con:" + s);

        s = s.toLowerCase();
        s = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();

        for (int i=0; i < this.jsonArray.length(); i++)
        {
            try
            {
                nombre = this.jsonArray.getJSONObject(i).getString("Nombre");
                telefono = this.jsonArray.getJSONObject(i).getString("Telefono");
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            if(nombre.equals(s))
            {
                MiDialogo miDialogo = new MiDialogo(this, 2, telefono);
                miDialogo.show(super.getSupportFragmentManager(), "Confirm");
                noEsta = false;
            }
        }

        if(noEsta)
        {
            MiDialogo miDialogo = new MiDialogo(this, 3, null);
            miDialogo.show(super.getSupportFragmentManager(), "Confirm");
        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
