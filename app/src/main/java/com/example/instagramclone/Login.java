package com.example.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class Login extends AppCompatActivity implements View.OnClickListener {
    //UI Components
    private ImageView imageView;
    private EditText email;
    private EditText password;
    private Button signup;
    private Button login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        setTitle("Log In");
        imageView=findViewById(R.id.imageView);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i==KeyEvent.KEYCODE_ENTER && keyEvent.getAction()==KeyEvent.ACTION_DOWN)
                {
                    onClick(login);

                }
                return false;
            }
        });
        signup=findViewById(R.id.signup);
        login=findViewById(R.id.login);

        signup.setOnClickListener(this);
        login.setOnClickListener(this);


        if(ParseUser.getCurrentUser()!=null)
        {
            ParseUser.logOut();
            transitiontoSocialMedia();
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.signup:
            {
                Intent intent=new Intent(Login.this,SignUp.class);
                startActivity(intent);

                break;
            }
            case R.id.login: {
                if (email.getText().toString().equals("") ||
                        password.getText().toString().equals("")) {
                    FancyToast.makeText(Login.this, "Email , Username , Password is required!!!", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();

                } else {
                    ParseUser.logInInBackground(email.getText().toString(), password.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (e == null && user != null) {
                                FancyToast.makeText(Login.this, user.getUsername() + " is logged in Successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                                transitiontoSocialMedia();
                            } else {
                                FancyToast.makeText(Login.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

                            }
                        }
                    });

                    break;
                }
            }
        }

    }


    public void LayoutTapped(View view)
    {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void transitiontoSocialMedia()
    {
        Intent intent=new Intent(Login.this,SocialMediaActivity.class);
        startActivity(intent);

    }
}
