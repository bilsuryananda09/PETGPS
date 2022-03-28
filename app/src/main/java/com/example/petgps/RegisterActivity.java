package com.example.petgps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {
    private EditText editFullname, editEmail, editPassword, editPasswordConf;
    private TextView login;
    private Button btnRegis;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editFullname = findViewById(R.id.fullname);
        editEmail = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);
        editPasswordConf = findViewById(R.id.passwordconf);
        btnRegis = findViewById(R.id.registerbtn);
        login = findViewById(R.id.login);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Silakan Tunggu!");
        progressDialog.setCancelable(false);

        login.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        });

        btnRegis.setOnClickListener(v ->{
            if(editFullname.getText().length()>0 && editEmail.getText().length()>0 && editPassword.getText().length()>0 && editPasswordConf.getText().length()>0){
                if(editPassword.getText().toString().equals(editPasswordConf.getText().toString())){
                    register(editFullname.getText().toString(), editEmail.getText().toString(), editPassword.getText().toString());
                }else{
                    Toast.makeText(getApplicationContext(),"Silahkan masukan password yang sama", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getApplicationContext(),"Silahkan isi semua data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void register(String fullname, String email, String password){
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful() && task.getResult()!=null){
                    FirebaseUser firebaseuser = task.getResult().getUser();
                    UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                            .setDisplayName(fullname)
                            .build();
                    firebaseuser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage() , Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void reload(){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }
}