package com.example.instagramclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class Users_posts extends AppCompatActivity {
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_posts);


        linearLayout=findViewById(R.id.linearLayout);
        Intent receivedIntentObject=getIntent();
        final String receivedUsername=receivedIntentObject.getStringExtra("username");
        FancyToast.makeText(Users_posts.this,receivedUsername, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();

        setTitle(receivedUsername+"'s Posts");

        ParseQuery<ParseObject> parseQuery=new ParseQuery<ParseObject>("Photo");
        parseQuery.whereEqualTo("username",receivedIntentObject);
        parseQuery.orderByDescending("createdAt");

        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e)
            {
                if(objects.size()>0 && e==null) {
                    for (ParseObject post : objects) {
                        final TextView postDescription = new TextView(Users_posts.this);
                        postDescription.setText(post.get("image_des")+"");
                        ParseFile postPicture= (ParseFile) post.get("picture");

                        postPicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if(data!=null && e==null)
                                {
                                    Bitmap bitmap= BitmapFactory.decodeByteArray(data,0,data.length);
                                    ImageView postImageView=new ImageView(Users_posts.this);
                                    LinearLayout.LayoutParams image_params=new LinearLayout.LayoutParams
                                            (ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT);
                                    image_params.setMargins(4,4,4,4);
                                    postImageView.setLayoutParams(image_params);
                                    postImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    postImageView.setImageBitmap(bitmap);

                                    postDescription.setLayoutParams(image_params);
                                    postDescription.setGravity(Gravity.CENTER);
                                    postDescription.setBackgroundColor(Color.BLUE);
                                    postDescription.setTextColor(Color.WHITE);
                                    postDescription.setTextSize(30f);


                                    linearLayout.addView(postImageView);
                                    linearLayout.addView(postDescription);
                                }
                            }
                        });



                    }
                }
                else
                {
                    FancyToast.makeText(Users_posts.this,receivedUsername+" has no image available!!!", Toast.LENGTH_LONG,FancyToast.INFO,true).show();
                    finish();
                }
            dialog.dismiss();
            }
        });

    }


}
