package es.ericguti.cursojedi.jedi15;

import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;


/**
 * Created by ericguti on 13/07/2015.
*/
public class PhotoDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        builder.setMessage("¿Confirma la acción seleccionada?")
                .setTitle("Confirmacion")
                .setPositiveButton("Galeria", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((imageI) getActivity()).intentImage(0);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Camara", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((imageI) getActivity()).intentImage(1);
                        dialog.cancel();
                    }
                });

        return builder.create();
    }

    public interface imageI {
        void intentImage(int i);
    }
}

