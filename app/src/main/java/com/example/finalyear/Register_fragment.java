package com.example.finalyear;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.finalyear.viewmodel.LoginViewModel;

public class Register_fragment extends Fragment {
    private EditText emalEt,passET,confirmpassET;
    private Button loginBTn,REgBTn;
    private TextView statustTV;



    private LoginViewModel loginViewModel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        loginViewModel =
                ViewModelProviders.of(this).get(LoginViewModel.class);
        View root = inflater.inflate(R.layout.fragment_registration, container, false);


        return root;
    }
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
     
        emalEt=view.findViewById(R.id.usernameinput);
        passET=view.findViewById(R.id.passwrodinput);
        REgBTn=view.findViewById(R.id.registerbutton);
        loginBTn=view.findViewById(R.id.loginbtn);
        statustTV=view.findViewById(R.id.statusTV);
        confirmpassET=view.findViewById(R.id.confirmpasswrodinput);

       /**/
        REgBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=emalEt.getText().toString();
                String password=passET.getText().toString();
                String confirmpass=confirmpassET.getText().toString();
                if (password.equals(confirmpass)){
                    loginViewModel.register(email,password);
                }else {
                    Toast.makeText(getActivity(), "password not match", Toast.LENGTH_SHORT).show();
                }

            }
        });


        loginViewModel.statelivedata.observe(this, new Observer<LoginViewModel.Authenticaitionstate>() {
            @Override
            public void onChanged(LoginViewModel.Authenticaitionstate authenticaitionstate) {
                switch (authenticaitionstate){
                    case AUTHENTICATED:
                        Navigation.findNavController(view).navigate(R.id.action_nav_tools_to_nav_home);
                        Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                        break;
                    case UNAUTHENTICATED:
                        break;
                }
            }
        });
        loginViewModel.errmsg.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                statustTV.setText(s);
            }
        });


    }

}