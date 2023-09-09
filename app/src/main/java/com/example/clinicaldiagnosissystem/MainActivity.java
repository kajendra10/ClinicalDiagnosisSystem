package com.example.clinicaldiagnosissystem;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends Activity {

    TextView textView;
    private EditText Name;
    private EditText Password;
    private Button Login;
    private TextView Info;
    private int counter = 5;
    private FirebaseAuth firebaseAuth;
    private TextView forgotPassword;
    private TextView register_text;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText)findViewById(R.id.c_username);
        Password = (EditText)findViewById(R.id.c_password);
        Login = (Button)findViewById(R.id.bt_login);
        Info = (TextView)findViewById(R.id.l_attempt);
        forgotPassword = (TextView)findViewById(R.id.resetpassword);
        register_text = (TextView)findViewById(R.id.registerText);

        Info.setText("Attempts remaining: 5");

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null){
            finish();
            startActivity(new Intent(MainActivity.this, ThirdActivity.class));
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(Name.getText()) || TextUtils.isEmpty(Password.getText())) {
                    Toast.makeText(MainActivity.this, "Enter email & password.", Toast.LENGTH_SHORT).show();
                } else {
                    validate(Name.getText().toString(), Password.getText().toString());
                }
            }

        });


        register_text.setPaintFlags(register_text.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);
        register_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
                overridePendingTransition(R.transition.slide_in_right, R.transition.slide_out_left);

            }
        });

        forgotPassword.setPaintFlags(forgotPassword.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PasswordActivity.class));
                overridePendingTransition(R.transition.slide_in_right, R.transition.slide_out_left);
            }
        });
    }

    private void validate(final String userNAME, final String userPASSWORD){

        progressDialog.setMessage("Your email is being verified, please wait!");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userNAME, userPASSWORD).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    checkEmailVerification();
                }else{
                    Toast.makeText(MainActivity.this, "Login failed! Check your email or password.", Toast.LENGTH_SHORT).show();
                    counter--;
                    Info.setText("Attempts remaining: " + counter);
                    progressDialog.dismiss();
                    if(counter == 0){
                        Login.setEnabled(false);
                    }
                }
            }
        });
    }


    private void checkEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailspot = firebaseUser.isEmailVerified();

        if(emailspot){
            finish();
            startActivity(new Intent(MainActivity.this, ThirdActivity.class));
            overridePendingTransition(R.transition.slide_in_right, R.transition.slide_out_left);
        }else{
            Toast.makeText(this, "Verify your email.", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }

}