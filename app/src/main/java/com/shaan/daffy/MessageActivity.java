package com.shaan.daffy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shaan.daffy.models.MessageMember;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MessageActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    ImageView imageView;
    ImageButton sendbtn, cambtn,micbtn;
    TextView username;
    EditText messageEt;
    UploadTask uploadTask;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference rootref1,rootref2;
    MessageMember messageMember;
    String receiver_name,receiver_uid,sender_uid,url;;
    String file = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+filename;

    MediaRecorder mediaRecorder;
    public static  String filename = "recorded.3gp";

    Uri uri;
    private static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            url = bundle.getString("u");
            receiver_name = bundle.getString("n");
            receiver_uid = bundle.getString("uid");
        }
        else
        {
            Toast.makeText(this, "User missing", Toast.LENGTH_SHORT).show();
        }
        messageMember = new MessageMember();
        recyclerView = findViewById(R.id.rv_message);
        cambtn = findViewById(R.id.cam_sendmessage);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MessageActivity.this));
        imageView = findViewById(R.id.iv_message);
        messageEt = findViewById(R.id.messageet);
        sendbtn = findViewById(R.id.imageButtonsend);
        username = findViewById(R.id.username_messageTvTv);
        micbtn = findViewById(R.id.btn_mic);


        Picasso.get().load(url).into(imageView);
        username.setText(receiver_name);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        sender_uid = user.getUid();

        rootref1 = database.getReference("Message").child(sender_uid).child(receiver_uid);
        rootref2 = database.getReference("Message").child(receiver_uid).child(sender_uid);
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);

        mediaRecorder.setOutputFile(file);

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SendMessage();

            }
        });
        cambtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,PICK_IMAGE);

            }
        });
       /* micbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }
        });

    }*/

  /*  private void createDialog() {

        LayoutInflater inflater = LayoutInflater.from(MessageActivity.this);
        View view = inflater.inflate(R.layout.record_layout, null);
        TextView textView = view.findViewById(R.id.tv_record_staus);
        TextView start = view.findViewById(R.id.btn_start_rc);
        TextView stop = view.findViewById(R.id.btn_stop_rc);
        TextView send = view.findViewById(R.id.btn_send_rc);


        AlertDialog alertDialog = new AlertDialog.Builder(MessageActivity.this)
                .setView(view)
                .create();

        alertDialog.show();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    mediaRecorder.prepare();
                    mediaRecorder.start();

                } catch (IOException e) {
                    e.printStackTrace();

                }
                textView.setText("Audio recording.....");

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mediaRecorder.stop();
                mediaRecorder.release();

                textView.setText("Recording Stopped");

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri audiofile = Uri.fromFile(new File(file));

                StorageReference storageReference = FirebaseStorage.getInstance().getReference("Audio files");
                final StorageReference reference = storageReference.child(System.currentTimeMillis()+ "."+filename);
                uploadTask = reference.putFile(audiofile);

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw  task.getException();
                        }

                        return reference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if (task.isSuccessful()){
                            Uri downloadUri = task.getResult();

                            Calendar cdate = Calendar.getInstance();
                            SimpleDateFormat currentdate = new SimpleDateFormat("dd-MMMM-yyyy");
                            final  String savedate = currentdate.format(cdate.getTime());

                            Calendar ctime = Calendar.getInstance();
                            SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss");
                            final String savetime = currenttime.format(ctime.getTime());


                            String time = savedate +":"+ savetime;

                            messageMember.setData(savedate);
                            messageMember.setTime(savetime);
                            messageMember.setMessage(downloadUri.toString());
                            messageMember.setReceiveruid(receiver_uid);
                            messageMember.setSenderuid(sender_uid);
                            messageMember.setType("audio");

                            String id = rootref1.push().getKey();
                            rootref1.child(id).setValue(messageMember);
                            String id1 = rootref2.push().getKey();
                            rootref2.child(id1).setValue(messageMember);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    
                                    
                                    alertDialog.dismiss();
                                    Toast.makeText(MessageActivity.this, "file sent", Toast.LENGTH_SHORT).show();

                                }
                            }, 1000);

                        }else {

                        }



                    }
                });

            }
        });*/

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICK_IMAGE || resultCode == RESULT_OK ||
                data != null || data.getData() != null) {
            uri = data.getData();

            String url = uri.toString();
            Intent intent = new Intent(MessageActivity.this, SendImage.class);
            intent.putExtra("u",url);
            intent.putExtra("n",receiver_name);
            intent.putExtra("ruid",receiver_uid);
            intent.putExtra("suid",sender_uid);
            startActivity(intent);

        }
        else{

            Toast.makeText(this, "No file selected",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<MessageMember> options1 =
                new FirebaseRecyclerOptions.Builder<MessageMember>()
                        .setQuery(rootref1,MessageMember.class)
                        .build();
        FirebaseRecyclerAdapter<MessageMember, MessageViewHolder> firebaseRecyclerAdapter1 =
                new FirebaseRecyclerAdapter<MessageMember, MessageViewHolder>(options1) {
                    @Override
                    protected void onBindViewHolder(@NonNull MessageViewHolder holder, int position, @NonNull MessageMember model) {

                    holder.Setmessage(getApplication(),model.getMessage(),model.getTime(),model.getData(),model.getType(),model.getSenderuid(),model.getReceiveruid());


                    String message = getItem(position).getMessage();
                    holder.playsender.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MediaPlayer mediaPlayer = new MediaPlayer();

                            holder.playsender.setImageResource(R.drawable.ic_baseline_pause_white);
                            try {
                                mediaPlayer.setDataSource(message);
                                mediaPlayer.prepare();
                                mediaPlayer.start();
                                holder.playsender.setClickable(false);
                                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mediaPlayer) {
                                        holder.playsender.setImageResource(R.drawable.ic_baseline_white);
                                        mediaPlayer.stop();
                                        holder.playsender.setClickable(true);
                                    }
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                        holder.playreceiver.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MediaPlayer mediaPlayer = new MediaPlayer();

                                holder.playreceiver.setImageResource(R.drawable.ic_baseline_pause_black);
                                try {
                                    mediaPlayer.setDataSource(message);
                                    mediaPlayer.prepare();
                                    mediaPlayer.start();
                                    holder.playreceiver.setClickable(false);
                                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                        @Override
                                        public void onCompletion(MediaPlayer mediaPlayer) {
                                            holder.playreceiver.setImageResource(R.drawable.ic_baseline_black);
                                            mediaPlayer.stop();
                                            holder.playreceiver.setClickable(true);
                                        }
                                    });
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        });

                    }

                    @NonNull
                    @Override
                    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.message_layout,parent,false);

                        return new MessageViewHolder(view);
                    }
                };


        firebaseRecyclerAdapter1.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter1);

    }

    private  void SendMessage()
    {


        String message = messageEt.getText().toString();

        Calendar cdate = Calendar.getInstance();
        SimpleDateFormat currentdate = new SimpleDateFormat("dd-MMMM-yyyy");
        final  String savedate = currentdate.format(cdate.getTime());

        Calendar ctime = Calendar.getInstance();
        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss");
        final String savetime = currenttime.format(ctime.getTime());


        String time = savedate +":"+ savetime;

        if (message.isEmpty())

        {

            Toast.makeText(this, "Cannot send empty message", Toast.LENGTH_SHORT).show();
        }
        else
        {
            messageMember.setData(savedate);
            messageMember.setTime(savetime);
            messageMember.setMessage(message);
            messageMember.setReceiveruid(receiver_uid);
            messageMember.setSenderuid(sender_uid);
            messageMember.setType("text");

            String id = rootref1.push().getKey();
            rootref1.child(id).setValue(messageMember);
            String id1 = rootref2.push().getKey();
            rootref2.child(id1).setValue(messageMember);

            messageEt.setText("");


        }
    }
}