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
int currentID = 0;
TableLayout book_list_lay;
TableRow firstRow;
ImageView[] booksImageViews = new ImageView[BOOK_VIEW];
TextView[] textViews = new TextView[BOOK_VIEW];
TableRow[] tableRows;
TextView tw;

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
        tableRows = new TableRow[BOOK_VIEW_V];

        for(int i = 0 ; i < BOOK_VIEW_H ; i++){
            tableRows[i] = new TableRow(this);
            for(int j =currentID ; j < currentID + BOOK_VIEW_V ; j++){
                tableRows[i].addView(textViews[j]= new TextView(this));
            }
            currentID = currentID + BOOK_VIEW_V;
            book_list_lay.addView(tableRows[i]);
        }
        bookListUpdate(startID);
        //-----------Draw Book-------------
        h = new Handler(){
            public void handleMessage(android.os.Message msg){
                if(msg.what == STATUS_OK){
                    Book currentBook = (Book)msg.obj;
                    textViews[msg.arg1].setText(currentBook.getName());
                    textViews[msg.arg1].invalidate();
                    Log.d(LL,"STATUS_OK"+" "+currentBook.getName());
                }else if(msg.what == STATUS_NON){
                    Log.d(LL,"STATUS_NON");
                }else Log.d(LL,"STATUS_undefine");
            }
        };
        //------------------------------
        h.sendEmptyMessage(STATUS_NON);
    }
    public void bookListUpdate(int startID){
        for(int i = 0; i < BOOK_VIEW; i++){
            int current_img_num= i;
            Book currentBook = new Book(i+startID);
            WaitData waitdata = new WaitData(currentBook, current_img_num);
                waitdata.start();
        }
    }

    class WaitData extends Thread{
        Book currentBook;
        int current_img_num;
        public WaitData(Book currentBook,int current_img_num){
            this.currentBook = currentBook;
            this.current_img_num = current_img_num;
        }
        @Override public void run() {
            Message msg;
            while (!currentBook.loadComplite){
            //Log.d(LL,"Loading");
            }
            msg = h.obtainMessage(STATUS_OK,current_img_num,0,currentBook);
            h.sendMessage(msg);
        }
    }
}
