package com.example.myapplication.ui.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    AppCompatButton Register;
    EditText Username;
    EditText Password;
    Button Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Username = findViewById(R.id.Username);
        Password = findViewById(R.id.Password);
        Login = findViewById(R.id.Login);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance(getResources().getString(R.string.USER_DATABASE_URL)).getReference();

        Register = findViewById(R.id.Register);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = Username.getText().toString();
                String password = Password.getText().toString();

                if (username.isEmpty()||password.isEmpty()){
                    AlertDialog("All field must be filled");
                }

                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(username)){
                            Log.d("TAG", "onDataChange: Username found");
                            String FBPassword = snapshot.child(username).child("password").getValue(String.class);
                            if (FBPassword.equals(password)){
                                Log.d("TAG", "onDataChange: Password benar");
                                String FBEmail = snapshot.child(username).child("email").getValue(String.class);
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                intent.putExtra("username",username);
                                startActivity(intent);
                            }
                            else{
                                Log.d("", "onDataChange: Password salah");
                                AlertDialog("Username or password is wrong");
                            }
                        }
                        else{
                            Log.d("TAG", "onDataChange: Username Salah");
                            AlertDialog("Username or password is wrong");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }

    public void AlertDialog(String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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