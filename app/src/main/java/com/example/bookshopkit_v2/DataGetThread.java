package com.example.bookshopkit_v2;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DataGetThread extends Thread {
    private static final String LL = "LightLog";
    FirebaseDatabase database = FirebaseDatabase. getInstance ();
    DatabaseReference booksRef = database.getReference ( "booksBase" );
    boolean loadComplite = false;
    @NonNull DataSnapshot lastSnap ;
    @Override public void run(){
        Log.d(LL,"DataGetThread start");
       booksRef.addValueEventListener(new ValueEventListener() {git
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lastSnap = dataSnapshot;
                Log.d(LL,"Snap granted");
                loadComplite = true;
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {
               Log.d(LL,"cancel update");
               loadComplite = false;
           }
       }) ;
    }

    @NonNull
    public DataSnapshot getLastSnap() {
        return lastSnap;
    }
    public boolean getComplite(){
        return loadComplite;
    }
}
