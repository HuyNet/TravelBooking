package com.example.travelbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;
import java.util.UUID;

public class SignUp extends AppCompatActivity {
    private ImageButton backBtn;
    private EditText name,email,phone,password,Cpassword;
    private Button signUp;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://travelbook-475c6-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initUi();
        initListener();
    }

    private void initUi() {

        backBtn=findViewById(R.id.backBtn);
        name = findViewById(R.id.nameEt);
        email=findViewById(R.id.emailEt);
        phone=findViewById(R.id.phoneEt);
        password=findViewById(R.id.passwordEt);
        Cpassword=findViewById(R.id.CpasswordEt);
        signUp=findViewById(R.id.signUpBtn);
    }
    private static String getRandomString(int i){
        final String characters="0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
        StringBuffer result=new StringBuffer();
        while (i>0){
            Random random=new Random();
            result.append(characters.charAt(random.nextInt(characters.length())));
            i--;
        }
        return result.toString();
    }

    private void initListener() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random=new Random();
                 String nameTxt = name.getText().toString().trim();
                 String emailTxt = email.getText().toString().trim();
                 String phoneTxt = phone.getText().toString().trim();
                 String passwordTxt = password.getText().toString().trim();
                 String CpasswordTxt = Cpassword.getText().toString().trim();
                 String userId = getRandomString(7);
                 String Role = "user";
                if(TextUtils.isEmpty(nameTxt)){
                    Toast.makeText(SignUp.this,"Nhập tên của bạn!", Toast.LENGTH_LONG).show();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches()){
                    Toast.makeText(SignUp.this,"Nhập email!", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(phoneTxt)){
                    Toast.makeText(SignUp.this,"Nhập số điện thoại!", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(passwordTxt)){
                    Toast.makeText(SignUp.this,"Nhập mật khẩu!", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(CpasswordTxt)){
                    Toast.makeText(SignUp.this,"Nhập lại mật khẩu!", Toast.LENGTH_LONG).show();
                }
                else if(!passwordTxt.equals(CpasswordTxt)){
                    Toast.makeText(SignUp.this,"Mật khẩu không trùng!", Toast.LENGTH_LONG).show();
                }
                else {
                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(userId)){
                                Toast.makeText(SignUp.this,"Số điện thoại đã đăng ký!", Toast.LENGTH_LONG).show();
                            }
                            else {
                                databaseReference.child("Users").child(userId).child("fullname").setValue(nameTxt);
                                databaseReference.child("Users").child(userId).child("email").setValue(emailTxt);
                                databaseReference.child("Users").child(userId).child("phone").setValue(phoneTxt);
                                databaseReference.child("Users").child(userId).child("password").setValue(passwordTxt);
                                databaseReference.child("Users").child(userId).child("role").setValue(Role);
                                Toast.makeText(SignUp.this,"Tạo tài khoản thành công", Toast.LENGTH_LONG).show();
                                finish();
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