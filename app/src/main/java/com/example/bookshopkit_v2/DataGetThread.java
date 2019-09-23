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
    private FirebaseDatabase database = FirebaseDatabase. getInstance ();
    private DatabaseReference booksRef = database.getReference ( "booksBase" );
    private volatile DataSnapshot lastSnap = null ;
    private volatile boolean loadComplite = false;

    DataSnapshot getLastSnap() {
        return lastSnap;
    }
    boolean isComplite(){
        return loadComplite;
    }
public DataGetThread(){

}
    @Override public void run(){
        Log.d(LL,"DataGetThread start");
        Log.d(LL,"bookRef "+booksRef);
           booksRef.addValueEventListener(new ValueEventListener() {
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

}
