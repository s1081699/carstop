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

//intent切換在切換方(ActivityMain)設計，而bundle的部分這邊會再寫東西來取得
public class MainActivity5 extends AppCompatActivity {
    Activity context=this;
    Button btedit;
    EditText txtemail,txtpassword;
    String email;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page6);
        Button btedit = findViewById(R.id.btedit);
        EditText txtemail = (EditText)findViewById(R.id.txtemail);
        EditText txtpassword = (EditText)findViewById(R.id.txtpassword);
        TextView tvfail = (TextView)findViewById(R.id.tvfail);
        mAuth = FirebaseAuth.getInstance();
        btedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.createUserWithEmailAndPassword(txtemail.getText().toString(),txtpassword.getText().toString()).addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user=mAuth.getCurrentUser();
                            Intent intent = new Intent(MainActivity5.this,MainActivity1.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("email",email);
                            startActivity(intent);
                        } else {
                            tvfail.setText("修改失敗" + task.getException());
                        }
                    }
                });
            }
        });
    }
}