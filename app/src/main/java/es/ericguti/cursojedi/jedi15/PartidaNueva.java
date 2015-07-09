package es.ericguti.cursojedi.jedi15;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;


public class PartidaNueva extends DialogFragment {
    Activity activity;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle args = getArguments();
        String points = args.getString("points");
        builder.setMessage(getString(R.string.puntuacion) + " " + points + ".\n"+ getString(R.string.volverJugar))
                .setTitle(R.string.FinPartida)
                .setPositiveButton(R.string.Si, new DialogInterface.OnClickListener()  {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        getActivity().recreate();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getActivity().finish();
                        dialog.cancel();
                    }
                });

        return builder.create();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }
}
