package com.example.clinicaldiagnosissystem;
import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordActivity extends Activity {

    private EditText passwordEM;
    private Button resetPassword;
    private FirebaseAuth firebaseAuth;
    private TextView back_login;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        passwordEM = (EditText)findViewById(R.id.emailpassword);
        resetPassword = (Button)findViewById(R.id.bt_resetpass);
        back_login = (TextView)findViewById(R.id.back_loginText);
        firebaseAuth = FirebaseAuth.getInstance();

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail = passwordEM.getText().toString().trim();

                if(useremail.equals("")){
                    Toast.makeText(PasswordActivity.this, "Please enter your email.", Toast.LENGTH_SHORT).show();
                }else{
                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(PasswordActivity.this, "Password reset email sent.", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(PasswordActivity.this, MainActivity.class));
                                overridePendingTransition(R.transition.slide_in_left, R.transition.slide_out_right);
                           }else{
                                Toast.makeText(PasswordActivity.this, "Invalid email.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });

        back_login.setPaintFlags(back_login.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);
        back_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PasswordActivity.this, MainActivity.class));
                overridePendingTransition(R.transition.slide_in_left, R.transition.slide_out_right);
            }
        });

    }


}