package com.example.ducausegundoparcial;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;

public class MiDialogo extends DialogFragment
{
    public MainActivity mainActivity;
    public int tipoDialog;
    public String telefono;

    public MiDialogo(MainActivity ma, int tipoDialog, String telefono)
    {
        this.mainActivity = ma;
        this.tipoDialog = tipoDialog;
        this.telefono = telefono;
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(super.getActivity());
        ClickDialog clickdg = null;

        if(this.tipoDialog == 1)
        {
            LayoutInflater li = LayoutInflater.from(getActivity());
            View view = li.inflate(R.layout.dialog, null);
            builder.setView(view);
            clickdg = new ClickDialog(view, this.mainActivity, true);
        }
        else
        {
            clickdg = new ClickDialog(null, null, false);
        }


        switch (this.tipoDialog)
        {
            case 1:
                builder.setTitle("Nuevo Contacto");
                //builder.setMessage("Mensaje nuevo para el usuario");

                builder.setPositiveButton("Guardar", clickdg);
                builder.setNeutralButton("Cancelar", clickdg);
                break;
            case 2:
                builder.setTitle("Persona Encontrada");
                builder.setMessage("El telefono es: " + this.telefono);
                builder.setPositiveButton("OK", clickdg);
                break;
            case 3:
                builder.setTitle("No Encontrada");
                builder.setMessage("La persona que busco no esta dentro de la lista");
                builder.setPositiveButton("OK", clickdg);
                break;
        }

        return builder.create();
    }

}
