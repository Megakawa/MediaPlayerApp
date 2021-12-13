package com.example.zingmp3fake.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zingmp3fake.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private Button login;
    private TextView Forgot;
    private TextView AddUser;
    private EditText User;
    private EditText Pass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseApp.initializeApp(getContext());
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
        login = view.findViewById(R.id.bt_login);
        Forgot = view.findViewById(R.id.tv_forgot);
        AddUser = view.findViewById(R.id.tv_addUser);
        User = view.findViewById(R.id.et_user);
        Pass = view.findViewById(R.id.et_pass);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = User.getText().toString();
                String pass = Pass.getText().toString();
                try {
                    Login(email,pass);
                }
                catch (Exception e){
                    Toast.makeText(getContext(),"wrong password or Email",Toast.LENGTH_LONG).show();
                }
            }
        });
        Forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = User.getText().toString();
                try {
                    resetPass(email);
                }
                catch (Exception e){
                    Toast.makeText(getContext(),"Email is empty",Toast.LENGTH_LONG).show();
                }
            }
        });
        AddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.signUpFragment);
            }
        });
    }

    public void Login(String email, String pass){
        firebaseAuth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Navigation.findNavController(getView()).navigate(R.id.selection_Fragment);
                        }else{
                        }
                    }
                });
    }
    public void resetPass(String email){
        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(),"Password reset email sent",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getContext(),"Email is not in database",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}