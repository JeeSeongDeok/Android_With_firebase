package com.example.team_p;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link notebook_positive_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class notebook_positive_1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView textView;
    private ArrayList<String> ar;
    private ArrayList<TextView> textarray;
    private ArrayList<String> ar1;
    private ArrayList<RadioButton> Radioarray;
    private ArrayList<TextView> textarray1;
    private int index, rg_check, ar_index;
    private String find_str;

    public notebook_positive_1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment notebook_positive_1.
     */
    // TODO: Rename and change types and number of parameters
    public static notebook_positive_1 newInstance(String param1, String param2) {
        notebook_positive_1 fragment = new notebook_positive_1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Init
        Bundle bundle = getArguments();
        View view = inflater.inflate(R.layout.negative_fragment_open,null);
        // 텍스트 뷰
        textarray1 = new ArrayList<>();
        textarray1.add((TextView)view.findViewById(R.id.TextView1));
        textarray1.add((TextView)view.findViewById(R.id.TextView2));
        textarray1.add((TextView)view.findViewById(R.id.TextView3));
        textarray1.add((TextView)view.findViewById(R.id.TextView4));
        textarray1.get(0).setVisibility(View.GONE);
        textarray1.get(1).setVisibility(View.GONE);
        textarray1.get(2).setVisibility(View.GONE);
        textarray1.get(3).setVisibility(View.GONE);
        // 버튼
        final Button before_btn = (Button)view.findViewById(R.id.Before_btn);
        final Button next_btn = (Button)view.findViewById(R.id.Next_btn);
        next_btn.setVisibility(View.INVISIBLE);
        before_btn.setVisibility(View.INVISIBLE);
        // 레디오박스
        Radioarray = new ArrayList<>();
        Radioarray.add((RadioButton) view.findViewById(R.id.radioButton));
        Radioarray.add((RadioButton) view.findViewById(R.id.radioButton2));
        Radioarray.add((RadioButton) view.findViewById(R.id.radioButton3));
        Radioarray.add((RadioButton) view.findViewById(R.id.radioButton4));
        Radioarray.add((RadioButton) view.findViewById(R.id.radioButton5));
        Radioarray.add((RadioButton) view.findViewById(R.id.radioButton6));
        Radioarray.add((RadioButton) view.findViewById(R.id.radioButton7));
        Radioarray.add((RadioButton) view.findViewById(R.id.radioButton8));
        Radioarray.add((RadioButton) view.findViewById(R.id.radioButton9));
        Radioarray.add((RadioButton) view.findViewById(R.id.radioButton10));
        // 레디오 그룹
        final RadioGroup rg = view.findViewById(R.id.radioGroup1);
        final RadioGroup rg1 = view.findViewById(R.id.radioGroup2);
        index = 0;
        rg_check = -1;
        ar_index = 0;

        if(bundle != null){
            ar = bundle.getStringArrayList("positive");
            ar1 = bundle.getStringArrayList("positive_word");
            find_str = ar1.get(0);
            // 레디오버튼 세팅
            for (int i = 0; i < 10; i++) {
                // 텍스트 설정
                Radioarray.get(i).setText(ar1.get(i));
            }

            // 라디오버튼 클릭 이벤트
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if(rg_check > -1) {
                        Radioarray.get(rg_check).setChecked(false);
                        rg_check = -1;
                    }
                    if(checkedId == R.id.radioButton) {
                        find_str = (String) Radioarray.get(0).getText();
                        rg_check = 0;
                        ar_index = 0;
                    }
                    else if(checkedId == R.id.radioButton2) {
                        find_str = (String) Radioarray.get(1).getText();
                        rg_check = 1;
                        ar_index = 0;
                    }
                    else if(checkedId == R.id.radioButton3) {
                        find_str = (String) Radioarray.get(2).getText();
                        rg_check = 2;
                        ar_index = 0;
                    }
                    else if(checkedId == R.id.radioButton4) {
                        find_str = (String) Radioarray.get(3).getText();
                        rg_check = 3;
                        ar_index = 0;
                    }
                    else {
                        find_str = (String) Radioarray.get(4).getText();
                        rg_check = 4;
                        ar_index = 0;
                    }
                    if(ar_index == 0) {
                        before_btn.setVisibility(View.VISIBLE);
                        next_btn.setVisibility(View.VISIBLE);
                        before_btn.setEnabled(false);
                    }
                    for (int i = index; i < index + 4; i++) {
                        if (ar_index >= ar.size()) {
                            next_btn.setEnabled(false);
                            textarray1.get(i%4).setVisibility(View.INVISIBLE);
                        } else {
                            if(ar.get(ar_index).contains(find_str)){
                                textarray1.get(i%4).setText(ar.get(ar_index));
                                textarray1.get(i%4).setVisibility(View.VISIBLE);
                                ar_index++;
                            }else {
                                ar_index++;
                                i -=1;
                            }
                        }
                    }
                }
            });
            rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if(rg_check > -1) {
                        Radioarray.get(rg_check).setChecked(false);
                        rg_check = -1;
                    }
                    if(checkedId == R.id.radioButton6) {
                        find_str = (String) Radioarray.get(5).getText();
                        rg_check = 5;
                        ar_index = 0;
                    }
                    else if(checkedId == R.id.radioButton7) {
                        find_str = (String) Radioarray.get(6).getText();
                        rg_check = 6;
                        ar_index = 0;
                    }
                    else if(checkedId == R.id.radioButton8) {
                        find_str = (String) Radioarray.get(7).getText();
                        rg_check = 7;
                        ar_index = 0;
                    }
                    else if(checkedId == R.id.radioButton9) {
                        find_str = (String) Radioarray.get(8).getText();
                        rg_check = 8;
                        ar_index = 0;
                    }
                    else {
                        find_str = (String) Radioarray.get(9).getText();
                        rg_check = 9;
                        ar_index = 0;
                    }
                    if(ar_index == 0) {
                        before_btn.setVisibility(View.VISIBLE);
                        next_btn.setVisibility(View.VISIBLE);
                        before_btn.setEnabled(false);
                    }
                    for (int i = index; i < index + 4; i++) {
                        if (ar_index >= ar.size()) {
                            next_btn.setEnabled(false);
                            textarray1.get(i%4).setVisibility(View.INVISIBLE);
                        } else {
                            if(ar.get(ar_index).contains(find_str)){
                                textarray1.get(i%4).setText(ar.get(ar_index));
                                textarray1.get(i%4).setVisibility(View.VISIBLE);
                                ar_index++;
                            }else {
                                ar_index++;
                                i -=1;
                            }
                        }
                    }
                }
            });
            // 버튼 리스너들
            next_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(before_btn.isEnabled() == false)
                        before_btn.setEnabled(true);
                    index += 4;
                    for (int i = index; i < index + 4; i++) {
                        if (ar_index >= ar.size()) {
                            next_btn.setEnabled(false);
                        } else {
                            if(ar.get(ar_index).contains(find_str)){
                                textarray1.get(i%4).setText(ar.get(ar_index));
                                ar_index++;
                            }else {
                                ar_index++;
                                i -=1;
                            }
                        }
                    }
                    if (ar.get(ar_index+1) == null)
                        next_btn.setEnabled(false);
                }
            });
            before_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    index -= 4;
                    if(next_btn.isEnabled() == false)
                        next_btn.setEnabled(true);
                    if (index < 0) {
                        index = 0;
                        before_btn.setEnabled(false);
                    } else {
                        before_btn.setEnabled(true);
                    }
                    for (int i = index; i < index + 4; i++) {
                        if(ar.get(ar_index) == null) {
                            before_btn.setEnabled(false);
                        }else {
                            if(ar.get(ar_index).contains(find_str)){
                                textarray1.get(i%4).setText(ar.get(ar_index));
                                ar_index--;
                            }else {
                                ar_index--;
                                i -=1;
                            }
                        }
                    }
                }
            });

        }else{
            Toast.makeText(inflater.getContext(), "다시 시도해주세요", Toast.LENGTH_SHORT).show();
        }

        return view;
    }
}