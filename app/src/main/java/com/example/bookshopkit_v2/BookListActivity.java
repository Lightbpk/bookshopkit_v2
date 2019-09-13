package com.example.bookshopkit_v2;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class BookListActivity extends Activity {
final String LL ="LightLog";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //---------полноэкран----------
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_book_list);
        //------------------------------
        Book book = new Book(1);
        Log.d(LL,"id "+book.getID()+"; name "+book.getName()+"; file "+book.getFile());
        Log.d(LL," Auth "+book.getAuthor()+"Note "+book.getNote()+" type "+book.getType());
    }


}
