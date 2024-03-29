package com.example.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.Adapter.UserAdapter;
import com.example.instagram.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FollowerActivity extends AppCompatActivity {
    String id ;
    String title;

    private List<String>  idList;
     RecyclerView rvFollowers;
     UserAdapter userAdapter;
     List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        title = intent.getStringExtra("title");



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rvFollowers = findViewById(R.id.rvFollowers);
        rvFollowers.setHasFixedSize(true);
        rvFollowers.setLayoutManager(new LinearLayoutManager(this));
        userList = new ArrayList<>();
        userAdapter = new UserAdapter(this,userList);
        rvFollowers.setAdapter(userAdapter);
        idList = new ArrayList<>();
        switch (title){
            case "likes":
                getLikes();
                break;
            case "following":
                getFollowing();
                break;
            case "follower":
                getFollower();
                break;
            case "views":
                getViews();
                break;


        }

    }
    private void getViews(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Story")
                .child(id).child(getIntent().getStringExtra("storyid")).child("views");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                idList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren() ){
                    idList.add(dataSnapshot.getKey());
                }
                showUser();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getFollowing() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Follow").child(id).child("following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                idList.clear();
                for (DataSnapshot  dataSnapshot: snapshot.getChildren()){
                    idList.add(dataSnapshot.getKey());
                }
                showUser();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getFollower() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Follow").child(id).child("followers");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                idList.clear();
                for (DataSnapshot  dataSnapshot: snapshot.getChildren()){
                    idList.add(dataSnapshot.getKey());
                }
                System.out.println("follower"+idList);
                showUser();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getLikes() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Likes").child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                idList.clear();
                for (DataSnapshot  dataSnapshot: snapshot.getChildren()){
                    idList.add(dataSnapshot.getKey());
                }
                System.out.println("Likes"+idList);
                showUser();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void showUser(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    for (String id : idList ){
                        if (user.getId().equals(id)){
                            userList.add(user);
                        }
                    }

                }
                userAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}