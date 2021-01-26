package com.example.loginnote.ui.cetegory;

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

import com.example.loginnote.R;

import java.util.Date;

public class Category_dialog extends DialogFragment {
    public  interface  dialog_Add_Category_Listener{
        void getData();
    }
    public   dialog_Add_Category_Listener dialogAddCategoryListener ;
    String name  = "-1";
    int keyId  ;
    public Category_dialog(String name,int keyId) {
        this.name= name;
        this.keyId = keyId;
    }
    public Category_dialog() {
    }

    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState) {
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.category_dialog_fragment,null);

        if(name.equals("-1")){  // when click add
            builder.setView(view)
                    .setTitle("Category From").setNegativeButton("close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).setPositiveButton("add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditText txt =  (EditText)view.findViewById(R.id.input);

                    try {
                        String date = java.text.DateFormat.getDateTimeInstance().format(new Date());
                        Category_OJ category_OJ = new Category_OJ(-1,txt.getText().toString(),date);
                        category_DB category_db = new category_DB(Category_dialog.this.getContext());
                        category_db.insetCategory(category_OJ);
                    }catch (ClassCastException e){
                        Toast.makeText(Category_dialog.this.getContext(),"error insert",Toast.LENGTH_SHORT).show();
                    }
                    dialogAddCategoryListener.getData();
                }
            });
        }else {
            EditText txt =  (EditText)view.findViewById(R.id.input);
            txt.setText(name);
            builder.setView(view)
                    .setTitle("Category Edit").setNegativeButton("close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).setPositiveButton("save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Category_OJ category_OJ = new Category_OJ();
                    category_OJ.setName(txt.getText().toString());
                    category_OJ.setCreateDate(java.text.DateFormat.getDateTimeInstance().format(new Date()));
                    category_OJ.setId(keyId);
                    category_DB category_db = new category_DB(Category_dialog.this.getContext());
                    category_db.updateCategory(category_OJ);
                    dialogAddCategoryListener.getData();
                }
            });

        }

        return builder.create();
    }

        // send data to fragment
        @Override
        public void onAttach (Context context){
            super.onAttach(context);
            dialogAddCategoryListener = (dialog_Add_Category_Listener) getParentFragment();
        }
}