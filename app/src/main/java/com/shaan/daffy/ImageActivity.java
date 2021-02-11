package com.shaan.daffy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class ImageActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    Button btnEdit,btnDel;
    DocumentReference reference;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    String url;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        btnDel = findViewById(R.id.btn_del_iv);
        btnEdit = findViewById(R.id.btn_edit_iv);
        imageView = findViewById(R.id.iv_expand);
        textView = findViewById(R.id.tv_name_image);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentid = user.getUid();


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("All Users");
        //reference = db.collection("user").document(currentid);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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

                    String name = dataSnapshot.child("name").getValue().toString();
                    url = dataSnapshot.child("url").getValue().toString();

                    Picasso.get().load(url).into(imageView);
                    textView.setText(name);
                } else {
                    Toast.makeText(ImageActivity.this, "No profile", Toast.LENGTH_SHORT).show();
                }
            }


        @Override
        public void onCancelled (@NonNull DatabaseError error){

        }
    });

}}