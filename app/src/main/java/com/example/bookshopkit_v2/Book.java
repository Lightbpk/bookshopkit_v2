package com.example.bookshopkit_v2;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Book {
    private static final String LL = "LightLog";
    private FirebaseDatabase database = FirebaseDatabase. getInstance ();
    DatabaseReference booksRef = database.getReference ( "booksBase" );
    private int ID;
    private boolean loadComplite= false;
    private DataSnapshot lastSnap;
    private String name="NoName",
            preview = "https://firebasestorage.googleapis.com/v0/b/bookshopkitv2.appspot.com/o/previews%2FEmpty.jpg?alt=media&token=9d54db17-4383-4a96-8ae2-d6ce369e1603",
            author = "NoAuthor",
            note = "no note",
            type = "free",
            file = "empty.zip";
    public Book(int ID) {
        this.ID = ID;
        booksRef.child("b"+ID).addValueEventListener(new ValueEventListener() {
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
    public int getID () {
            return ID;
        }
    public boolean isLoadComplite(){
        return loadComplite;
    }

    public String getName(){
        if(lastSnap.hasChild("Name")) {
            name = "" + lastSnap.child("Name").getValue();
        }
        return name;

    }
    public String getPreview(){
        if(lastSnap.hasChild("Preview")) {
            preview = "" + lastSnap.child("Preview").getValue();
        }
        return preview;
    }
    public String getAuthor(){
        if(lastSnap.hasChild("Author")){
            author = ""+lastSnap.child("Author").getValue();
        }
        return author;
    }

    public String getNote(){
        if(lastSnap.hasChild("Note")){
            note = ""+lastSnap.child("Note").getValue();
        }
        return  note;
    }
    public String getType(){
        if(lastSnap.hasChild("Type")){
            type = ""+lastSnap.child("Type").getValue();
        }
        return type;
    }
    public String getFile(){
        if(lastSnap.hasChild("File")){
            file = ""+lastSnap.child("File").getValue();
        }
        return file;
    }
}
