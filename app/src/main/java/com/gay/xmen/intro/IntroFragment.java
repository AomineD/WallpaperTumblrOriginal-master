package com.gay.xmen.intro;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gay.xmen.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntroFragment extends Fragment {

    public static final String key_title = "jdasjdjas";
    public static final String key_desc = "sdajsdjas";
    public static final String key_id = "jasdjedw2";


    private String[] text_descp;
    private String[] text_title;
    private int[] introimage;
    private int id;
    public IntroFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        introimage = new int[]{R.drawable.intro1, R.drawable.intro2, R.drawable.intro3, R.drawable.intro4};
        text_title = new String[]{getString(R.string.intro1_title), getString(R.string.intro2_title), getString(R.string.intro3_title), getString(R.string.intro4_title)};
        text_descp = new String[]{getString(R.string.intro1_desc), getString(R.string.intro2_desc), getString(R.string.intro3_desc), getString(R.string.intro4_desc)};


        Bundle Arguments = getArguments();


        if(Arguments != null){

            id = Arguments.getInt(key_id);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_intro, container, false);


        ImageView img = view.findViewById(R.id.pictt);
        TextView title = view.findViewById(R.id.title);
        TextView desc = view.findViewById(R.id.desc);




        img.setImageDrawable(getResources().getDrawable(introimage[id]));
        title.setText(text_title[id]);
desc.setText(text_descp[id]);



        return view;
    }

}
