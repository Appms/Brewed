package com.example.ams.brewed;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by AMS on 04/06/2015.
 */

public class MessageFragment extends DialogFragment {

    public MessageFragment(){}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_DARK);

        LayoutInflater inflater = this.getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.criteria_dialog, null);
        builder.setView(view);

        final EditText criteriaField = (EditText) view.findViewById(R.id.criteriaField);

        builder.setTitle("Enter Search Criteria: ").setPositiveButton("Confirm", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (Viewmodel.getInstance().currentSearchType){
                    case BEER:
                        Viewmodel.getInstance().onBeerSearchRequested(criteriaField.getText().toString());
                        break;
                    case BREWERY:
                        Viewmodel.getInstance().onBrewerySearchRequested(criteriaField.getText().toString());
                }
            }
        });

        return builder.create();
    }
}
