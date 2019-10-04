package com.example.bookshopkit_v2;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class BookListActivity extends Activity  {
final String LL ="LightLog";
int BOOK_VIEW_V = 3;
int BOOK_VIEW_H = 3;
int BOOK_VIEW = BOOK_VIEW_H * BOOK_VIEW_V;
int startID = 0;
TableLayout book_list_lay;
TableRow firstrow;
ImageView[] booksImageViews = new ImageView[BOOK_VIEW];
TextView[] textViews = new TextView[BOOK_VIEW];
TextView tw;

//ArrayList booksAL = new ArrayList();
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
        firstrow = findViewById(R.id.firstRow);
        tw = findViewById(R.id.tw);
        bookListUpdate(startID);
        //-----------Draw Book-------------
        h = new Handler(){
            public void handleMessage(android.os.Message msg){
                if(msg.what == STATUS_OK){
                    Book currentBook = (Book)msg.obj;
                    textViews[currentBook.getID()]= new TextView(BookListActivity.this);
                    textViews[currentBook.getID()].setText(currentBook.getName());
                    firstrow.addView(textViews[currentBook.getID()]);
                    firstrow.invalidate();
                    Log.d(LL,"STATUS_OK"+" "+currentBook.getName());
                }else if(msg.what == STATUS_NON){
                    tw.setText("nononon");
                    tw.invalidate();
                    Log.d(LL,"STATUS_NON");
                }else Log.d(LL,"STATUS_undefine");
            }
        };
        //------------------------------
        h.sendEmptyMessage(STATUS_NON);
    }
    public void bookListUpdate(int startID){
        for(int i = 0; i < BOOK_VIEW; i++){
            Book currentBook = new Book(i+startID);
            WaitData waitdata = new WaitData(currentBook);
            waitdata.start();
        }


      /*for(int i = 0 ; i < BOOK_VIEW_V ; i++){
           TableRow row = new TableRow(this);
           for(int j =0 ; j < BOOK_VIEW_H ; j++){
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
        Book currentBook;
        public WaitData(Book currentBook){
            this.currentBook = currentBook;
        }
        @Override public void run() {
            Message msg;
            while (!currentBook.loadComplite){
            //Log.d(LL,"Loading");
            }
            msg = h.obtainMessage(STATUS_OK,currentBook);
            h.sendMessage(msg);
        }
    }
}
