package com.shaan.sanctify;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
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
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class Fragment1 extends Fragment implements View.OnClickListener{

    ImageView imageView;
    TextView nameEt,profEt,bioEt,emailEt,webEt;
    ImageButton imageButtonEdit,imageButtonMenu;
    DocumentReference reference;
    FirebaseFirestore firestore;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    String url;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment1,container,false);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        firestore = FirebaseFirestore.getInstance();
        //reference = firestore.collection("user").document(userid);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("All Users");


        imageView = getActivity().findViewById(R.id.iv_f1);
        nameEt = getActivity().findViewById(R.id.tv_name_f1);
        profEt = getActivity().findViewById(R.id.tv_prof_f1);
        bioEt = getActivity().findViewById(R.id.tv_bio_f1);
        emailEt = getActivity().findViewById(R.id.tv_email_f1);
        webEt = getActivity().findViewById(R.id.tv_web_f1);

        imageButtonEdit = getActivity().findViewById(R.id.ib_edit_f1);
        imageButtonMenu = getActivity().findViewById(R.id.ib_menu_f1);


        imageButtonMenu.setOnClickListener(this);
        imageButtonEdit.setOnClickListener(this);
        imageView.setOnClickListener(this);
        webEt.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ib_edit_f1:
                Intent intent = new Intent(getActivity(),UpdateProfile.class);
                startActivity(intent);
                break;
            case R.id.ib_menu_f1:
                BottomSheetMenu bottomSheetMenu = new BottomSheetMenu();
                bottomSheetMenu.show(getFragmentManager(),"bottomsheet");

                break;
            case R.id.iv_f1:
                Intent intent1 = new Intent(getActivity(),ImageActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_web_f1:
                try {
                    String url = webEt.getText().toString();
                    Intent intent2 = new Intent(Intent.ACTION_VIEW);
                    intent2.setData(Uri.parse(url));
                    startActivity(intent2);
                }catch (Exception e){
                    Toast.makeText(getActivity(), "Invalid Url", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();

        databaseReference.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    String nameResult = dataSnapshot.child("name").getValue().toString();
                    String bioResult = dataSnapshot.child("bio").getValue().toString();
                    String emailResult = dataSnapshot.child("email").getValue().toString();
                    String webResult = dataSnapshot.child("web").getValue().toString();
                    url = dataSnapshot.child("url").getValue().toString();
                    String profResult = dataSnapshot.child("prof").getValue().toString();

                    Picasso.get().load(url).into(imageView);
                    nameEt.setText(nameResult);
                    bioEt.setText(bioResult);
                    emailEt.setText(emailResult);
                    webEt.setText(webResult);
                    profEt.setText(profResult);



                }
                else {
                    Intent intent = new Intent(getActivity(), CreateProfile.class);
                    startActivity(intent);
                }



            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Failed to read value.", error.toException());
            }
        });


    }


    private void checkUserStatus()

    {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null)
        {
            //user is signed  is stay here
            //set email of logged in user

        }
        else
        {
            //user not signed in to main activity
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }
    }





}
