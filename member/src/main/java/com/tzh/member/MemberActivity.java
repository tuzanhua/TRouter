package com.tzh.member;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioRouting;
import android.os.Bundle;
import android.view.View;

import com.tzh.annation.TRouterPath;
import com.tzh.router.TRouter;

@TRouterPath("member/member")
public class MemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TRouter.getInstance().navigation("main/main");
            }
        });
    }
}
