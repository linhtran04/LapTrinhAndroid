package com.example.loginnote.ui.status;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.loginnote.R;
import com.example.loginnote.ui.priority.Priority_dialog;
import com.example.loginnote.ui.priority.priority_DB;

import java.util.Date;

public class Status_dialog extends DialogFragment {
    public  interface  dialog_Add_Status_Listener{
        void getData();
    }
    public   dialog_Add_Status_Listener dialogAddStatusListener ;
    String name  = "-1";
    int keyId  ;
    public Status_dialog(String name,int keyId) {
        this.name= name;
        this.keyId = keyId;
    }
    public Status_dialog() {
    }

    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState) {
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.status_dialog_fragment,null);

        if(name.equals("-1")){  // when click add
            builder.setView(view)
                    .setTitle("Status From").setNegativeButton("close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).setPositiveButton("add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditText txt =  (EditText)view.findViewById(R.id.input_Status);
                    if(txt.getText().toString().equals(""))
                    {
                        Toast.makeText(Status_dialog.this.getContext(),"error name status null",Toast.LENGTH_SHORT).show();
                    }else {
                        try {
                            String date = java.text.DateFormat.getDateTimeInstance().format(new Date());
                            Status_OJ status_oj = new Status_OJ(-1,txt.getText().toString(),date);


                            String a = txt.getText().toString();


                            Status_DB status_db = new Status_DB(Status_dialog.this.getContext());
                            status_db.insetStatus(status_oj);
                        }catch (ClassCastException e){
                            Toast.makeText(Status_dialog.this.getContext(),"error insert",Toast.LENGTH_SHORT).show();
                        }
                    }

                    dialogAddStatusListener.getData();
                }
            });
        }else { //when click edit
            EditText txt =  (EditText)view.findViewById(R.id.input_Status);
            txt.setText(name);
            builder.setView(view)
                    .setTitle("Status Edit").setNegativeButton("close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).setPositiveButton("save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Status_OJ status_oj = new Status_OJ();
                    status_oj.setName(txt.getText().toString());
                    status_oj.setCreatedate(java.text.DateFormat.getDateTimeInstance().format(new Date()));
                    status_oj.setId(keyId);
                    Status_DB status_db = new Status_DB(Status_dialog.this.getContext());
                    status_db.updateStatus(status_oj);
                    dialogAddStatusListener.getData();
                }
            });

        }

        return builder.create();
    }

    // send data to fragment
    @Override
    public void onAttach( Context context) {
        super.onAttach(context);
        dialogAddStatusListener = (dialog_Add_Status_Listener) getParentFragment();
    }



}
