package com.gay.xmen.intro;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.gay.xmen.Adapters.IntroViewPagerAdapter;
import com.gay.xmen.MainActivity;
import com.gay.xmen.R;
import com.gay.xmen.activities.SplashActivity;

public class IntroActivity extends AppCompatActivity {


    private ViewPager viewPager;
    private Button Next;
    private Button Prev;
    int ic = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        viewPager = findViewById(R.id.vipager);
        Prev = findViewById(R.id.prev);
        Next = findViewById(R.id.next);

        IntroViewPagerAdapter pagerAdapter = new IntroViewPagerAdapter(getSupportFragmentManager(), 4);

        viewPager.setAdapter(pagerAdapter);

viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        ic = position;
        if(ic == viewPager.getAdapter().getCount() - 1){
            Log.e("MAIN", "onClick: SI MARICO");
            Next.setBackground(getResources().getDrawable(R.drawable.ok_icon));
        }
        if(ic < viewPager.getAdapter().getCount() - 1){
            Next.setBackground(getResources().getDrawable(R.drawable.arrow_icon));
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
});

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ic + 1 < viewPager.getAdapter().getCount()){
                    ic++;

                        viewPager.setCurrentItem(ic);

                }else {


                    if (ic == viewPager.getAdapter().getCount() - 1) {
                        Log.e("MAIN", "onClick: SI MARICO");
                        Next.setBackground(getResources().getDrawable(R.drawable.ok_icon));

                        SplashActivity.preferences.edit().putInt(MainActivity.key_tt, 1).commit();

                        Intent intent = new Intent(IntroActivity.this, SplashActivity.class);

                        startActivity(intent);

                    }
                }

                Log.e("MAIN", "onClick: pos "+ic );
            }
        });

        Prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ic - 1 >= 0){
                    ic--;
                    Next.setBackground(getResources().getDrawable(R.drawable.arrow_icon));
                    viewPager.setCurrentItem(ic);
                }
            }
        });

    }
}
