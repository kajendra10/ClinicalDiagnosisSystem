package com.example.clinicaldiagnosissystem;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SecondActivity extends Activity {

    TextView textView;
    private EditText userEmail, userName, userPassword, userSex;
    private Button regButton;
    private FirebaseAuth firebaseAuth;
    String Email, name, password, sex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    //Upload data to database (firebase)
                    final String user_email = userEmail.getText().toString().trim();
                    String pass_word = userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, pass_word).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                sendEmailVerification();
                                sendUserData();
                                Toast.makeText(SecondActivity.this, "Successfully registered! Verification email sent.", Toast.LENGTH_SHORT).show();
                                overridePendingTransition(R.transition.slide_in_left, R.transition.slide_out_right);
                                finish();
                            }else{
                                Toast.makeText(SecondActivity.this, "Registration failed! Check your details.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });

        textView=(TextView)findViewById(R.id.loginText);
        textView.setPaintFlags(textView.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();

            }
        });

    }


    public void openMainActivity() {
        startActivity(new Intent(SecondActivity.this, MainActivity.class));
        overridePendingTransition(R.transition.slide_in_left, R.transition.slide_out_right);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.transition.slide_in_left, R.transition.slide_out_right);
    }

    private void setupUIViews() {
        userEmail = (EditText)findViewById(R.id.email);
        userName = (EditText)findViewById(R.id.username);
        userPassword = (EditText)findViewById(R.id.password);
        regButton = (Button)findViewById(R.id.bt_login);
        userSex = (EditText)findViewById(R.id.yoursex);
    }


    private Boolean validate() {
        Boolean result = false;

        Email = userEmail.getText().toString();
        name = userName.getText().toString();
        password = userPassword.getText().toString();
        sex = userSex.getText().toString();

        if(TextUtils.isEmpty(Email)){
            userEmail.setError("Email required");
        }

        if(TextUtils.isEmpty(name)){
            userName.setError("Username required");
        }

        if(TextUtils.isEmpty(password)){
            userPassword.setError("Password required");
        }

        if(password.length() < 5){
            userPassword.setError("Password should contain minimum 5 characters");
        }

        if(TextUtils.isEmpty(sex)){
            userSex.setError("Sex required");
        }

        if(Email.isEmpty() || name.isEmpty() || password.isEmpty() || sex.isEmpty()){
            Toast.makeText(this, "Please enter all the details.", Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }
        return result;
    }

    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        sendUserData();
                        Toast.makeText(SecondActivity.this,"Successfully registered! Verification email sent.", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        startActivity(new Intent(SecondActivity.this, MainActivity.class));
                        finish();

                    }else{
                        Toast.makeText(SecondActivity.this, "Verification email not sent.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        UserProfile userProfile = new UserProfile(Email, sex, name);
        myRef.setValue(userProfile);
    }

}