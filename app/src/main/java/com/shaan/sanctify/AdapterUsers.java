package com.shaan.sanctify;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterUsers extends FirebaseRecyclerAdapter<ModelUser,AdapterUsers. myviewholder> {


        public AdapterUsers(@NonNull FirebaseRecyclerOptions<ModelUser> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull final ModelUser model) {
            holder.nameIv.setText(model.getName());
            holder.emailIv.setText(model.getEmail());

            Picasso.get().load(model.getUrl()).placeholder(R.drawable.ic_default_img).into(holder.img1);

            /*holder.img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatActivity activity=(AppCompatActivity)view.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new descfragment(model.getName(),model.getCourse(),model.getEmail(),model.getPurl())).addToBackStack(null).commit();
                }
            });*/
        }

        @NonNull
        @Override
        public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_users,parent,false);
            return new myviewholder(view);
        }

        public class myviewholder extends RecyclerView.ViewHolder
        {
            ImageView img1;
            TextView nameIv,emailIv;

            public myviewholder(@NonNull View itemView) {
                super(itemView);

                img1=itemView.findViewById(R.id.avatarIv);
                nameIv=itemView.findViewById(R.id.nameIv);

                emailIv=itemView.findViewById(R.id.emailIv);
            }
        }

    }
