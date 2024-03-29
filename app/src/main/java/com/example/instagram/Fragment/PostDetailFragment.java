package com.example.instagram.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instagram.Model.Post;
import com.example.instagram.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import com.example.instagram.Adapter.PostAdapter;


public class PostDetailFragment extends Fragment {

    String postid;
    RecyclerView rvPhotoDetail;
    PostAdapter postAdapter;
    List<Post> postList;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_detail, container, false);
        SharedPreferences sharedPreferences= getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        postid = sharedPreferences.getString("postid","none");


        rvPhotoDetail = view.findViewById(R.id.rvPhotoDetail);
        rvPhotoDetail.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvPhotoDetail.setLayoutManager(linearLayoutManager);
        postList = new ArrayList<>();
        postAdapter = new PostAdapter(getContext(),postList);
        rvPhotoDetail.setAdapter(postAdapter);

        readPosts();

        return view;
    }

    private void readPosts() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts").child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                Post post = snapshot.getValue(Post.class);
                postList.add(post);
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}