package com.example.bookshopkit_v2;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

public class Book {
    private static final String LL = "LightLog";
    int ID;
    DataSnapshot lastSnap;
    private String name="NoName",
            preview = "https://firebasestorage.googleapis.com/v0/b/bookshopkitv2.appspot.com/o/previews%2FEmpty.jpg?alt=media&token=9d54db17-4383-4a96-8ae2-d6ce369e1603",
            author = "NoAuthor",
            note = "no note",
            type = "free",
            file = "empty.zip";
    public Book(int ID) {
        this.ID = ID;
        DataGetThread bookLoadThread = new DataGetThread();
        bookLoadThread.start();
        do {
            Log.d(LL,"do ");
            if (bookLoadThread.isComplite()) {
                Log.d(LL, "load complite");
                break;
            }else{

            }


        }
        while (true);
        lastSnap = bookLoadThread.getLastSnap();
    }
    public int getID () {
            return ID;
        }

    public String getName(){
        if(lastSnap.child("b"+ID).hasChild("Name")) {
            name = "" + lastSnap.child("Name").getValue();
        }
        return name;

    }
    public String getPreview(){
        if(lastSnap.child("b"+ID).hasChild("Preview")) {
            preview = "" + lastSnap.child("Preview").getValue();
        }
        return preview;
    }
    public String getAuthor(){
        if(lastSnap.child("b"+ID).hasChild("Author")){
            author = ""+lastSnap.child("Author").getValue();
        }
        return author;
    }

    public String getNote(){
        if(lastSnap.child("b"+ID).hasChild("Note")){
            note = ""+lastSnap.child("Note").getValue();
        }
        return  note;
    }
    public String getType(){
        if(lastSnap.child("b"+ID).hasChild("Type")){
            type = ""+lastSnap.child("Type").getValue();
        }
        return type;
    }
    public String getFile(){
        if(lastSnap.child("b"+ID).hasChild("File")){
            file = ""+lastSnap.child("File").getValue();
        }
        return file;
    }
}
