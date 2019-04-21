package com.example.adminuser.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminuser.R;
import com.example.adminuser.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.editTextName)
    EditText eTxtName;

    @BindView(R.id.editTextEmail)
    EditText eTxtEmail;

    @BindView(R.id.editTextPassword)
    EditText eTxtPassword;

    @BindView(R.id.textViewLogin)
    TextView txtLogin;

    @BindView(R.id.buttonRegister)
    Button btnRegister;

    User user;

    FirebaseAuth auth;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    FirebaseUser firebaseUser;

    void initViews(){

        ButterKnife.bind(this);
        btnRegister.setOnClickListener(this);
        txtLogin.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        user = new User();

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = auth.getCurrentUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initViews();
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.buttonRegister) {
            //Get the data from UI and put it into User Object
            user.name = eTxtName.getText().toString();
            user.email = eTxtEmail.getText().toString();
            user.password = eTxtPassword.getText().toString();

           // if(Util.isInternetConnected(this)) {
                progressDialog.show();
                registerUser();
            //}else{
              //  Toast.makeText(this,"Please Connect to Internet and Try Again",Toast.LENGTH_LONG).show();
       //     }
        } else {
            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    void registerUser(){

        auth.createUserWithEmailAndPassword(user.email,user.password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isComplete()) {
                            Toast.makeText(RegistrationActivity.this, user.name + "User created ", Toast.LENGTH_LONG).show();
                             progressDialog.dismiss();
                                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                        }

                    }
                });

    }
}
