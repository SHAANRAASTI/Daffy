package com.shaan.sanctify;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class PrivacyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {


    String[] status = {"Choose any one","Public","Private"};

    TextView status_tv;
    Spinner spinner;
    Button button;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference reference;
    DatabaseReference databaseReference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        button = findViewById(R.id.btn_privacy);
        status_tv = findViewById(R.id.tv_status);
        spinner = findViewById(R.id.spinner);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentid = user.getUid();
       // reference = db.collection("user").document(currentid);
        databaseReference = database.getReference("All Users");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,status);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePrivacy();
            }
        });

    }




    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

        Toast.makeText(this, "Please Select a value", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();
        databaseReference.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    if (dataSnapshot.exists()) {
                        String privacy_result = dataSnapshot.child("privacy").getValue().toString();
                        status_tv.setText(privacy_result);
                    } else {
                        Toast.makeText(PrivacyActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        }


    private void savePrivacy() {

        HashMap hashMap = new HashMap();
        user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();
        final String value = spinner.getSelectedItem().toString();
        if (value == "Choose any one") {
            Toast.makeText(this, "Please Select a value", Toast.LENGTH_SHORT).show();
        } else {

            hashMap.put("privacy", value);
        }
        databaseReference.child(userid).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(PrivacyActivity.this, "Status Updated", Toast.LENGTH_SHORT).show();
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PrivacyActivity.this, "There is an error", Toast.LENGTH_SHORT).show();
            }
        });

        }


}





