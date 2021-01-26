package com.example.loginnote.ui.changepass;

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
import com.example.loginnote.MainActivity;
import com.example.loginnote.R;
import com.example.loginnote.User;

import static com.example.loginnote.LoginActivity.AccInfo;
public class ChangePassword extends Fragment{

    private ChangePasswordViewModel mViewModel;
    Button btnChange, btnReturn;
    EditText edOld_Pass, edNew_Pass, edAgain_Pass;
    DatabaseHelper helper;

    public static ChangePassword newInstance() {
        return new ChangePassword();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.change_password_fragment, container, false);
        btnChange = (Button)view.findViewById(R.id.btnChangePass);
        btnReturn = (Button)view.findViewById(R.id.btnChangePass_Return);
        edOld_Pass = (EditText)view.findViewById(R.id.etChangePass_Current);
        edNew_Pass = (EditText)view.findViewById(R.id.etChangePass_New);
        edAgain_Pass = (EditText)view.findViewById(R.id.etChangePass_Confirm);

        helper = new DatabaseHelper(getContext());
        User user = helper.getAccInfo(AccInfo.getEmail(), AccInfo.getPassword());
        edOld_Pass.setText(user.getPassword());


        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePassword_DB changePasswordDb = new ChangePassword_DB(ChangePassword.this.getContext());
                String pass_old = edOld_Pass.getText().toString();
                String pass_new = edNew_Pass.getText().toString();
                String pass_new_agian = edAgain_Pass.getText().toString();
                if(pass_old.equals(AccInfo.getPassword())){
                    if(pass_new.equals("") && pass_new_agian.equals("") && pass_old.equals("")) {
                        Toast.makeText(ChangePassword.this.getContext()," Error, please enter value ",Toast.LENGTH_SHORT).show();
                    }else if(pass_new_agian.equals("") && pass_new.equals("")){
                        Toast.makeText(ChangePassword.this.getContext()," Error, please enter new password and confirm password ",Toast.LENGTH_SHORT).show();
                    }else if(pass_old.equals("") && pass_new.equals("")){
                            Toast.makeText(ChangePassword.this.getContext()," Error, please enter old password and new password ",Toast.LENGTH_SHORT).show();
                    }else if(pass_old.equals("") && pass_new_agian.equals("")){
                        Toast.makeText(ChangePassword.this.getContext()," Error, please enter old password and confirm password ",Toast.LENGTH_SHORT).show();
                    }else if(pass_new.equals("")) {
                        Toast.makeText(ChangePassword.this.getContext(), " Error, please enter new password ", Toast.LENGTH_SHORT).show();
                    }else if(pass_new_agian.equals("")) {
                        Toast.makeText(ChangePassword.this.getContext(), " Error, please enter new confirm password ", Toast.LENGTH_SHORT).show();
                    }else if(pass_old.equals("")) {
                        Toast.makeText(ChangePassword.this.getContext(), " Error, please enter old password ", Toast.LENGTH_SHORT).show();
                    }else {
                        if(pass_new.equals(pass_new_agian)) {
                            ChangePassword_OJ changePasswordOj = new ChangePassword_OJ(AccInfo.getId(), AccInfo.getName(),  AccInfo.getEmail(), pass_new);
                            boolean update = changePasswordDb.updatePass(changePasswordOj);
                            if (update == true) {
                                Toast.makeText(ChangePassword.this.getContext(), " update password ", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(ChangePassword.this.getContext(), " update error password ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Toast.makeText(ChangePassword.this.getContext(), " password not match ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChangePassword.this.getContext(),"return",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChangePassword.this.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ChangePasswordViewModel.class);
        // TODO: Use the ViewModel
    }
}
