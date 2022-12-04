package com.example.myapplication;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;

//intent切換在切換方(ActivityMain)設計，而bundle的部分這邊會再寫東西來取得
public class MainActivity extends AppCompatActivity {
    Activity context=this;
    Button btlogin,btregister;
    EditText txtemail,txtpassword;
    TextView tvfail;
    String email;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btlogin = findViewById(R.id.btlogin);
        Button btregister = findViewById(R.id.btregister);
        EditText txtemail = (EditText)findViewById(R.id.txtemail);
        EditText txtpassword = (EditText)findViewById(R.id.txtpassword);
        TextView tvfail = (TextView)findViewById(R.id.tvfail);
        mAuth = FirebaseAuth.getInstance();

        btregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.createUserWithEmailAndPassword(txtemail.getText().toString(),txtpassword.getText().toString()).addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user=mAuth.getCurrentUser();
                            Intent intent = new Intent(MainActivity.this,MainActivity1.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("email",email);
                            startActivity(intent);
                        } else {
                            tvfail.setText("註冊失敗" + task.getException());
                        }
                    }
                });
            }
        });
        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signInWithEmailAndPassword(txtemail.getText().toString(),txtpassword.getText().toString()).addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user=mAuth.getCurrentUser();
                            email=user.getEmail();
                            Intent intent = new Intent(MainActivity.this,MainActivity1.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("email",email);
                            startActivity(intent);
                        } else {
                            tvfail.setText("登入失敗" + task.getException());
                        }
                    }
                });
            }
        });
    }
}