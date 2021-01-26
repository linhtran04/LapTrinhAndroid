package com.example.loginnote.ui.editprofile;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginnote.DatabaseHelper;
import com.example.loginnote.LoginActivity;
import com.example.loginnote.MainActivity;
import com.example.loginnote.R;
import com.example.loginnote.User;

import static com.example.loginnote.LoginActivity.AccInfo;

public class profile extends Fragment {

    private EditProfileViewModel mViewModel;
    Button btnHome, btnConfirm;
    EditText etName,  etEmail;
    DatabaseHelper helper;

    public static profile newInstance() {
        return new profile();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_profile_fragment, container, false);
        btnHome = (Button)view.findViewById(R.id.btnReturnHome);
        btnConfirm = (Button)view.findViewById(R.id.btnConfirmEditProfile);
        etName = (EditText)view.findViewById(R.id.etEditName);
        etEmail = (EditText)view.findViewById(R.id.etEditEmail);

        helper = new DatabaseHelper(getContext());
        User user = helper.getAccInfo(AccInfo.getEmail(), AccInfo.getPassword());
        etName.setText(user.getName());
        etEmail.setText(AccInfo.getEmail());

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(profile.this.getContext(),"Home",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(profile.this.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_DB profile_db = new profile_DB(profile.this.getContext());
                String Name = etName.getText().toString();
                String email = etEmail.getText().toString();

                ProfileOJ profileOJ = new ProfileOJ(AccInfo.getId(), Name, email,AccInfo.getPassword());
                boolean update =profile_db.updateProfile(profileOJ);
                if(update == true)
                    Toast.makeText(profile.this.getContext(),"update profile ",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(profile.this.getContext(),"update error profile ",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);
        // TODO: Use the ViewModel
    }

}