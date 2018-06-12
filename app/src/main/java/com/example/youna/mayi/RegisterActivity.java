package com.example.youna.mayi;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v4.view.ScrollingView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    Spinner spinner;
    ScrollingView ScroallView;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseDatabase mFirebase;
    private DatabaseReference testFirebase;
    private FirebaseUser currentUser;
    private String testEmail, testPassword, nickname, passwordAnswer;
    public int num = 0;
    public int cnt = 0;
    public int emailCount;
    public int nickCount;
    private EditText editText3;
    private EditText editText4;
    private EditText editText5;
    private EditText editText6;
    private EditText editText9;
    public String[] dataEamil;
    public String[] emailArray;
    public String[] comEamil;
    public String[] comNick;
    public String[] nickArray;
    public String[] dataNick;
    public List emailList;
    public List nickList;
    public List codeList;
    private int dataCnt;
    private int ran_num1; //랜덤4자리 숫자
    private String ran_num2; //랜덤4자리숫자(String)
    private char ran_engle1; //랜덤영어대문자1
    private char ran_engle1_1; //랜덤영어대문자2
    private String ran_engle2; //랜덤영어대문자1 (String)
    private String ran_engle2_2; //랜덤영어대문자2 (String)
    private String saveCode;
    private String code; //회원코드
    private String dataCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        Intent intent=new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //mFirebaseDatabase.getInstance().getReference();
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.Hints, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        cnt=0;
        final TextView textView10 = (TextView) findViewById(R.id.textView10);
        textView10.setMovementMethod(new ScrollingMovementMethod());

        TextView textView11 = (TextView) findViewById(R.id.textView11);
        textView11.setMovementMethod(new ScrollingMovementMethod());

        TextView textView12 = (TextView) findViewById(R.id.textView11);
        textView12.setMovementMethod(new ScrollingMovementMethod());

        editText3 = (EditText) findViewById(R.id.idText);
        editText3.setPrivateImeOptions("defaultInputmode=english;");

        editText4 = (EditText) findViewById(R.id.editText4);
        editText4.setPrivateImeOptions("defaultInputmode=english;");

        editText5 = (EditText) findViewById(R.id.editText5);

        editText6 = (EditText) findViewById(R.id.editText6);
        //editText6.setFilters(new InputFilter[]{filterAlphaNum});
        editText9 = (EditText) findViewById(R.id.editText9);

        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        final CheckBox checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        final CheckBox checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
        final Button Button2 = (Button) findViewById(R.id.Button2);
        final Button overlapButton = (Button) findViewById(R.id.overlapButton);
        final Button button4 = (Button) findViewById(R.id.button4);

        editText4.setEnabled(false);
        editText5.setEnabled(false);
        editText6.setEnabled(false);
        editText9.setEnabled(false);

        spinner.setEnabled(false);
        checkBox.setEnabled(false);
        checkBox2.setEnabled(false);
        checkBox3.setEnabled(false);
        Button2.setEnabled(false);
        Button2.setBackgroundColor(Color.rgb(191, 191, 191));

        emailList=new ArrayList();
        nickList=new ArrayList();
        codeList=new ArrayList();

        num = 2;
        testFirebase = FirebaseDatabase.getInstance().getReference();
        copyFirebase();
        editText4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    editText5.setEnabled(true);
                } else {
                    editText5.setEnabled(true);
                }
            }

            public void afterTextChanged(Editable s) {
                if (s.length() > 10) {
                    s.delete(10, 11);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
                    dialog.setMessage("10자리까지 입력해주세요").setPositiveButton("확인", null).create();
                    dialog.show();
                }
            }
        });

        editText5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = editText4.getText().toString();
                String confirm = editText5.getText().toString();

                if (password.equals(confirm)) {
                    editText4.setBackgroundColor(Color.rgb(151, 199, 119));
                    editText5.setBackgroundColor(Color.rgb(151, 199, 119));
                    AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
                    dialog.setMessage("비밀번호가 일치합니다").setPositiveButton("확인", null).create();
                    dialog.show();
                    editText6.setEnabled(true);
                } else {
                    editText4.setBackgroundColor(Color.rgb(254, 138, 144));
                    editText5.setBackgroundColor(Color.rgb(254, 138, 144));
                    editText6.setEnabled(true);
                }
            }
        });

        editText6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    spinner.setEnabled(true);
                    editText9.setEnabled(true);
                } else {
                    spinner.setEnabled(true);
                    editText9.setEnabled(true);
                }
            }

            public void afterTextChanged(Editable s) {
                if (s.length() > 10) {
                    s.delete(10, 11);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
                    dialog.setMessage("10자리까지 입력해주세요").setPositiveButton("확인", null).create();
                    dialog.show();
                }
            }
        });

        editText9.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    checkBox.setEnabled(true);
                } else {
                    checkBox.setEnabled(true);
                }
            }

            public void afterTextChanged(Editable s) {
            }
        });

        checkBox.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    checkBox2.setEnabled(true);
                } else {
                    checkBox2.setEnabled(true);
                    checkBox3.setEnabled(true);
                    Button2.setBackgroundColor(Color.rgb(191, 191, 191));
                }
            }

        });

        checkBox2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (checkBox2.isChecked()) {
                    checkBox3.setEnabled(true);
                } else {
                    checkBox3.setEnabled(true);
                    Button2.setBackgroundColor(Color.rgb(191, 191, 191));
                }
            }

        });
        overlapButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                checkEmail2(editText3.getText().toString());
                //isExistemail(editText3.getText().toString());

            }
        });

        button4.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
           //     testFirebase = FirebaseDatabase.getInstance().getReference();

                Log.d("test!!!!!!!!!!!",Integer.toString(cnt));
               // isExistnickname(editText6.getText().toString());
               checkNick2(editText6.getText().toString());
                Log.d("test????",Integer.toString(cnt));
            }
        });

        checkBox3.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (checkBox3.isChecked()) {
                    Button2.setEnabled(true);
                    Button2.setBackgroundColor(Color.rgb(253, 93, 101));
                    Button2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            testEmail = editText3.getText().toString();
                            testPassword = editText4.getText().toString();
                            nickname = editText6.getText().toString();
                            passwordAnswer = editText9.getText().toString();
                            createUser(testEmail, testPassword);

                        }
                    });
                } else {
                    Button2.setBackgroundColor(Color.rgb(191, 191, 191));
                }
            }

        });


    }

    public void copyFirebase()
    {
        emailList.clear();
        testFirebase.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("@@@@@@@@", "!!!");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(snapshot.child("이메일").getValue()==null)
                        break;
                    emailList.add(snapshot.child("이메일").getValue().toString());
                    if(snapshot.child("닉네임").getValue()==null)
                        break;
                    nickList.add(snapshot.child("닉네임").getValue().toString());
                    if(snapshot.child("회원 코드").getValue()==null)
                        break;
                    codeList.add(snapshot.child("회원 코드").getValue().toString());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void checkEmail2(final String abc)
    {
        int i=0;
        Log.d("!!@@##",Integer.toString(emailList.size()));

        if(abc.length()==0)
            Toast.makeText(getApplicationContext(), "아이디는 빈칸일 수 없습니다", Toast.LENGTH_SHORT).show();
        while(emailList.size()!=i) {
            if(emailList.get(i).toString().compareTo(abc)==0) {
                Toast.makeText(getApplicationContext(), "사용할 수 없는 아이디입니다", Toast.LENGTH_SHORT).show();
                editText4.setEnabled(false);
            }
            i++;
        }
        Toast.makeText(getApplicationContext(), "사용 가능한 아이디입니다", Toast.LENGTH_SHORT).show();
        editText4.setEnabled(true);
    }

    public void checkNick2(final String abc)
    {
        int i=0;
        Log.d("!!@@##",Integer.toString(emailList.size()));

        if(abc.length()==0)
            Toast.makeText(getApplicationContext(), "닉네임은 빈칸일 수 없습니다", Toast.LENGTH_SHORT).show();
        while(nickList.size()!=i) {
            if(nickList.get(i).toString().compareTo(abc)==0) {
                Toast.makeText(getApplicationContext(), "사용할 수 없는 닉네임입니다", Toast.LENGTH_SHORT).show();
                editText4.setEnabled(false);
            }
            i++;
        }
        Toast.makeText(getApplicationContext(), "사용 가능한 닉네임입니다", Toast.LENGTH_SHORT).show();
        editText4.setEnabled(true);
    }
     public void checkCode(final String abc)
    {
        int i=0;
        Log.d("!!@@##",Integer.toString(emailList.size()));

        if(abc.length()==0)
            Toast.makeText(getApplicationContext(), "닉네임은 빈칸일 수 없습니다", Toast.LENGTH_SHORT).show();
        while(nickList.size()!=i) {
            if(nickList.get(i).toString().compareTo(abc)==0) {
                Toast.makeText(getApplicationContext(), "사용할 수 없는 닉네임입니다", Toast.LENGTH_SHORT).show();
                editText4.setEnabled(false);
            }
            i++;
        }
        Toast.makeText(getApplicationContext(), "사용 가능한 닉네임입니다", Toast.LENGTH_SHORT).show();
        editText4.setEnabled(true);
    }



    public String makeCode() {
        ran_num1 = (int) (Math.random() * 9999);

        ran_num2 = String.valueOf(ran_num1);


        ran_engle1 = (char) ((Math.random() * 26) + 65); // 대문자 출력, 소문자는+97
        ran_engle1_1 = (char) ((Math.random() * 26) + 65);

        ran_engle2 = String.valueOf(ran_engle1);
        ran_engle2_2 = String.valueOf(ran_engle1_1);

        String code = ran_num2 + ran_engle2 + ran_engle2_2;
        return code;
    }


    public InputFilter filterAlphaNum = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[ㄱ-ㅣ가-힣]*$");
            if (!ps.matcher(source).matches()) {
                return "";
            }
            return null;
        }
    };


    protected class CustomInputFilter implements InputFilter {
        @Override
        public CharSequence filter(CharSequence source, int start,
                                   int end, Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[a-zA-Z0-9]+$");

            if (source.equals("") || ps.matcher(source).matches()) {
                return source;
            }
            AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
            dialog.setMessage("영문, 숫자만 입력해주세요").setPositiveButton("확인", null).create();
            dialog.show();
            return "";

        }
    }

    public void createUser(final String email, final String password) {
        //    mDatabase=FirebaseDatabase.getInstance().getReference();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // currentUser = mAuth.getCurrentUser();

                           // checkCode();

                            mDatabase.child("User").child(nickname).child("이메일").setValue(testEmail);
                            mDatabase.child("User").child(nickname).child("닉네임").setValue(nickname);
                            mDatabase.child("User").child(nickname).child("비밀번호").setValue(testPassword);
                            // mDatabase.child(nickname).child("비밀번호 질문").setValue(spinner);
                            mDatabase.child("User").child(nickname).child("비밀번호 답변").setValue(passwordAnswer);

                            mDatabase.child("User").child(nickname).child("회원 코드").setValue(makeCode());
                            Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(i);
                            finish();
                            return;
                        } else {
                            Toast.makeText(getApplicationContext(), "회원가입이 실패되었습니다", Toast.LENGTH_SHORT).show();
                        }

                        finish();
                        return;
                    }

                });

    }


    public void checkCode() {
        testFirebase.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String stringCode = makeCode();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if(snapshot.child("회원 코드").getValue()==null) {

                        mDatabase.child("User").child(nickname).child("회원 코드").setValue(stringCode);
                        return;
                    }
                    String test2 = snapshot.child("회원 코드").getValue().toString();

                    if (test2.equals(stringCode)) {
                        checkCode();
                    } else {
                        if(snapshot.child("회원코드").getValue()==null)
                        mDatabase.child("User").child(nickname).child("회원 코드").setValue(stringCode);
                        return;
                    }
                }
                return;
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return;
    }




    public void isExistemail(final String abc){
        testFirebase.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                comEamil=new String[100];
                emailArray=new String[100];
                dataEamil= new String[100];
                num=2;
                Log.d("@@@@@@@@","!!!");
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    if(snapshot.child("이메일").getValue()==null) {
                        Toast.makeText(getApplicationContext(), "사용 가능한 아이디입니다", Toast.LENGTH_SHORT).show();
                        editText4.setEnabled(true);
                      return;
                    }

                    String test2 =snapshot.child("이메일").getValue().toString();
                    if(abc.length()==0)
                    {
                        Toast.makeText(getApplicationContext(), "아이디는 빈칸일 수 없습니다", Toast.LENGTH_SHORT).show();
                    }
                    else if(abc.compareTo(test2)==0)
                    {
                        if(cnt>50) {
                            num = 0;
                            return;
                        }
                        Toast.makeText(getApplicationContext(), "사용할 수 없는 아이디입니다", Toast.LENGTH_SHORT).show();
                        editText4.setEnabled(false);
                          num=1;
                        break;
                    }
                    num++;
                    cnt++;
                }
                if(num>=2)
                {
                    Toast.makeText(getApplicationContext(), "사용 가능한 아이디입니다", Toast.LENGTH_SHORT).show();
                    editText4.setEnabled(true);
                }

                Log.d("!!!!!!!!!!!!!!!!!!",Integer.toString(cnt));
                return;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return;
    }

    public void isExistnickname(final String abc){

        testFirebase.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                comNick=new String[100];
                num=2;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d("[Test]", "ValueEventListener : " + snapshot.getValue());

                    if(snapshot.child("닉네임").getValue()==null) {
                        Toast.makeText(getApplicationContext(), "사용 가능한 닉네임입니다", Toast.LENGTH_SHORT).show();
                        editText4.setEnabled(true);
                        return;
                    }

                    String test2 =snapshot.child("닉네임").getValue().toString();
                    Log.d("test",abc);
                    Log.d("test2",test2);
                    if(abc.length()==0)
                    {
                        Toast.makeText(getApplicationContext(), "닉네임은 빈칸일 수 없습니다", Toast.LENGTH_SHORT).show();
                    }
                    else if(abc.compareTo(test2)==0)
                    {
                        if(cnt>50) {
                            num = 0;
                            return;
                        }
                        Toast.makeText(getApplicationContext(), "사용할 수 없는 닉네임입니다", Toast.LENGTH_SHORT).show();
                        editText4.setEnabled(false);
                        num=1;
                        break;
                    }
                    num++;
                    cnt++;
                }
                if(num>=2)
                {
                    Toast.makeText(getApplicationContext(), "사용 가능한 닉네임입니다", Toast.LENGTH_SHORT).show();
                    editText4.setEnabled(true);
                }


                }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public class User {

        private String username;
        private String email;
        private String pass;
        private String  passAnswer;
        public User() {

        }

        public User(String passAnswer,String pass,String username, String email) {
            this.username = username;
            this.email = email;
            this.pass=pass;
            this.passAnswer=passAnswer;
        }
        public String getUsername()
        {
            return username;
        }
        public String getEmail()
        {
            return email;
        }

    }

    public boolean checkEmail(String testEmail){
        String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(testEmail);
        boolean isNormal = m.matches();
        return isNormal;
    }
}

