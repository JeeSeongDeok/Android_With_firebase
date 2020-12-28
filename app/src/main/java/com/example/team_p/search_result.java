package com.example.team_p;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import static com.example.team_p.AutoCompleteActivity.COUNTRIES;

//헤드셋
public class search_result extends AppCompatActivity {
    private Button button;
    private Button button1;
    private static Toast toast;
    private ListView listView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private AutoCompleteTextView editText;
    private ArrayList<FItem> data = null;
    private ListView listView2;
    private FAdapter adapter2;
    private Intent intent;
    private String userSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        this.InitializeView(); //도구들 연결
        this.Autocomplete();//자동완성
        this.getDB();// 데이터베이스에서 자료 들고오면서 리스트 뷰에 뿌리기
        this.SetListener(); // 리스너 세팅
    }
    // 도구들 연결
    public void InitializeView(){
        editText = (AutoCompleteTextView) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.back_button);
        button1 = (Button) findViewById(R.id.Button1);
        listView = (ListView) findViewById(R.id.listView);
        listView2 = (ListView)findViewById(R.id.listView);
        data = new ArrayList<>();
        adapter2 = new FAdapter(this, R.layout.activity_f_item, data);
        listView2.setAdapter(adapter2);
    }
    // 검색창 오토컴플리트 연결
    public void Autocomplete(){
        //자동완성
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        this.editText.setAdapter(adapter1);
    }
    // MainActivity에서 넘어온 search 값 가공
    public void searchData(){
        userSearch = getIntent().getStringExtra("search");
        if(!userSearch.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
            userSearch = userSearch.toUpperCase();
        }
        else{
            // 한글이 포함된 경우
            switch(userSearch){
                case "노트북":
                    userSearch = "NOTEBOOK";
                    break;
                case "헤드셋":
                    userSearch = "HEADSET";
                    break;
            }
        }
    }
    // 데이터베이스에서 자료 들고오면서 리스트뷰에 뿌리기
    public void getDB(){
        // 인텐트
        searchData();
        // 리얼스트림데이터베이스 처리
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        DatabaseReference ProductRef = databaseReference.child(userSearch);
        // 데이터베이스에서 가져올 변수
        ProductRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    String name = postSnapshot.getKey();
                    FItem f1 = new FItem(""+name, "");
                    adapter2.add(f1);
                }
                listView2.setAdapter(adapter2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    // 리스너 정리
    // 클릭, 키보드 이벤트 설정
    public void SetListener(){
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), FClicked.class);

                intent.putExtra("name", data.get(position).getName_text());
                intent.putExtra("search", userSearch);
                startActivity(intent);
            }
        });
        // 클릭 리스너
        View.OnClickListener ClcikListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.back_button:
                        intent = new Intent(search_result.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.Button1:
                        String str = editText.getText().toString();
                        //EditText에 빈칸 들어올 시 에러 나는거 처리하는 코드
                        if(str.getBytes().length <= 0){
                            //Toast.makeText(search_result.this, "검색어를 입력해주세요.", Toast.LENGTH_LONG).show();
                        }
                        else {
                            intent = new Intent(search_result.this, search_result.class);
                            intent.putExtra("search", str);
                            startActivity(intent);
                        }
                        break;
                }
            }
        };
        // 버튼 리스너 설정
        button.setOnClickListener(ClcikListener);
        button1.setOnClickListener(ClcikListener);
        // 텍스트박스 엔터 설정 안하게 만들기
        View.OnKeyListener keyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (v.getId()) {
                    case R.id.editText:
                        // 줄바꿈 허용 안함
                        if (keyCode == event.KEYCODE_ENTER)
                            return true;
                        else
                            return false;
                }
                return false;
            }
        };
        editText.setOnKeyListener(keyListener);
    }
    //EditText포커스로 인한 키보드 내리기
    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
}
