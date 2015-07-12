package es.ericguti.cursojedi.jedi15;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by ericguti on 12/07/2015.
 */
public class ConfirmacionRanking extends android.support.v4.app.DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.BorrarRanking)
                .setTitle(R.string.confirm)
                .setPositiveButton(R.string.Si, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((borrarRankingI) getActivity()).borrarRanking();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }

    public interface borrarRankingI {
        void borrarRanking();
    }
}



