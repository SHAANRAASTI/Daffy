package com.shaan.daffy;

import android.app.Application;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shaan.daffy.R;

public class MessageViewHolder extends RecyclerView.ViewHolder {

    TextView senderTv, receiverTv;

    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);
    }
    public  void Setmessage(Application application, String message, String time, String data, String type, String senderuid, String receiveruid) {

        senderTv = itemView.findViewById(R.id.sender_tv);
        receiverTv = itemView.findViewById(R.id.receiver_tv);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUid = user.getUid();


        if (currentUid.equals(senderuid))
        {
            receiverTv.setVisibility(View.GONE);
            senderTv.setText(message);
        }
      else if (currentUid.equals(receiveruid)) {
            senderTv.setVisibility(View.GONE);
            receiverTv.setText(message);

        }
    }}




