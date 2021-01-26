package com.example.loginnote.ui.priority;


import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.loginnote.DBHelper;
import com.example.loginnote.R;
import com.example.loginnote.ui.cetegory.Category_dialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Priority extends Fragment implements Priority_dialog.dialog_Add_Priority_Listener{

    private  static  String TAG="priority";
    private PriorityViewModel mViewModel;

    private List<PriorityOJ> listPriority = new ArrayList<PriorityOJ>();
    private int index = 0;
    private ListView listView;
    ArrayList<PriorityOJ> arrayPriority = new ArrayList<PriorityOJ>();
    private PriorityAdapter adapter = null;
    Button cancel,add;

    public static Priority newInstance() {
        return new Priority();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.priority_fragment, container, false);
        FloatingActionButton b = v.findViewById(R.id.add_priority);
        listView = v.findViewById(R.id.lvPriority);
        refreshDB();
        b.setOnClickListener(new View.OnClickListener() {  // button click
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        return v;
    }

    public void openDialog(){
        Priority_dialog dialogAddPriority = new Priority_dialog();
        dialogAddPriority.show(getChildFragmentManager(),"example dialog");
    }

 /*   public void openDialogEdit(String name, int key){
        Priority_dialog dialogAddPriority = new Priority_dialog(name,key);
        dialogAddPriority.show(getChildFragmentManager(),"example dialog");
    }*/



    @Override
    public void getData() {
        refreshDB();
    }

    public void refreshDB(){
        priority_DB dbHelper = new priority_DB(Priority.this.getContext());
        listPriority = dbHelper.priorityOJList();
        PriorityAdapter priorityArrayAdapter = new PriorityAdapter();
        listView.setAdapter(priorityArrayAdapter);
    }

    public class PriorityAdapter extends ArrayAdapter<PriorityOJ>{
        public PriorityAdapter(Context context ,int textViewResourceId){
            super(context,textViewResourceId);
        }
        public PriorityAdapter(){
            super(Priority.this.getContext(), android.R.layout.simple_list_item_1,listPriority);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View row = convertView;
            if(row == null){
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.row_priority,null);
            }
            PriorityOJ p = listPriority.get(position);
            ((TextView)row.findViewById(R.id.name)).setText(p.getName());
            ((TextView)row.findViewById(R.id.date)).setText(p.getCreateDate());
            return row;
        }
    }

  /*  public void showPopup(View v){
        PopupMenu popupMenu = new PopupMenu(getContext(),v);
       popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu_priority);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch(item.getItemId()){
            case R.id.edit:
                openDialogEdit(listPriority.get(index).getName(),listPriority.get(index).getId());
                return  true;
            case R.id.delete:
                priority_DB priority_db = new priority_DB(Priority.this.getContext());
                priority_db.deletePriority(listPriority.get(index).getId());
                getData();
                return  true;
            default:
                return false;
        }
    }*/

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PriorityViewModel.class);
        // TODO: Use the ViewModel
    }



    public  interface  dialog_Add_Priority_Listener{
        void applyAdd(String priority, String date);
    }
}