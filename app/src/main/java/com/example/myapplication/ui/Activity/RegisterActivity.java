package com.example.myapplication.ui.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.ui.Model.User.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    EditText Username;
    EditText Password;
    EditText Email;
    Button RegisterNow;
    UserModel userModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Username = findViewById(R.id.RegUsername);
        Password = findViewById(R.id.RegPassword);
        Email = findViewById(R.id.Email);

        RegisterNow = findViewById(R.id.RegisterNow);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance(getResources().getString(R.string.USER_DATABASE_URL)).getReference();


        RegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = Username.getText().toString();
                String password = Password.getText().toString();
                String email = Email.getText().toString();


                if (username.isEmpty()||password.isEmpty()||email.isEmpty()){
                    AlertDialog("All field must be filled");
                }
                else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(username)){
                                AlertDialog("Username already exist");
                            }
                            else {
                                UserModel userModel = new UserModel(username,password,email);
                                databaseReference.child("users").child(username).setValue(userModel);
                                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                intent.putExtra("Username",Username.getText().toString());
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

    public void AlertDialog(String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setMessage(Message)
                .setTitle("Alert")
        .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}