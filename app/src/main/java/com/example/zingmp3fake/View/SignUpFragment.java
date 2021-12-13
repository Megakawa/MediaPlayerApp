package com.example.zingmp3fake.View;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zingmp3fake.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpFragment extends Fragment {
    private FirebaseAuth firebaseAuth;

    private EditText User;
    private EditText Pass;
    private EditText RePass;
    private TextView Login;
    private TextView Warning;
    private Button SignUp;


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
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        User = view.findViewById(R.id.et_user);
        Pass = view.findViewById(R.id.et_pass);
        RePass = view.findViewById(R.id.et_repass);
        Login = view.findViewById(R.id.Login);
        SignUp = view.findViewById(R.id.bt_SignUp);
        Warning = view.findViewById(R.id.Warning);

        FirebaseApp.initializeApp(getContext());
        firebaseAuth = FirebaseAuth.getInstance();

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = User.getText().toString();
                String pass = Pass.getText().toString();
                String repass = RePass.getText().toString();
                if (repass.equals(pass))
                {
                    try {
                        createNewUser(email,pass);
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Success").setMessage("Congratulation, your account has been successfully created.");
                        builder.setCancelable(true);
                        AlertDialog alert = builder.create();
                        alert.show();
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                    catch (Exception e) {
                        Warning.setText(e.getMessage());
                    }
                }
                else
                {
                    Warning.setText("The password you have entered does not match your current one");
                }
                User.setText("");
                Pass.setText("");
                RePass.setText("");
            }
        });
    }

    public void createNewUser(String newEmail, String newPass){
        firebaseAuth.createUserWithEmailAndPassword(newEmail,newPass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                        }else{

                        }
                    }
                });
    }
}