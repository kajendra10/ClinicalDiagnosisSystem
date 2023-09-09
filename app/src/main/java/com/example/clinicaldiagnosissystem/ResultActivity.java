package com.example.clinicaldiagnosissystem;
import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button button;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        button = findViewById(R.id.re_dg);
        progressDialog = new ProgressDialog(this);


        Intent intent = getIntent();
        List<String> list = (List<String>) intent.getSerializableExtra("selected_symptom");
        String givenTemp = intent.getStringExtra("givenTemp");

        TextView textView = findViewById(R.id.disease_view);
        TextView health_view = findViewById(R.id.health_view);
        String str = "";
        for(String s : list){
            str+=s+"\n";
        }
        textView.setText(str);
        health_view.setText(givenTemp);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.logoutmenu:{
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(ResultActivity.this, MainActivity.class));
                overridePendingTransition(R.transition.slide_in_left, R.transition.slide_out_right);
                progressDialog.setMessage("Logging out...");
                progressDialog.show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void onButtonClicked(View view) {

        startActivity(new Intent(ResultActivity.this, ThirdActivity.class));
        overridePendingTransition(R.transition.slide_in_left, R.transition.slide_out_right);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }
}
