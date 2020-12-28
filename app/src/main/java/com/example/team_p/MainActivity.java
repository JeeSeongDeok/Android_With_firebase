package com.example.team_p;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

import static com.example.team_p.AutoCompleteActivity.COUNTRIES;
public class MainActivity extends AppCompatActivity {
    // 변수 선언
    private static Toast toast;
    private AutoCompleteTextView user_search;
    private Button btn;
    private ImageButton sell_btn, asus_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //각 버튼 변수 및 유저서치 변수 연결
        this.InitializeView();
        //어댑터 연결 후 자동 연결
        this.AutoComplete();
        //리스너 설정
        this.SetListener();
    }


    // Toast가 뭐고? ㅋㅋ;
    public static void showToast(Context context, String message){
        if(toast == null){
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }
        else{
            toast.setText(message);
        }
        toast.show();
    }
    //EditText포커스로 인한 키보드 내리기
    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
    //배경 터치시 키보드 내리기
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
    // user_search 어댑터 연결 및 자동완성기능
    public void AutoComplete(){
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        this.user_search.setAdapter(adapter1);
    }
    // user_search 및 Btn 변수 연결
    public void InitializeView(){
        user_search = (AutoCompleteTextView) findViewById(R.id.user_serach);
        btn = (Button) findViewById(R.id.Button1);
        sell_btn = (ImageButton) findViewById(R.id.imageButton14);
        asus_btn = (ImageButton) findViewById(R.id.imageButton13);
    }
    // 클릭, 키보드 이벤트 설정
    public void SetListener(){
        // 클릭 리스너
        View.OnClickListener Listener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent;
                switch(v.getId()){
                    case R.id.Button1:
                        String str = user_search.getText().toString();
                        //EditText에 빈칸 들어올 시 에러 나는거 처리하는 코드
                        if(str.getBytes().length <= 0){
                            //Toast.makeText(MainActivity.this, "검색어를 입력해주세요.", Toast.LENGTH_LONG).show();
                        }
                        else {
                            // 검색어를 search_result 클래스에 보내는 인텐드 작업임
                            intent = new Intent(MainActivity.this, search_result.class);
                            intent.putExtra("search", str);
                            startActivity(intent);
                        }
                        break;
                    case R.id.imageButton14:
                        intent = new Intent(MainActivity.this, FClicked.class);
                        intent.putExtra("name", "Samsung_NT950XCR-G58A");
                        intent.putExtra("search", "NOTEBOOK");
                        startActivity(intent);
                        break;
                    case R.id.imageButton13:
                        intent = new Intent(MainActivity.this, FClicked.class);
                        intent.putExtra("name", "Asus_G731GU-EV001");
                        intent.putExtra("search", "NOTEBOOK");
                        startActivity(intent);
                        break;
                }
            }
        };
        // 버튼 리스너 설정
        btn.setOnClickListener(Listener);
        sell_btn.setOnClickListener(Listener);
        asus_btn.setOnClickListener(Listener);
        // 텍스트박스 엔터 설정 안하게 만들기
        View.OnKeyListener keyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (v.getId()) {
                    case R.id.user_serach:
                        // 줄바꿈 허용 안함
                        if (keyCode == event.KEYCODE_ENTER)
                            return true;
                        else
                            return false;
                }
                return false;
            }
        };
        user_search.setOnKeyListener(keyListener);

    }
}