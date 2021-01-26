package com.example.loginnote.ui.note;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.loginnote.R;
import com.example.loginnote.ui.cetegory.Category_OJ;
import com.example.loginnote.ui.priority.PriorityOJ;
import com.example.loginnote.ui.status.Status_OJ;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.loginnote.LoginActivity.AccInfo;

public class Note_dialog extends DialogFragment{
    Button btn;
    TextView txt;
    EditText name;
    Spinner category,priority,status;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private  Note_dialog_listen note_dialog_listen;
    noteOJ noteOJData;
    int keyId = -1 ;
    public Note_dialog(noteOJ noteOJ,int keyId) {
        this.noteOJData= noteOJ;
        this.keyId = keyId;
    }
    public Note_dialog() {
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState){
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_note,null);
        txt = view.findViewById(R.id.show_calendar);
        name = view.findViewById(R.id.input_note);
        category =  view.findViewById(R.id.select_category);
        priority =  view.findViewById(R.id.select_priority);
        status =  view.findViewById(R.id.select_status);

        addDataSpinnerCategory(view);
        addDataSpinnerStatus(view);
        addDataSpinnerPriority(view);

        btn = view.findViewById(R.id.open_calendar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year  = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog  = new DatePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener,year,month,day
                );
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month+=1;
                String date = dayOfMonth+"/"+month+"/"+year;
                txt.setText(date);
            }
        };

        if(keyId == -1){
            // when click add
            builder.setView(view)
                    .setTitle("Note form").setNegativeButton("close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).setPositiveButton("add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String date = java.text.DateFormat.getDateTimeInstance().format(new Date());
                    noteOJ noteOJ = new noteOJ(
                            name.getText().toString(),
                            category.getSelectedItem().toString(),
                            priority.getSelectedItem().toString(),
                            status.getSelectedItem().toString(),
                            txt.getText().toString(),
                            date,
                            -1
                    );
                    note_DB note_db = new note_DB(getContext());
                    if(checkDataInput(noteOJ)){
                        try {
                            note_db.insertCategory(noteOJ);
                            note_dialog_listen.getData();
                        }catch (Exception e){
                            Toast.makeText(getContext(),"error insert",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getContext(),R.string.insert_full,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            name.setText(noteOJData.getName());
            addDataSpinnerCategory(view,noteOJData.getCategory());
            addDataSpinnerStatus(view,noteOJData.getStatus());
            addDataSpinnerPriority(view,noteOJData.getPriority());
            txt.setText(noteOJData.getPlanDate());


            // when click add
            builder.setView(view)
                    .setTitle("Note form").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).setPositiveButton("save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String date = java.text.DateFormat.getDateTimeInstance().format(new Date());
                    noteOJ noteOJ = new noteOJ(
                            name.getText().toString(),
                            category.getSelectedItem().toString(),
                            priority.getSelectedItem().toString(),
                            status.getSelectedItem().toString(),
                            txt.getText().toString(),
                            date,
                            AccInfo.getId()
                    );
                    note_DB note_db = new note_DB(getContext());

                    try {
                        note_db.updateNote(noteOJ);
                        note_dialog_listen.getData();
                    }catch (Exception e){
                        Toast.makeText(getContext(),"error update",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        note_dialog_listen.getData();
        return builder.create();
    }

    private  Boolean checkDataInput(noteOJ noteOJ){
        if(noteOJ.getCategory().equals("")
                || noteOJ.getName().equals("")
                || noteOJ.getPlanDate().equals("")
                || noteOJ.getPriority().equals("")
                || noteOJ.getStatus().equals("")
        )
            return false;
        return true;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        note_dialog_listen = (Note_dialog_listen) getParentFragment();
    }

    private void addDataSpinnerCategory(View view){
        note_DB note_db = new note_DB(Note_dialog.this.getContext());
        List<Category_OJ> categories = note_db.getSpinnerCategory();
        List<String> nameCategori = new ArrayList<>();
        for (Category_OJ cate:categories) {
            nameCategori.add(cate.getName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, nameCategori);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinnerCategory = view.findViewById(R.id.select_category);
        spinnerCategory.setAdapter(dataAdapter);
    }

    private void addDataSpinnerCategory(View view,String category){
        note_DB note_db = new note_DB(Note_dialog.this.getContext());
        List<Category_OJ> categories = note_db.getSpinnerCategory();
        List<String> nameCategori = new ArrayList<>();
        for (Category_OJ cate:categories) {
            nameCategori.add(cate.getName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, nameCategori);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinnerCategory = view.findViewById(R.id.select_category);
        spinnerCategory.setAdapter(dataAdapter);
        if(category != null){
            int spinnerPosition = dataAdapter.getPosition(category);
            spinnerCategory.setSelection(spinnerPosition);
        }
    }

    private void addDataSpinnerPriority(View view){
        note_DB note_db = new note_DB(Note_dialog.this.getContext());
        List<PriorityOJ> priorityOJS = note_db.getSpinnerPriority();
        List<String> namePriority = new ArrayList<>();
        for (PriorityOJ cate:priorityOJS) {
            namePriority.add(cate.getName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, namePriority);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinnerPriority = view.findViewById(R.id.select_priority);
        spinnerPriority.setAdapter(dataAdapter);
    }

    private void addDataSpinnerPriority(View view,String Priority){
        note_DB note_db = new note_DB(Note_dialog.this.getContext());
        List<PriorityOJ> priorityOJS = note_db.getSpinnerPriority();
        List<String> namePriority = new ArrayList<>();
        for (PriorityOJ cate:priorityOJS) {
            namePriority.add(cate.getName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, namePriority);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinnerPriority = view.findViewById(R.id.select_priority);
        spinnerPriority.setAdapter(dataAdapter);
        if(Priority != null){
            int spinnerPosition = dataAdapter.getPosition(Priority);
            spinnerPriority.setSelection(spinnerPosition);
        }

    }


    private void addDataSpinnerStatus(View view){
        note_DB note_db = new note_DB(Note_dialog.this.getContext());
        List<Status_OJ> lstStatus = note_db.getSpinnerStatus();
        List<String> nameStatus = new ArrayList<>();
        for (Status_OJ cate:lstStatus) {
            nameStatus.add(cate.getName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, nameStatus);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinnerStatus = view.findViewById(R.id.select_status);
        spinnerStatus.setAdapter(dataAdapter);
    }

    private void addDataSpinnerStatus(View view, String status){
        note_DB note_db = new note_DB(Note_dialog.this.getContext());
        List<Status_OJ> lstStatus = note_db.getSpinnerStatus();
        List<String> nameStatus = new ArrayList<>();
        for (Status_OJ cate:lstStatus) {
            nameStatus.add(cate.getName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, nameStatus);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinnerStatus = view.findViewById(R.id.select_status);
        spinnerStatus.setAdapter(dataAdapter);
        if(status != null){
            int spinnerPosition = dataAdapter.getPosition(status);
            spinnerStatus.setSelection(spinnerPosition);
        }
    }






    public interface Note_dialog_listen{
        void getData();
    }
}




