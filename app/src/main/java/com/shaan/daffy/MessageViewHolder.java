package com.shaan.daffy;

import android.app.Application;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shaan.daffy.R;
import com.squareup.picasso.Picasso;

public class MessageViewHolder extends RecyclerView.ViewHolder {

    TextView senderTv, receiverTv;
    ImageView iv_sender,iv_receiver;
    ImageButton playsender,playreceiver;

    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);
    }
    public  void Setmessage(Application application, String message, String time, String data, String type, String senderuid, String receiveruid) {

        senderTv = itemView.findViewById(R.id.sender_tv);
        receiverTv = itemView.findViewById(R.id.receiver_tv);
        playreceiver = itemView.findViewById(R.id.play_message_receiver);
        playsender = itemView.findViewById(R.id.play_message_sender);
        LinearLayout llsender = itemView.findViewById(R.id.llsender);
        LinearLayout llreceiver = itemView.findViewById(R.id.llreceiver);



        iv_receiver = itemView.findViewById(R.id.iv_receiver);
        iv_sender = itemView.findViewById(R.id.iv_sender);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUid = user.getUid();

        if (type.equals("text"))
        {
            if (currentUid.equals(senderuid))
            {
                receiverTv.setVisibility(View.GONE);
                senderTv.setText(message);
            }
            else if (currentUid.equals(receiveruid)) {
                senderTv.setVisibility(View.GONE);
                receiverTv.setText(message);

            }
        }
        else if(type.equals("iv"))
        {
            if (currentUid.equals(senderuid))
            {

            receiverTv.setVisibility(View.GONE);
            senderTv.setVisibility(View.GONE);
            iv_sender.setVisibility(View.VISIBLE);
            Picasso.get().load(message).into(iv_sender);

            }
            else if(currentUid.equals(receiveruid))
            {
                receiverTv.setVisibility(View.GONE);
                senderTv.setVisibility(View.GONE);
                iv_receiver.setVisibility(View.VISIBLE);
                Picasso.get().load(message).into(iv_receiver);


            }

        } else if (type.equals("audio"))
        {

            if (currentUid.equals(senderuid))
            {
                senderTv.setVisibility(View.GONE);
                receiverTv.setVisibility(View.GONE);
                llsender.setVisibility(View.VISIBLE);
                llreceiver.setVisibility(View.GONE);


            }
            else if(currentUid.equals(receiveruid))
            {

                senderTv.setVisibility(View.GONE);
                receiverTv.setVisibility(View.GONE);
                llsender.setVisibility(View.GONE);
                llreceiver.setVisibility(View.VISIBLE);

            }




        }

    }
}




