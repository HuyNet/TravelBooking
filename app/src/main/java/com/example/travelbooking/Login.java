package com.example.travelbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    private LinearLayout layoutSignUp;
    private EditText phone,password;
    private Button loginBtn;

    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://travelbook-475c6-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUi();
        initListener();
    }

    private void initUi() {

        layoutSignUp = findViewById(R.id.noAccount);
        phone =findViewById(R.id.phoneEt);
        password =findViewById(R.id.passwordEt);
        loginBtn =findViewById(R.id.loginBtn);

    }
    private void initListener() {
        layoutSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Login.this,SignUp.class);
                startActivity(intent);
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneTxt = phone.getText().toString().trim();
                String passwordTxt = password.getText().toString().trim();
                if(TextUtils.isEmpty(phoneTxt)){
                    Toast.makeText(Login.this,"Số điện thoại sai...",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(passwordTxt)){
                    Toast.makeText(Login.this,"Password sai...",Toast.LENGTH_SHORT).show();
                }
                else {
                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.hasChild(phoneTxt)){
                                    String getPassword = snapshot.child(phoneTxt).child("password").getValue(String.class);
                                     if(getPassword.equals(passwordTxt)){
                                        Toast.makeText(Login.this,"Đăng nhập thành công !",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Login.this,AdminActivity.class));
                                        finish();
                                    }else {
                                        Toast.makeText(Login.this,"Mật khẩu không đúng!",Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(Login.this,"Số điện thoại không !",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
}