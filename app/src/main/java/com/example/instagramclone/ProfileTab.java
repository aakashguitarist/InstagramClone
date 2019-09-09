package com.example.instagramclone;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTab extends Fragment {

    private EditText edtProfileName,edtProfileBio,edtProfileProfession,edtProfileHobby,edtProfileSport;
    private Button updateInfo;

    public ProfileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile_tab, container, false);
        edtProfileName=view.findViewById(R.id.editText);
        edtProfileBio=view.findViewById(R.id.editText2);
        edtProfileProfession=view.findViewById(R.id.editText3);
        edtProfileHobby=view.findViewById(R.id.editText4);
        edtProfileSport=view.findViewById(R.id.editText5);
        updateInfo=view.findViewById(R.id.button);

        final ParseUser appuser= ParseUser.getCurrentUser();

        if(appuser.get("ProfileName")==null)
            edtProfileName.setText("");
        else
            edtProfileName.setText(appuser.get("ProfileName")+"");

        if(appuser.get("ProfileBio")==null)
            edtProfileBio.setText("");
        else
            edtProfileBio.setText(appuser.get("ProfileBio")+"");

        if(appuser.get("ProfileProfession")==null)
            edtProfileProfession.setText("");
        else
            edtProfileProfession.setText(appuser.get("ProfileProfession")+"");

        if(appuser.get("ProfileHobby")==null)
            edtProfileHobby.setText("");
        else
            edtProfileHobby.setText(appuser.get("ProfileHobby")+"");

        if(appuser.get("ProfileSport")==null)
            edtProfileSport.setText("");
        else
            edtProfileSport.setText(appuser.get("ProfileSport")+"");


        updateInfo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        appuser.put("ProfileName", edtProfileName.getText().toString());
                        appuser.put("ProfileBio", edtProfileBio.getText().toString());
                        appuser.put("ProfileProfession", edtProfileProfession.getText().toString());
                        appuser.put("ProfileHobby", edtProfileHobby.getText().toString());
                        appuser.put("ProfileSport", edtProfileSport.getText().toString());

                        appuser.saveEventually(new SaveCallback() {
                                                   @Override
                                                   public void done(ParseException e) {

                                                       if (e == null) {
                                                           FancyToast.makeText(getContext(), "Info Updated", FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();

                                                       } else {
                                                           FancyToast.makeText(getContext(), e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();


                                                       }
                                                   }

                                               }
                        );


                    }


                });


        return view;
    }
}
