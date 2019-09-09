package com.example.instagramclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.Objects;

public class SignUp extends AppCompatActivity implements View.OnClickListener{
    //UI Components
    private ImageView imageView;
    private EditText email;
    private EditText username;
    private EditText password;
    private Button signup;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        setTitle("Sign Up");

        imageView=findViewById(R.id.imageView);
        email=findViewById(R.id.email);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);

        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    if(i==KeyEvent.KEYCODE_ENTER && keyEvent.getAction()==KeyEvent.ACTION_DOWN)
                    {
                        onClick(signup);

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
//            ParseUser.logOut();
            transitiontoSocialMedia();
        }
    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.signup: {
                if (email.getText().toString().equals("") ||
                        username.getText().toString().equals("") ||
                        password.getText().toString().equals("")) {
                    FancyToast.makeText(SignUp.this, "Email , Username , Password is required!!!", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();

                } else {


                    final ParseUser appUser = new ParseUser();
                    appUser.setEmail(email.getText().toString());
                    appUser.setUsername(username.getText().toString());
                    appUser.setPassword(password.getText().toString());

                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Signing Up " + username.getText().toString());
                    progressDialog.show();

                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(SignUp.this, appUser.getUsername() + " is signed up", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                                transitiontoSocialMedia();
                            } else {
                                FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

                            }

                            progressDialog.dismiss();
                        }
                    });

                    break;
                }
            }
            case R.id.login:
            {
                Intent intent=new Intent(SignUp.this,Login.class);
                startActivity(intent);

                break;
            }


        }
}

public void LayoutTapped(View view)
{
    try {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        final boolean b = inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus())
                .getWindowToken(), 0);
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
}
public void transitiontoSocialMedia()
{
    Intent intent=new Intent(SignUp.this,SocialMediaActivity.class);
    startActivity(intent);
    finish();

}

}
