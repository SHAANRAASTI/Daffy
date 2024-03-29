package com.shaan.daffy;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class AboutUs extends AppCompatActivity {
    ImageButton insta;
    ImageButton facebook;
    ImageButton linkedin;
    Uri Instagram = Uri.parse("https://www.instagram.com/shaanraasti/");
    Uri Facebook = Uri.parse("https://www.facebook.com/Daffy-106641428129173/");
    Uri LinkedIn = Uri.parse("https://www.linkedin.com/in/shaan-raasti-b23b9a15b/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_about_us);

        //hide toolbar
        getSupportActionBar().hide();

        insta = (ImageButton)findViewById(R.id.insta);
        facebook = (ImageButton)findViewById(R.id.facebook);
        linkedin = (ImageButton)findViewById(R.id.mail);

        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(Intent.ACTION_VIEW,Instagram);
                i.setPackage("com.instagram.android");
                try {
                    startActivity(i);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.instagram.com/shaanraasti/")));
                }
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(Intent.ACTION_VIEW,Facebook);
                i.setPackage("com.facebook.android");
                try {
                    startActivity(i);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.facebook.com/Daffy-106641428129173/")));
                }
            }
        });
        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(Intent.ACTION_VIEW,LinkedIn);
                i.setPackage("com.linkedin.android");
                try {
                    startActivity(i);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.linkedin.com/in/shaan-raasti-b23b9a15b/")));
                }
            }
        });
    }
}