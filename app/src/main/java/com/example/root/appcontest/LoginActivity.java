package com.example.root.appcontest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity{

    Button login;
    Button signUp;
    EditText editEmail;
    EditText editPassword;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    boolean isEmptyEditField()
    {
        if((editEmail.getText().toString().length() == 0)||(editPassword.getText().toString().length() == 0))
            return true;
        else
            return false;
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        login = findViewById(R.id.login);
        signUp = findViewById(R.id.sign_up);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        mAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (isEmptyEditField()) {
                    Toast.makeText(LoginActivity.this, "모든 칸을 채워주세요!!", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(editEmail.getText().toString(), editPassword.getText().toString())
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, "로그인 실패~~", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "로그인 성공!!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });

                }

            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,Sign_up.class);
                startActivity(intent);
                finish();
            }
        });
        this.setTitle("로그인");
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null){
                    Toast.makeText(LoginActivity.this, "로그인 성공!!", Toast.LENGTH_SHORT).show();


                }
                else{
                    Toast.makeText(LoginActivity.this,"로그아웃 성공!!", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
}
