package com.shaan.daffy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

public class BottomSheetMenu extends BottomSheetDialogFragment {


    FirebaseFirestore db = FirebaseFirestore.getInstance();


   DatabaseReference reference;
   // DocumentReference reference ;
    CardView cv_privacy,cv_logout,cv_delete;
    FirebaseAuth mAuth;
    DatabaseReference df;
    FirebaseUser mCurrentUser;
    String url,name,currentid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = getLayoutInflater().inflate(R.layout.bottom_sheet_menu,null);


       //cv_delete = view.findViewById(R.id.cv_delete);
       cv_logout = view.findViewById(R.id.cv_logout);
       cv_privacy = view.findViewById(R.id.cv_privacy);
       mAuth = FirebaseAuth.getInstance();





        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentid= user.getUid();

        reference = FirebaseDatabase.getInstance().getReference("All Users");
      // reference = db.collection("user").document(currentid);

             reference.child(currentid).addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                     if (dataSnapshot.exists()) {
                           url = dataSnapshot.child("url").toString();

                       }else {

                         Toast.makeText(getActivity(), "Url not exist", Toast.LENGTH_SHORT).show();

                       }
                   }

                 @Override
                 public void onCancelled(@NonNull DatabaseError error) {

                 }
             });


       mCurrentUser = mAuth.getCurrentUser();


       cv_logout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               logout();
           }
       });
       cv_privacy.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               startActivity(new Intent(getActivity(),PrivacyActivity.class));
           }
       });
       /*cv_delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {


               AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
               builder.setTitle("Delete Profile")
                       .setMessage("Are you sure to delete")
                       .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {

                                       Query query = reference.orderByChild("uid").equalTo(currentid);
                                       query.addListenerForSingleValueEvent(new ValueEventListener() {
                                           @Override
                                           public void onDataChange(@NonNull DataSnapshot snapshot) {
                                               for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                                   dataSnapshot.getRef().removeValue();
                                                   Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                                               }
                                           }

                                           @Override
                                           public void onCancelled(@NonNull DatabaseError error) {

                                           }


                                       });


                               StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(url);
                               ref.delete()
                                       .addOnSuccessListener(new OnSuccessListener<Void>() {
                                           @Override
                                           public void onSuccess(Void aVoid) {


                                               Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                                           }
                                       });



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
       });*/



       return view;
    }

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Logout")
                .setMessage("Are you sure to logout")
                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        mAuth.signOut();
                        startActivity(new Intent(getActivity(),LoginActivity.class));
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
