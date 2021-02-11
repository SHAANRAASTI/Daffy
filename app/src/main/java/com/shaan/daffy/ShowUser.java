package com.shaan.daffy;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
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
import com.shaan.daffy.models.RequestMember;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ShowUser extends AppCompatActivity {



    TextView nametv,professiontv,biotv,emailtv,websitetv,requesttv;
    ImageView imageView;
    FirebaseDatabase database, databaseprofileshow,databaseprofileshow1;
    DatabaseReference databaseReference,databaseReference1,databaseReference2,postnoref,requestref,databaseReferenceprofileshow,databaseReferenceprofileshow1;
    TextView button,followers_tv,posts_tv;
    CardView followers_cv,posts_cd;
    String url,name,age,email,privacy,p,website,bio,userid;
    RequestMember requestMember;
    String name_result;
    String uidreq,namereq,urlreq,professionreq;

    int postNo ;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference,documentReference1;

    int followercount ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user);


        databaseprofileshow = FirebaseDatabase.getInstance();
        databaseprofileshow1 = FirebaseDatabase.getInstance();
        database = FirebaseDatabase.getInstance();

        requestMember = new RequestMember();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId = user.getUid();


        nametv = findViewById(R.id.name_tv_showprofile);
        professiontv = findViewById(R.id.age_tv_showprofile);
        biotv = findViewById(R.id.bio_tv_showprofile);
        emailtv = findViewById(R.id.email_tv_showProfile);
        imageView = findViewById(R.id.imageView_showprofile);
        websitetv = findViewById(R.id.website_tv_showprofile);
        button = findViewById(R.id.btn_requestshowprofile);
        requesttv = findViewById(R.id.tv_requestshowprofile);

        followers_tv = findViewById(R.id.followerNo_tv);
        posts_tv = findViewById(R.id.postsNo_tv);
        followers_cv = findViewById(R.id.followers_cardview);
        posts_cd =findViewById(R.id.followers_cardview);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            url = extras.getString("u");
            name = extras.getString("n");
            userid = extras.getString("uid");
        }else {
         //   Toast.makeText(this, "Privact account", Toast.LENGTH_SHORT).show();
        }

        databaseReference = database.getReference("Requests").child(userid);
        databaseReference1 = database.getReference("followers").child(userid);
        databaseReferenceprofileshow = databaseprofileshow.getReference("All Users").child(userid);
        databaseReferenceprofileshow1 = databaseprofileshow1.getReference("All Users").child(currentUserId);
       // documentReference = db.collection("user").document(userid);

        postnoref = database.getReference("User Posts").child(userid);
         databaseReference2  = database.getReference("followers");
       //documentReference1 = db.collection("user").document(currentUserId);


        postnoref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postNo = (int)snapshot.getChildrenCount();
                posts_tv.setText(Integer.toString(postNo));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String status = button.getText().toString();
                if (status.equals("Follow")){
                    follow();
                }else if (status.equals("Requested")){
                    delRequest();
                }else if (status.equals("Following")){
                    unFollow();
                }

            }
        });

    }

    private void delRequest() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId = user.getUid();
        databaseReference.child(currentUserId).removeValue();
        button.setText("Follow ");
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId = user.getUid();

        databaseReferenceprofileshow.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                            String name_result = dataSnapshot.child("name").getValue().toString();
                            String age_result = dataSnapshot.child("prof").getValue().toString();

                            String bio_result = dataSnapshot.child("bio").getValue().toString();
                            String email_result = dataSnapshot.child("email").getValue().toString();
                            String web_result = dataSnapshot.child("web").getValue().toString();
                            String Url = dataSnapshot.child("url").getValue().toString();
                            p = dataSnapshot.child("privacy").getValue().toString();


                               if (p.equals("Public")){
                                   professiontv.setText(bio_result);
                                   nametv.setText(name_result);
                                   biotv.setText(age_result);
                                   emailtv.setText(email_result);
                                   websitetv.setText(web_result);
                                   Picasso.get().load(Url).into(imageView);
                                   requesttv.setVisibility(View.GONE);
                               }else {

                                   String u = button.getText().toString();
                                   if (u.equals("Following")){
                                       professiontv.setText(bio_result);
                                       nametv.setText(name_result);
                                       biotv.setText(age_result);
                                       emailtv.setText(email_result);
                                       websitetv.setText(web_result);
                                       Picasso.get().load(Url).into(imageView);
                                       requesttv.setVisibility(View.GONE);
                                   }else {
                                       professiontv.setText("*****************");
                                       nametv.setText(name_result);
                                       biotv.setText("*****************");
                                       emailtv.setText("*****************");
                                       websitetv.setText("*****************");
                                       Picasso.get().load(Url).into(imageView);
                                       requesttv.setVisibility(View.VISIBLE);
                                   }

                               }





                        }else {
                            Toast.makeText(ShowUser.this, "No Profile exist", Toast.LENGTH_SHORT).show();
                        }
                    }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        databaseReferenceprofileshow1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                        if (dataSnapshot.exists()){
                            namereq = dataSnapshot.child("name").getValue().toString();
                            professionreq = dataSnapshot.child("prof").getValue().toString();
                            urlreq = dataSnapshot.child("url").getValue().toString();


                        }else {
                          //  Toast.makeText(ShowUser.this, "No Profile exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // refernce for following
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                  followercount = (int)snapshot.getChildrenCount();
                  followers_tv.setText(Integer.toString(followercount));


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.hasChild(currentUserId)){
                 button.setText("Requested");

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(userid).hasChild(currentUserId)){
                    button.setText("Following");
                }else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    void follow() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId = user.getUid();


        if (p.equals("Public")){
            button.setText("Following");
            requestMember.setUserid(currentUserId);
            requestMember.setProfession(professionreq);
            requestMember.setUrl(urlreq);
            requestMember.setName(namereq);

            databaseReference1.child(currentUserId).setValue(requestMember);
        }else {

            button.setText("Requested");
            requestMember.setUserid(currentUserId);
            requestMember.setProfession(professionreq);
            requestMember.setUrl(urlreq);
            requestMember.setName(namereq);
            databaseReference.child(currentUserId).setValue(requestMember);
            requesttv.setText("Wait Until your request is accepted");

        }
    }

    private void unFollow() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId = user.getUid();

        AlertDialog.Builder builder = new AlertDialog.Builder(ShowUser.this);
        builder.setTitle("Unfollow")
                .setMessage("Are you sure to Unfollow?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databaseReference1.child(currentUserId).removeValue();
                        button.setText("Follow");
                        followers_tv.setText("0");
                        Toast.makeText(ShowUser.this, "Unfollowed", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        builder.create();
        builder.show();
    }


}

