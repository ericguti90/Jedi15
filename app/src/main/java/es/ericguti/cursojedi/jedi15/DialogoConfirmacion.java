package es.ericguti.cursojedi.jedi15;

import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

/**
 * Created by inlab on 06/07/2015.
 */
public class DialogoConfirmacion extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String user, pass;
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        final Bundle args = getArguments();
        user = args.getString("user");
        pass = args.getString("pass");
        builder.setMessage("¿Quieres registrarte " + user +"?")
                .setTitle("Usuario no existe")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener()  {
                    public void onClick(DialogInterface dialog, int id) {
                        //Log.i("Dialogos", "Confirmacion Aceptada.");
                        MyBD bdUsers = new MyBD(getActivity().getApplicationContext());
                        SQLiteDatabase db = bdUsers.getWritableDatabase();
                        if(db != null) {
                            db.execSQL("INSERT INTO usuaris VALUES ('"+user+"','"+pass+"','','')");
                        }
                        db.close();
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Usuario creado", Toast.LENGTH_LONG);
                        toast.show();
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Log.i("Dialogos", "Confirmacion Cancelada.");
                        dialog.cancel();
                    }
                });

        return builder.create();
    }
}
