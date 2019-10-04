package com.example.bookshopkit_v2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Book {
    private static final String LL = "LightLog";
    private FirebaseDatabase database = FirebaseDatabase. getInstance ();
    DatabaseReference booksRef = database.getReference ( "booksBase" );
    private int ID;
    private DataSnapshot lastBookBaseSnap;
    volatile boolean loadComplite= false;
    private String name="NoName",
            previewUrl = "https://firebasestorage.googleapis.com/v0/b/bookshopkitv2.appspot.com/o/previews%2FEmpty.jpg?alt=media&token=9d54db17-4383-4a96-8ae2-d6ce369e1603",
            author = "NoAuthor",
            note = "no note",
            type = "free",
            file = "empty.zip";
    Context context;

    public Book(int ID) {
        this.ID = ID;

        booksRef.child("b"+ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lastBookBaseSnap = dataSnapshot;
                Log.d(LL,"Snap granted");
                loadComplite = true;

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(LL,"cancel update");
                loadComplite = false;
            }
        }) ;
        /*try {
            fireget.join();
            Log.d(LL,"Waiting 1sec");
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d(LL,"Interrupt exception");
        }*/
    }
    class Fireget extends Thread{

        @Override public void run() {

        }
        boolean isComplite(){
            return loadComplite;
        }
    }

    public int getID () {
            return ID;
        }

    public String getName(){
        if(lastBookBaseSnap.hasChild("Name")) {
            name = "" + lastBookBaseSnap.child("Name").getValue();
            Log.d(LL,"");
        }
        return name;

    }
    public String getPreviewUrl(){
        if(lastBookBaseSnap.hasChild("Preview")){
            previewUrl = ""+ lastBookBaseSnap.child("Preview").getValue();
        }
        return previewUrl;

    }
    public Bitmap getPreviewBitmap(Context context){
        Bitmap bm = null;

        this.context = context;
        if(lastBookBaseSnap != null) {
            if (lastBookBaseSnap.child("b0").hasChild("Preview")) {
                previewUrl = "" + lastBookBaseSnap.child("Preview").getValue();
                try {
                    URL aURL = new URL(previewUrl);
                    URLConnection conn = aURL.openConnection();
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(is);
                    bm = BitmapFactory.decodeStream(bis);
                    bis.close();
                    is.close();
                } catch (IOException e) {
                    Log.d(LL, "Error getting bitmap", e);
                }
            }
        }else bm = BitmapFactory.decodeResource(context.getResources(),R.drawable.empty
                );
        return bm;
    }

    public String getAuthor(){
        if(lastBookBaseSnap.hasChild("Author")){
            author = ""+ lastBookBaseSnap.child("Author").getValue();
        }
        return author;
    }

    public String getNote(){
        if(lastBookBaseSnap.hasChild("Note")){
            note = ""+ lastBookBaseSnap.child("Note").getValue();
        }
        return  note;
    }
    public String getType(){
        if(lastBookBaseSnap.hasChild("Type")){
            type = ""+ lastBookBaseSnap.child("Type").getValue();
        }
        return type;
    }
    public String getFile(){
        if(lastBookBaseSnap.hasChild("File")){
            file = ""+ lastBookBaseSnap.child("File").getValue();
        }
        return file;
    }
}
