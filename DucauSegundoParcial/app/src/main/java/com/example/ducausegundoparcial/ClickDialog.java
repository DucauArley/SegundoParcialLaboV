package com.example.ducausegundoparcial;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ClickDialog implements DialogInterface.OnClickListener
{
    public View v;
    public MainActivity mainActivity;
    public boolean funciona;

    public ClickDialog(View v, MainActivity ma, boolean funciona)
    {
        this.v = v;
        this.mainActivity = ma;
        this.funciona = funciona;
    }

    @Override
    public void onClick(DialogInterface dialog, int which)
    {
        switch(which)
        {
            case -1:
                if(funciona)
                {
                    EditText etNombre = v.findViewById(R.id.etNombre);
                    EditText etTelefono = v.findViewById(R.id.etTelefono);
                    SharedPreferences preferences = this.mainActivity.getSharedPreferences("misContactos", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    if(verificar(etNombre.getText().toString()) && verificar(etTelefono.getText().toString()))
                    {
                        JSONObject json = new JSONObject();
                        try
                        {
                            String nombre = etNombre.getText().toString().toLowerCase();
                            nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1).toLowerCase();
                            json.put("Nombre", nombre);
                            json.put("Telefono", etTelefono.getText().toString());
                            Log.d("JSON", json.toString());
                            this.mainActivity.jsonArray.put(json);
                            Log.d("Array", this.mainActivity.jsonArray.toString());
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                        editor.putString("Contactos", this.mainActivity.jsonArray.toString());
                        //editor.remove("Contactos");
                        editor.commit();
                        TextView txtCont = this.mainActivity.findViewById(R.id.Contactos);
                        txtCont.setText(this.mainActivity.jsonArray.toString());

                        Toast.makeText(mainActivity.getApplicationContext(),
                                "Contacto guardado exitosamente",
                                Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(mainActivity.getApplicationContext(),
                                "Datos incompletos",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case -2:
                Log.d("Click", "clickeo el boton negativo");
                break;
            case -3:
                Log.d("Click", "clickeo el boton neutral");
                break;
        }

    }

    private boolean verificar(String elemento)
    {
        boolean retorno = true;

        if(elemento.equals("") || elemento == null)
        {
            retorno = false;
        }

        return retorno;
    }

}
