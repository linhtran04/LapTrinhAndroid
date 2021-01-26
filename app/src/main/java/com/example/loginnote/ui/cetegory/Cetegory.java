package com.example.loginnote.ui.cetegory;
import android.content.Context;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.loginnote.R;
import java.util.ArrayList;
import java.util.List;

public class Cetegory extends Fragment implements Category_dialog.dialog_Add_Category_Listener, PopupMenu.OnMenuItemClickListener {

    private  static  String TAG="category";
    private CetegoryViewModel mViewModel;

    public static Cetegory newInstance() {
        return new Cetegory();
    }


    private List<Category_OJ> listcategory = new ArrayList<Category_OJ>();
    private int index = 0;
    private ListView listView;
    ArrayList<Category_OJ> arrayCategoryOJS = new ArrayList<Category_OJ>();
    private CategoryAdapter adapter = null;
    Button cancel,add;



    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,
                              Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cetegory_fragment, container, false);
        FloatingActionButton b = v.findViewById(R.id.add_category);
        listView = v.findViewById(R.id.lv_category);
        b.setOnClickListener(new View.OnClickListener() {  // button click
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        listView = v.findViewById(R.id.lv_category);
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
        Category_dialog dialogAddCategory = new Category_dialog();
        dialogAddCategory.show(getChildFragmentManager(),"example dialog");
    }

    //open dialog edit when press and hold
    public  void openDialogEdit(String name , int key){
        Category_dialog dialogAddCategory = new Category_dialog(name,key);
        dialogAddCategory.show(getChildFragmentManager(),"example dialog");
    }

    @Override
    public void getData() {
        refreshData();
    }



    public void refreshData(){
        category_DB categoryDb = new category_DB(getContext());
        listcategory = categoryDb.getListCategory();
        CategoryAdapter categoryAdapter = new CategoryAdapter();
        listView.setAdapter(categoryAdapter);
    }




    // adapter
    public class CategoryAdapter extends ArrayAdapter<Category_OJ> {
        public CategoryAdapter(Context context , int textViewResourceId){
            super(context,textViewResourceId);
        }
        public CategoryAdapter(){
            super(Cetegory.this.getContext(), android.R.layout.simple_list_item_1,listcategory);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View row = convertView;
            if(row == null){
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.row_category,null);
            }
            Category_OJ c = listcategory.get(position);
            ((TextView)row.findViewById(R.id.name)).setText(c.getName());
            ((TextView)row.findViewById(R.id.date)).setText(c.getCreateDate());
            return row;
        }
    }


    // shows a pop-up when pressed and held
    public  void  showPopup(View v){
        PopupMenu popupMenu = new PopupMenu(getContext(),v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu_category);
        popupMenu.show();
    }

    // switch case chose
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch(item.getItemId()){
            case R.id.edit:
                openDialogEdit(listcategory.get(index).getName(),listcategory.get(index).getId());
                return  true;
            case R.id.delete:
                category_DB category_db = new category_DB(getContext());
                category_db.deleteCategory(listcategory.get(index).getId());
                getData();
                return  true;
            default:
                return false;
        }

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CetegoryViewModel.class);
        // TODO: Use the ViewModel
    }
}
