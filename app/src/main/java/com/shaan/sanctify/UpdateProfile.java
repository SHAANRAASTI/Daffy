package com.shaan.sanctify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class UpdateProfile extends AppCompatActivity {
    EditText etname,etBio,etProfession,etEmail,etWeb;
    Button button;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
  //  DocumentReference documentReference ;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        databaseReference = database.getReference("All Users");

        etBio = findViewById(R.id.et_bio_up);
        etEmail = findViewById(R.id.et_email_up);
        etname = findViewById(R.id.et_name_up);
        etProfession = findViewById(R.id.et_profession_up);
        etWeb = findViewById(R.id.et_web_up);
        button = findViewById(R.id.btn_up);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();


        user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();
//read user table
        databaseReference.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    String nameResult = dataSnapshot.child("name").getValue().toString();
                    String bioResult = dataSnapshot.child("bio").getValue().toString();
                    String emailResult = dataSnapshot.child("email").getValue().toString();
                    String webResult = dataSnapshot.child("web").getValue().toString();
                    String url = dataSnapshot.child("url").getValue().toString();
                    String profResult = dataSnapshot.child("prof").getValue().toString();


                            etname.setText(nameResult);
                            etBio.setText(bioResult);
                            etEmail.setText(emailResult);
                            etWeb.setText(webResult);
                            etProfession.setText(profResult);

                        }else {
                            Toast.makeText(UpdateProfile.this, "No profile ", Toast.LENGTH_SHORT).show();
                        }

                    }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void updateProfile() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();

        final String name = etname.getText().toString();
        final String bio = etBio.getText().toString();
        final String prof = etProfession.getText().toString();
        final String web = etWeb.getText().toString();
        final String email =etEmail.getText().toString();

        HashMap hashMap = new HashMap();
        hashMap.put("name", name);
        hashMap.put("bio", bio);
        hashMap.put("prof", prof);
        hashMap.put("web", web);
        hashMap.put("email", email);

databaseReference.child(userid).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
    @Override
    public void onSuccess(Object o) {

        Toast.makeText(UpdateProfile.this, "Updated Successfully",Toast.LENGTH_SHORT).show();
    }
}).addOnFailureListener(new OnFailureListener() {
    @Override
    public void onFailure(@NonNull Exception e) {
        Toast.makeText(UpdateProfile.this, "Failed"+e.getMessage(),Toast.LENGTH_SHORT).show();
    }
});

    }


}