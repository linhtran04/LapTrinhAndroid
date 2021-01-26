package com.example.loginnote.ui.priority;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.loginnote.DBHelper;
import com.example.loginnote.LoginActivity;
import com.example.loginnote.R;
import com.example.loginnote.ui.cetegory.Category_dialog;

import java.util.Date;

public class Priority_dialog extends DialogFragment {
    public interface dialog_Add_Priority_Listener {
        void getData();
    }
    public Priority_dialog.dialog_Add_Priority_Listener dialogAddPriorityListener;
    String name  = "-1";
    int keyId;
    EditText txt;
    public Priority_dialog(String name) {
        this.name= name;
        this.keyId = keyId;
    }
    public Priority_dialog() {
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.priority_form_fragment,null);
        txt = (EditText)view.findViewById(R.id.inputPriority);
        if(name.equals("-1")){  // when click add
            builder.setView(view).setTitle("Priority From").setNegativeButton("close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).setPositiveButton("add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    PriorityOJ priorityOJ = null;
                    String date = java.text.DateFormat.getDateTimeInstance().format(new Date());
                    try {
                        priorityOJ = new PriorityOJ(-1,txt.getText().toString(),date);

                        Toast.makeText(Priority_dialog.this.getContext(),priorityOJ.toString(),Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(Priority_dialog.this.getContext(),"error insert",Toast.LENGTH_SHORT).show();
                    }


                    priority_DB dbHelper = new priority_DB(Priority_dialog.this.getContext());
                    boolean success = dbHelper.addPriority(priorityOJ);
                    dialogAddPriorityListener.getData();
                    Toast.makeText(Priority_dialog.this.getContext(),"Success = " + success,Toast.LENGTH_SHORT).show();
                }
            });
        }
        return builder.create();
    }
    @Override
    public void onAttach( Context context) {
        super.onAttach(context);
        dialogAddPriorityListener = (dialog_Add_Priority_Listener) getParentFragment();
    }


}
