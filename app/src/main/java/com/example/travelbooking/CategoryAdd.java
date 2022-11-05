package com.example.travelbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class CategoryAdd extends AppCompatActivity {
    private EditText categoryEt;
    private Button submitBtn;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://travelbook-475c6-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_add);
        initUi();
        initListener();
    }

    private void initUi() {
        categoryEt=findViewById(R.id.categoryEt);
        submitBtn=findViewById(R.id.submitBtn);
    }
    private void initListener() {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random=new Random();
                String titleTxt =categoryEt.getText().toString().trim();
                int Id = random.nextInt(1000);
                databaseReference.child("Categories").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(titleTxt)){
                            Toast.makeText(CategoryAdd.this,"Danh mục đã có!", Toast.LENGTH_LONG).show();
                        }else {
                            databaseReference.child("Categories").child(titleTxt).child("id").setValue(Id);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

}