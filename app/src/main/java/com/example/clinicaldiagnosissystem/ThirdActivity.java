package com.example.clinicaldiagnosissystem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ThirdActivity extends AppCompatActivity {

    CheckBox cb0, cb1, cb2, cb3, cb4, cb5, cb6, cb7, cb8, cb9;
    String d0, d1, d2, d3, d4, d5, d6, d7, d8, d9;
    List<String> selectedCheckBoxes;
    private FirebaseAuth firebaseAuth;
    public EditText editText;
    private ProgressDialog progressDialog;
    FirebaseDatabase database;
    DatabaseReference reference;
    Disease disease;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        reference = database.getInstance().getReference().child("disease");

        disease = new Disease();
        cb0 = findViewById(R.id.first_check);
        cb1 = findViewById(R.id.second_check);
        cb2 = findViewById(R.id.third_check);
        cb3 = findViewById(R.id.fourth_check);
        cb4 = findViewById(R.id.fifth_check);
        cb5 = findViewById(R.id.sixth_check);
        cb6 = findViewById(R.id.seventh_check);
        cb7 = findViewById(R.id.eighth_check);
        cb8 = findViewById(R.id.ninth_check);
        cb9 = findViewById(R.id.tenth_check);

        String d0 = "None";
        String d1 = "Breathing Difficulties";
        String d2 = "Sore Throat";
        String d3 = "Vomiting";
        String d4 = "Nosebleed";
        String d5 = "Abdominal Pain";
        String d6 = "Dizziness";
        String d7 = "Low Back Pain";
        String d8 = "Headache";
        String d9 = "Irregular Periods";

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if (snapshot.exists()){
                    i = (int)snapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        selectedCheckBoxes = new ArrayList<>();
        editText = findViewById(R.id.temper_text);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void onCheckBoxClickedMethod(View view) {
        switch (view.getId()) {
            case R.id.first_check:
                if (cb0.isChecked()) {
                    disease.setDis0(d0);
                    reference.child(String.valueOf(i+1)).setValue(disease);
                    selectedCheckBoxes.add("-----\n");
                } else {
                    selectedCheckBoxes.remove("-----\n");
                }
                break;

            case R.id.second_check:
                if (cb1.isChecked()) {
                    disease.setDis1(d1);
                    reference.child(String.valueOf(i+1)).setValue(disease);
                    selectedCheckBoxes.add("Asthma\n");
                } else {
                    selectedCheckBoxes.remove("Asthma\n");
                }
                break;

            case R.id.third_check:
                if (cb2.isChecked()) {
                    disease.setDis2(d2);
                    reference.child(String.valueOf(i+1)).setValue(disease);
                    selectedCheckBoxes.add("Mononucleosis\n");
                } else {
                    selectedCheckBoxes.remove("Mononucleosis\n");
                }
                break;

            case R.id.fourth_check:
                if (cb3.isChecked()) {
                    disease.setDis3(d3);
                    reference.child(String.valueOf(i+1)).setValue(disease);
                    selectedCheckBoxes.add("Viral Gastroenteritis\n");
                } else {
                    selectedCheckBoxes.remove("Viral Gastroenteritis\n");
                }
                break;

            case R.id.fifth_check:
                if (cb4.isChecked()) {
                    disease.setDis4(d4);
                    reference.child(String.valueOf(i+1)).setValue(disease);
                    selectedCheckBoxes.add("Immune Thrombocytopenia\n");
                } else {
                    selectedCheckBoxes.remove("Immune Thrombocytopenia\n");
                }
                break;

            case R.id.sixth_check:
                if (cb5.isChecked()) {
                    disease.setDis5(d5);
                    reference.child(String.valueOf(i+1)).setValue(disease);
                    selectedCheckBoxes.add("Gastroesophageal Reflux Disease\n");
                } else {
                    selectedCheckBoxes.remove("Gastroesophageal Reflux Disease\n");
                }
                break;

            case R.id.seventh_check:
                if (cb6.isChecked()) {
                    disease.setDis6(d6);
                    reference.child(String.valueOf(i+1)).setValue(disease);
                    selectedCheckBoxes.add("Multiple Sclerosis\n");
                } else {
                    selectedCheckBoxes.remove("Multiple Sclerosis\n");
                }
                break;

            case R.id.eighth_check:
                if (cb7.isChecked()) {
                    disease.setDis7(d7);
                    reference.child(String.valueOf(i+1)).setValue(disease);
                    selectedCheckBoxes.add("Pelvic Congestion Syndrome\n");
                } else {
                    selectedCheckBoxes.remove("Pelvic Congestion Syndrome\n");
                }
                break;

            case R.id.ninth_check:
                if (cb8.isChecked()) {
                    disease.setDis8(d8);
                    reference.child(String.valueOf(i+1)).setValue(disease);
                    selectedCheckBoxes.add("Meningitis (INFECTION)\n");
                } else {
                    selectedCheckBoxes.remove("Meningitis (INFECTION)\n");
                }
                break;

            case R.id.tenth_check:
                if (cb9.isChecked()) {
                    disease.setDis9(d9);
                    reference.child(String.valueOf(i+1)).setValue(disease);
                    selectedCheckBoxes.add("Polycystic Ovary Syndrome\n");
                } else {
                    selectedCheckBoxes.remove("Polycystic Ovary Syndrome\n");
                }
                break;
        }
    }

    public void onButtonClicked(View view) {
        if(TextUtils.isEmpty(editText.getText())){
            Toast.makeText(this, "Enter temperature", Toast.LENGTH_SHORT).show();
        }else if(selectedCheckBoxes.isEmpty()){
            Toast.makeText(this, "Select a symptom", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(ThirdActivity.this, ResultActivity.class);
            intent.putExtra("selected_symptom", (Serializable) selectedCheckBoxes);
            intent.putExtra("givenTemp", getTemp());
            startActivity(intent);
            overridePendingTransition(R.transition.slide_in_right, R.transition.slide_out_left);
            progressDialog.setMessage("Diagnosing, please wait!");
            progressDialog.show();
        }
    }

    private String getTemp()
    {
        String tempValue = null;
        double checkTemp = Double.parseDouble(editText.getText().toString());
        if (checkTemp <= 37.9 && checkTemp > 35.0)
            tempValue = "NORMAL ";
        else if (checkTemp <= 25.0)
            tempValue = "VERY LOW";
        else if (checkTemp <= 26.5)
            tempValue = "LOW";
        else if (checkTemp <= 35.0)
            tempValue = "HEALTHY";
        else if (checkTemp <= 39.0)
            tempValue = "RISK";
        else if (checkTemp > 39.0)
            tempValue = "HIGH RISK";

        return tempValue;
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
                startActivity(new Intent(ThirdActivity.this, MainActivity.class));
                progressDialog.setMessage("Logging out...");
                progressDialog.show();
                overridePendingTransition(R.transition.slide_in_left, R.transition.slide_out_right);
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
