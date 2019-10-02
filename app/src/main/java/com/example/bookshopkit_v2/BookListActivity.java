package com.example.bookshopkit_v2;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BookListActivity extends Activity  {
final String LL ="LightLog";
int num_books_v = 3;
int num_books_h = 3;
int num_books_visible = num_books_h * num_books_v;
int startID = 0;
TableLayout book_list_lay;
ImageView[] booksImageViews = new ImageView[num_books_visible];
Book[] books = new Book[num_books_visible];
ImageView[] imageViews = new ImageView[num_books_visible];
TextView tw;
Book book;
Handler h;

final int STATUS_OK = 1;
final int STATUS_NON = 0;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //---------Fullscreen activity----------
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_book_list);
        //------------------------------
        book_list_lay = findViewById(R.id.book_list_lay);
        tw = findViewById(R.id.tw);
        bookListUpdate();
        h = new Handler(){
            public void handleMessage(android.os.Message msg){

                if(msg.what == STATUS_OK){
                    tw.setText(book.getName());
                    tw.invalidate();
                    Log.d(LL,"STATUS_OK"+" "+book.getName());
                }else if(msg.what == STATUS_NON){
                    tw.setText("nononon");
                    tw.invalidate();
                    Log.d(LL,"STATUS_NON");
                }else Log.d(LL,"STATUS_undefine");

            }
        };
        h.sendEmptyMessage(STATUS_NON);
    }
    public void bookListUpdate(){

        book = new Book(0);
        WaitData waitdata = new WaitData();
        waitdata.start();
      /*for(int i = 0 ; i < num_books_v ; i++){
           TableRow row = new TableRow(this);
           for(int j =0 ; j < num_books_h ; j++){
               books[startID] =  new Book(startID);
               imageViews[startID].setImageBitmap(books[startID].getPreviewBitmap(this));
               row.addView(imageViews[startID]);
           }
           book_list_lay.addView(row);
       }*/
        //book_list_lay.addView();
    }

    public void onDrawBook(String text){


    }
    class WaitData extends Thread{
        @Override public void run() {
            while (!book.loadComplite){
            Log.d(LL,"Loading");
            }

            h.sendEmptyMessage(STATUS_OK);
        }
    }


}
