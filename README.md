# 안드로이드에서 파이어베이스 사용하기 및 제품 감성분석
안드로이드에서는 내부 데이터베이스인 SQLITE를 내장하고 있으며, SQLITE를 사용하지 않더라도 백엔드 서버를 통해서 데이터베이스를 구현할 수 있다.
그러나, 학부생 입장에서는 백엔드 서버와 데이터베이스를 구현하기에는 쉽지는 않다. 이 문제를 해결하기 위해 구글에서 제공하는 파이어베이스를 통해서 백엔드서버와 데이터베이스를 구현했다.
방학 때 경남테크노파크에서 인공지능에 관한 수업을 들은 이후, 이와 관련된 프로젝트를 완성했다. 제품에 대한 리뷰를 크롤링을 통해서 긁어온 후, 리뷰를 TF-IDF 알고리즘을 통해서 분석을 해봤다. 

## 실행화면
### 메인 화면 
<img src="https://user-images.githubusercontent.com/23256819/111060388-dfaa8580-84df-11eb-819b-6910060ad4eb.png" width="300" height="500"></img>
<br>
노트북 이미지는 Firebase에 저장된 이미지를 불러왔다.
###  검색결과 화면
<img src="https://user-images.githubusercontent.com/23256819/111060390-e0dbb280-84df-11eb-87b5-cf7c862edaab.png" width="300" height="500"></img>
###  상품화면
<img src="https://user-images.githubusercontent.com/23256819/111060392-e1744900-84df-11eb-9e6a-ea1eda19bfbe.png" width="300" height="500"></img>
###  리뷰 클릭 시 나오는 화면
<img src="https://user-images.githubusercontent.com/23256819/111060393-e1744900-84df-11eb-9e0a-8b0ce5271fc2.png" width="300" height="500"></img>
<br>
TF-IDF 알고리즘을 이용해서 추출한 단어들이다.
###  단어 선택시 나오는 화면
<img src="https://user-images.githubusercontent.com/23256819/111060394-e20cdf80-84df-11eb-95b7-acbae4dcac8e.png" width="300" height="500"></img>
<br>
단어를 선택시 단어가 포함된 리뷰가 나타난다.

## 파이어베이스 사용을 위한 설정 및 코드 출처 - [파이어베이스 공식문서](https://firebase.google.com/docs/android/setup)
### bundle.gradle (project name)에 추가하기
<pre>
<code>
dependencies {
        classpath 'com.android.tools.build:gradle:4.1.0'
        classpath 'com.google.gms:google-services:4.3.4'

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
</code>
</pre>
### bundle.gradle (Module.app)에 추가하기
<pre>
<code>
apply plugin: 'com.android.application'
apply plugin: 'com.google.gms.google-services'
dependencies{
    implementation 'com.google.firebase:firebase-database:19.5.1'
    implementation 'com.google.firebase:firebase-analytics:18.0.0'
    implementation 'com.google.firebase:firebase-storage:19.2.0'
}
</code>
</pre>

### 자바코드

```java
// DB 처리
// 변수 선언
private FirebaseDatabase firebaseDatabase;
private DatabaseReference databaseReference;

firebaseDatabase = FirebaseDatabase.getInstance();
databaseReference = firebaseDatabase.getReference();
// 어디 테이블에서 들고올지 설정. 이 어플리케이션의 경우는 Notebook - 0 테이블이름을 선택
DatabaseReference Ref = databaseReference.child(this.search).child(this.Name).child("0");
// 실시간 데이터베이스를 사용해서 계속 응답을 받거나 보낼 수 있는 함수와, 한번만 부를 수 있는 함수(SingleValueEvent)가 있다. 
// 여기서는 한번만 부르는 SingleValueEvent을 사용함
Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // 성공적으로 불러냇으면 이 함수가 실행됨.
                ArrayList <String> ar = new ArrayList<>();
                ArrayList <String> ar1 = new ArrayList<>();
                ArrayList <String> ar2 = new ArrayList<>();
                ArrayList <String> ar3 = new ArrayList<>();
                bundle = new Bundle();
                // 0 테이블의 자식 NegativeReview에 있는 값을 하나식 불러옵니다.
                for (DataSnapshot postSnapshot : snapshot.child("NegativeReview").getChildren()) {
                    ar.add((String)postSnapshot.getValue());
                }
                // 0 테이블의 자식 NegativeWord에 있는 값을 하나식 불러옵니다.
                for (DataSnapshot postSnapshot : snapshot.child("NegativeWord").getChildren()) {
                    ar2.add((String)postSnapshot.getValue());
                }
                // arrayList에 있는 값을 번들로 보냄
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
            public void onCancelled(@NonNull DatabaseError error) { 
                   // 성공적으로 불러내지 못하면 이 함수가 실행됨
            }
        });
```
