package com.example.youna.mayi;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GuardianDisplayActivity extends AppCompatActivity {
    private ImageView imageView12;
    private ImageView imageView13;
    private TextView codeText;
    private DatabaseReference testFirebase;
    private FirebaseAuth mAuth;
    private String email;
    private String authCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardian_display);
        mAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        email= intent.getStringExtra("email");

        codeText=(TextView)findViewById(R.id.codeText);

        imageView12 = (ImageView)findViewById(R.id.imageView12);
        imageView12.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                show();
            }
        });

        imageView13 = (ImageView)findViewById(R.id.imageView13);
        imageView13.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                show2();
            }
        });
        testFirebase = FirebaseDatabase.getInstance().getReference();
        authEmail(email);


    }
    void show(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("로그아웃 하시겠습니까?");
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAuth.signOut();
                        Intent intent = new Intent(GuardianDisplayActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });
        builder.show();
    }
    void show2(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("위치정보를 확인하시겠습니까?");
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(GuardianDisplayActivity.this, GpsActivity.class);
                        startActivity(intent);
                    }
                });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert_ex = new AlertDialog.Builder(this);
        alert_ex.setMessage("헬프투유를 종료하시겠습니까?");

        alert_ex.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alert_ex.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        AlertDialog alert = alert_ex.create();
        alert.show();
    }




    public void authEmail(final String abc){
        testFirebase.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Log.d("[Test]", "ValueEventListener : " + snapshot.getValue());

                    if(snapshot.child("이메일").getValue()==null) {
                        return;
                    }
                    String test2 =snapshot.child("이메일").getValue().toString();

                    if(test2.equals(abc)==true) {
                        authCode = snapshot.child("회원 코드").getValue().toString();
                        codeText.setText(authCode);
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}

