package es.ericguti.cursojedi.jedi15;

import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

public class DialogoConfirmacion extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String user, pass;
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        final Bundle args = getArguments();
        user = args.getString("user");
        pass = args.getString("pass");
        builder.setMessage(getString(R.string.registro) + " " + user +"?")
                .setTitle(R.string.noExiste)
                .setPositiveButton(R.string.Aceptar, new DialogInterface.OnClickListener()  {
                    public void onClick(DialogInterface dialog, int id) {
                        MyBD bdUsers = new MyBD(getActivity().getApplicationContext());
                        SQLiteDatabase db = bdUsers.getWritableDatabase();
                        if(db != null) {
                            db.execSQL("INSERT INTO usuaris VALUES ('"+user+"','"+pass+"','','')");
                        }
                        db.close();
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(), R.string.newuser, Toast.LENGTH_LONG);
                        toast.show();
                        ((MainActivity) getActivity()).saveUser(user);
                        getActivity().finish();
                        dialog.cancel();
                        Intent intent = new Intent(getActivity().getApplicationContext(), MenuActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);

                    }
                })
                .setNegativeButton(R.string.Cancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }
}
