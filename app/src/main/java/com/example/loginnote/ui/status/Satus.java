package com.example.loginnote.ui.status;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginnote.R;
import com.example.loginnote.ui.priority.Priority;
import com.example.loginnote.ui.priority.priority_DB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Satus extends Fragment  implements Status_dialog.dialog_Add_Status_Listener,PopupMenu.OnMenuItemClickListener{
    private  static  String TAG="category";
    private StatusViewModel mViewModel;

    public static Satus newInstance() {
        return new Satus();
    }

    private List<Status_OJ> liststatus = new ArrayList<Status_OJ>();
    private int index = 0;
    private ListView listView;
    ArrayList<Status_OJ> arrayCategoryOJS = new ArrayList<Status_OJ>();
    private StatusAdapter adapter = null;
    Button cancel,add;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.status_fragment, container, false);
        FloatingActionButton b = v.findViewById(R.id.add_status);
        listView = v.findViewById(R.id.lv_status);
        b.setOnClickListener(new View.OnClickListener() {  // button click
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        listView = v.findViewById(R.id.lv_status);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {  // press and hold
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                index = position;
                showPopup(view);
                return false;
            }
        });
        refreshData();
        return v;
    }


    // open dialog add category
    public  void openDialog(){
        Status_dialog dialogAddStatus = new Status_dialog();
        dialogAddStatus.show(getChildFragmentManager(),"example dialog");
    }

    //open dialog edit when press and hold
    public  void openDialogEdit(String name , int key){
        Status_dialog dialogAddStatus = new Status_dialog(name,key);
        dialogAddStatus.show(getChildFragmentManager(),"example dialog");
    }

    @Override
    public void getData() {
        refreshData();
    }

    public void refreshData() {
        Status_DB status_db = new Status_DB(Satus.this.getContext());
        liststatus = status_db.getListStatus();
        StatusAdapter statusAdapter = new StatusAdapter();
        listView.setAdapter(statusAdapter);
    }
    // adapter
    public class StatusAdapter extends ArrayAdapter<Status_OJ>{
        public StatusAdapter(Context context ,int textViewResourceId){
            super(context,textViewResourceId);
        }
        public StatusAdapter(){
            super(Satus.this.getContext(), android.R.layout.simple_list_item_1,liststatus);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View row = convertView;
            if(row == null){
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.row_status,null);
            }

            Status_OJ c = liststatus.get(position);
            ((TextView)row.findViewById(R.id.name)).setText(c.getName());
            ((TextView)row.findViewById(R.id.date)).setText(c.getCreatedate());
            return row;
        }
    }


    // shows a pop-up when pressed and held
    public  void  showPopup(View v){
        PopupMenu popupMenu = new PopupMenu(getContext(),v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu_status);
        popupMenu.show();
    }

    // switch case chose
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch(item.getItemId()){
            case R.id.edit:
                openDialogEdit(liststatus.get(index).getName(),liststatus.get(index).getId());
                return  true;
            case R.id.delete:
                Status_DB status_db = new Status_DB(Satus.this.getContext());
                status_db.deleteStatus(liststatus.get(index).getId());
                getData();
                return  true;
            default:
                return false;
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(StatusViewModel.class);
        // TODO: Use the ViewModel
    }


}


