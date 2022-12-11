package com.example.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.pojo.Member;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

//intent切換在切換方(ActivityMain)設計，而bundle的部分這邊會再寫東西來取得
enum FormMode {
    LOGIN, REGISTER
}
public class MainActivity extends AppCompatActivity {
    Activity context = this;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    FormMode mode = FormMode.LOGIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (Objects.nonNull(user) && !user.getEmail().isEmpty()) {
            gotoNextIndent(user);
        }
        setContentView(R.layout.activity_main);
        Button btnAction = findViewById(R.id.btnAction);
        Button btnToggleLogin = findViewById(R.id.toggleLogin);
        Button btnToggleRegister = findViewById(R.id.toggleRegister);
        toggleLogin();
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionEvent();
            }
        });

        btnToggleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLogin();
            }
        });

        btnToggleRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleRegister();
            }
        });

    }

    //註冊
    private void register(String email, String password) {
        if (!isEmailAndPasswordValid(email, password)) {
            displayErrorText("Email 或 密碼格式錯誤");
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    try {
                        throw Objects.requireNonNull(task.getException());
                    } catch (FirebaseAuthUserCollisionException exception) {
                        displayErrorText("該Email已被註冊。");
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        displayErrorText("未知的錯誤");
                        return;
                    }
                }
                Member member = new Member();
                member.setName(email.substring(0, email.indexOf("@")));
                member.setLevel(0);
                db.collection("Member")
                        .document(Objects.requireNonNull(task.getResult().getUser()).getUid())
                        .set(member);
                login (email, password);
            }
        });

    }

    //登入
    private void login(String email, String password) {
        if (!isEmailAndPasswordValid(email, password)) {
            displayErrorText("Email 或 密碼格式錯誤");
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    try {
                        throw Objects.requireNonNull(task.getException());
                    } catch (FirebaseAuthInvalidCredentialsException exception) {
                        displayErrorText("帳號或密碼錯誤。");
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        displayErrorText("未知的錯誤");
                        return;
                    }
                }

                FirebaseUser user = mAuth.getCurrentUser();
                if (Objects.isNull(user)) {
                    displayErrorText("找不到該使用者");
                    return;
                }
                gotoNextIndent(user);
            }
        });

    }

    //登入或註冊失敗時顯示錯誤訊息
    private void displayErrorText(String errorMsg) {
        TextView tvFail = (TextView) findViewById(R.id.tvfail);
        tvFail.setText(errorMsg);
    }

    //驗證email及密碼是否符合規範
    private boolean isEmailAndPasswordValid(String email, String password) {
        return Objects.nonNull(email) && Objects.nonNull(password) && !email.isEmpty() && !password.isEmpty() && password.length() >= 6;
    }

    public void actionEvent() {
        EditText txtEmail = (EditText) findViewById(R.id.textAddress);
        EditText txtPassword = (EditText) findViewById(R.id.txtPassword);
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        switch (mode) {
            case LOGIN:
                login(email, password);
                break;
            case REGISTER:
                register(email, password);
                break;
        }
    }

    public void toggleRegister() {
        mode = FormMode.REGISTER;
        Button btnAction = findViewById(R.id.btnAction);
        TextView mainText = findViewById(R.id.mainText);
        mainText.setText("請輸入註冊資訊：");
        btnAction.setText("註冊");
    }

    public void toggleLogin() {
        mode = FormMode.LOGIN;
        Button btnAction = findViewById(R.id.btnAction);
        TextView mainText = findViewById(R.id.mainText);
        mainText.setText("請輸入登入資訊：");
        btnAction.setText("登入");
    }

    private void gotoNextIndent(FirebaseUser user) {
        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("email", Objects.requireNonNull(user).getEmail());
        startActivity(intent);
    }
}