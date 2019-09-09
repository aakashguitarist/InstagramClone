package com.example.instagramclone;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersTab extends Fragment implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {

    private ListView listView;
    private ArrayList<String> arrayList;
    private ArrayAdapter arrayAdapter;
    public UsersTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_users_tab, container, false);

        listView=view.findViewById(R.id.listView);
        arrayList=new ArrayList();
        arrayAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,arrayList);

        listView.setOnItemClickListener((AdapterView.OnItemClickListener) UsersTab.this);
        listView.setOnItemLongClickListener(UsersTab.this);


        final TextView textView=view.findViewById(R.id.textView);


        ParseQuery<ParseUser> parseQuery=ParseUser.getQuery();
        parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().toString());

        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if (e == null) {
                    if (users.size() > 0) {
                        for (ParseUser user : users) {

                        arrayList.add(user.getUsername());
                        }

                        listView.setAdapter(arrayAdapter);
                        textView.animate().alpha(0).setDuration(2000);
                        listView.setVisibility(View.VISIBLE);
                    }

                }
            }
        });
        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent(getContext(), Users_posts.class);
        intent.putExtra("username",arrayList.get(i));
        startActivity(intent);
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        ParseQuery<ParseUser> parseQuery= ParseUser.getQuery();
        parseQuery.whereEqualTo("username",arrayList.get(i));
        parseQuery.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser user, ParseException e) {

                if(user!=null && e==null)
                {
                    final PrettyDialog prettyDialog=new PrettyDialog(getContext());
                            prettyDialog.setTitle(user.getUsername()+" 's Info")
                                    .setMessage(user.get("profileBio")+ "\n"
                                            + user.get("profileProfession") +"\n"
                                            + user.get("profileHobbies") +"\n"+
                                            user.get("profileFavSport")).setIcon(R.drawable.person)
                                    .addButton(
                                            "OK",   //button text
                                            R.color.pdlg_color_white, //button text color
                                            R.color.pdlg_color_green, //button background color
                                            new PrettyDialogCallback() {
                                                @Override
                                                public void onClick()
                                                {
                                                    prettyDialog.dismiss();

                                                }
                                            }
                                    ).show();
                }
            }
        });

        return true;
    }
}
