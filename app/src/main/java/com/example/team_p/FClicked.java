package com.example.team_p;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

//activity_f_item의 아이템이 클릭되었을 때
//나타나는 화면임.

public class FClicked extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String search, Name;
    private boolean isChange = true;
    private FragmentManager fm, fm1;
    private FragmentTransaction fragmentTransaction, fragmentTransaction1;
    private Fragment Positive_fr_open, Positive_fr_close, Negative_fr_open, Negative_fr_close;
    private Bundle bundle, bundle1;
    private Switch positive_switch, negative_switch;
    private ImageView profile;
    private TextView name;

    // intent로 넘어온 값들 받아들이는 함수
    public void get_intent(){
        Intent intent = getIntent();
        this.Name = intent.getStringExtra("name");
        this.search = intent.getStringExtra("search");

    }
    public void xml_set() {
        //이미지 불러오는 거 처리
        name.setText(this.Name);
        getImg();

        // DB 처리
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        DatabaseReference Ref = databaseReference.child(this.search).child(this.Name).child("0");


        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList <String> ar = new ArrayList<>();
                ArrayList <String> ar1 = new ArrayList<>();
                ArrayList <String> ar2 = new ArrayList<>();
                ArrayList <String> ar3 = new ArrayList<>();
                bundle = new Bundle();
                for (DataSnapshot postSnapshot : snapshot.child("NegativeReview").getChildren()) {
                    ar.add((String)postSnapshot.getValue());
                }
                for (DataSnapshot postSnapshot : snapshot.child("NegativeWord").getChildren()) {
                    ar2.add((String)postSnapshot.getValue());
                }
                bundle.putStringArrayList("negative_word", ar2);
                bundle.putStringArrayList("negative", ar);
                Negative_fr_open = new negative_fragment_open();
                Negative_fr_open.setArguments(bundle);

                bundle1 = new Bundle();
                for (DataSnapshot postSnapshot : snapshot.child("PostiveReview").getChildren()) {
                    ar1.add((String)postSnapshot.getValue());
                }
                for (DataSnapshot postSnapshot : snapshot.child("PostiveWord").getChildren()) {
                    ar3.add((String)postSnapshot.getValue());
                }
                bundle1.putStringArrayList("positive_word", ar3);
                bundle1.putStringArrayList("positive", ar1);
                Positive_fr_open = new notebook_positive_1();
                Positive_fr_open.setArguments(bundle1);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
        // DB 끝
        // 리스너들 처리하자
        setListener();
    }
    public void getImg(){
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://teamp-c4c2f.appspot.com");
        StorageReference storageRef = storage.getReference();
        storageRef.child("NOTEBOOK/"+ this.Name + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //이미지 로드 성공시
                Glide.with(getApplicationContext())
                        .load(uri)
                        .into(profile);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                //이미지 로드 실패시
                Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setListener(){

        // 네거티브 클릭 이벤트
        negative_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Fragment fr;
                if(isChecked){
                    fr = Negative_fr_open;
                }else{
                    fr = Negative_fr_close;
                }
                fm = getSupportFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.negative_fragment, fr);
                fragmentTransaction.commit();
            }
        });
        // 긍정 스위치 변경
        positive_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Fragment fr1;
                if (isChecked){
                    fr1 = Positive_fr_open;
                }else{
                    fr1 = Positive_fr_close;
                }
                fm1 = getSupportFragmentManager();
                fragmentTransaction1 = fm1.beginTransaction();
                fragmentTransaction1.replace(R.id.positive_frog, fr1);
                fragmentTransaction1.commit();
            }
        });
    }
    public void init(){
        profile = (ImageView) findViewById(R.id.img_prof);
        name = (TextView) findViewById(R.id.name);
        Positive_fr_close = new notebook_positive_1_1();
        Positive_fr_open = new notebook_positive_1();
        Negative_fr_open = new negative_fragment_open();
        Negative_fr_close = new negative_fragment_close();
        positive_switch = findViewById(R.id.Positive_switch);
        negative_switch = findViewById(R.id.switch1);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_clicked);

        get_intent();
        init();
        xml_set();
    }
}