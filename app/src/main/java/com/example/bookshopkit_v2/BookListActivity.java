package com.example.bookshopkit_v2;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.HashMap;

public class BookListActivity extends Activity  {
final String LL ="LightLog";
int BOOK_VIEW_V = 2;
int BOOK_VIEW_H = 2;
int BOOK_VIEW = BOOK_VIEW_H * BOOK_VIEW_V;
int startID = 0;
int currentID = 0;
TableLayout book_list_lay;
ImageView[] booksImageViews;
TextView[] textViews;
TableRow[] tableRows;

Handler h;

final int STATUS_OK = 1;
final int STATUS_NON = 0;
final int STATUS_BITMAP = 2;

private static final int SWIPE_MIN_DISTANCE = 120;
private static final int SWIPE_THRESHOLD_VELOCITY = 200;
GestureDetector gdt;

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
        //=============init books template==========================
        booksImageViews = new ImageView[BOOK_VIEW];
        textViews = new TextView[BOOK_VIEW];
        book_list_lay = findViewById(R.id.book_list_lay);
        tableRows = new TableRow[BOOK_VIEW_V];
        TableRow.LayoutParams lp = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final HashMap<Integer, Book> booksMap = new HashMap<>();
        for(int i = 0 ; i < BOOK_VIEW_H ; i++){
            tableRows[i] = new TableRow(this);
            tableRows[i].setLayoutParams(lp);
            Log.d(LL,"Add new Row");
            Log.d(LL,"i = " +i);
            for(int j =currentID ; j < currentID + BOOK_VIEW_V ; j++){
                tableRows[i].addView(booksImageViews[j] = new ImageView(this));
                booksImageViews[j].setImageDrawable(getResources().getDrawable(R.drawable.empty));
                tableRows[i].addView(textViews[j]= new TextView(this));
                Log.d(LL,"j = " +j);
            }
            currentID = currentID + BOOK_VIEW_V;
            book_list_lay.addView(tableRows[i]);
            tableRows[i].invalidate();
        }
        for(int k = 0; k < BOOK_VIEW; k++){
            Log.d(LL,"ImageView "+ k + " x "+booksImageViews[k].getX() + " y "+ booksImageViews[k].getY());
        }
        //=================================================
        bookListUpdate(startID);
        //-----------Draw Book-------------
        h = new Handler(){
            public void handleMessage(android.os.Message msg){
                if(msg.what == STATUS_OK){
                    Log.d(LL,"STATUS_OK");
                    final Book currentBook = (Book)msg.obj;
                    textViews[msg.arg1].setText(currentBook.getName());
                    textViews[msg.arg1].invalidate();
                    booksMap.put(msg.arg1,currentBook);
                    /*booksImageViews[msg.arg1].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d(LL,"Book "+currentBook.getName()+" clicked");
                        }
                    });*/
                    //Log.d(LL,"STATUS_OK"+" "+currentBook.getName());
                }else if(msg.what == STATUS_NON){
                    Log.d(LL,"STATUS_NON");
                }else if(msg.what == STATUS_BITMAP){
                    booksImageViews[msg.arg1].setImageBitmap((Bitmap) msg.obj);
                    booksImageViews[msg.arg1].invalidate();
                    Log.d(LL,"Book "+msg.arg1+ " x "+ booksImageViews[msg.arg1].getX()+ " y "+ booksImageViews[msg.arg1].getY());

                }
                else Log.d(LL,"STATUS_undefine");
            }
        };
        //------------------------------
        h.sendEmptyMessage(STATUS_NON);
        gdt = new GestureDetector(this,new GestureListener());
        /*book_list_lay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gdt.onTouchEvent(event);
                return true;
            }
        });*/
    }
    public void bookListUpdate(int startID){
        for(int i = 0; i < BOOK_VIEW; i++){
            int current_img_num= i;
            Book currentBook = new Book(i+startID);
            WaitDataThread waitdatathread = new WaitDataThread(currentBook, current_img_num);
                waitdatathread.start();
        }
    }

    class WaitDataThread extends Thread{
        Book currentBook;
        int current_img_num;
        public WaitDataThread(Book currentBook, int current_img_num){
            this.currentBook = currentBook;
            this.current_img_num = current_img_num;
        }
        @Override public void run() {
            Message msg,msg2;
            while (!currentBook.loadComplite){
            //Log.d(LL,"Loading");
            }
            msg = h.obtainMessage(STATUS_OK,current_img_num,0,currentBook);
            h.sendMessage(msg);
            msg2 = h.obtainMessage(STATUS_BITMAP,current_img_num,0,currentBook.getPreviewBitmap());
            h.sendMessage(msg2);
        }
    }
    //=======================touch interface====================
    @Override
    public boolean onTouchEvent(MotionEvent event){
        gdt.onTouchEvent(event);
        return true;
    }
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                Log.d(LL,"To the Left");
                startID = startID - BOOK_VIEW;
                if(startID < 0){
                    startID = 0;
                }
                bookListUpdate(startID);
                return false; // справа налево
            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                Log.d(LL,"To the Right");
                bookListUpdate(startID + BOOK_VIEW);
                return false; // слева направо
            } else if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY){
                Log.d(LL,"To the Up");
                startID = startID - BOOK_VIEW;
                if(startID < 0){
                    startID = 0;
                }
                bookListUpdate(startID);
                return false;
            } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY){
                bookListUpdate(startID + BOOK_VIEW);
                Log.d(LL,"To the Down");
                return false;
            }
            else Log.d(LL,"Err swipe");
            Log.d(LL,"shnopt");
            return false;
        }
        @Override
        public boolean onSingleTapUp(MotionEvent e){

            for(int i =0 ;i < BOOK_VIEW ; i++){
                if (e.getX() > booksImageViews[i].getX() && e.getX() < (booksImageViews[i].getX() + booksImageViews[i].getWidth()) ){
                    if(e.getY() > booksImageViews[i].getY() && e.getY() < (booksImageViews[i].getY()+ booksImageViews[i].getHeight())){
                        Log.d(LL,"book "+i +" clicked");
                        Log.d(LL," bX "+booksImageViews[i].getX());
                        Log.d(LL," bX +W "+(booksImageViews[i].getX() + booksImageViews[i].getWidth()));
                        Log.d(LL," bY "+booksImageViews[i].getY());
                        Log.d(LL," bY +H "+(booksImageViews[i].getY()+ booksImageViews[i].getHeight()));
                    }
                }
                Log.d(LL,"");
            }
            Log.d(LL,"Single Tap");
            Log.d(LL," X "+e.getX());
            Log.d(LL," Y "+e.getY());



            return super.onSingleTapUp(e);
        }
    }

}
