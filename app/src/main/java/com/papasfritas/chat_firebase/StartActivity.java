package com.papasfritas.chat_firebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.papasfritas.chat_firebase.Adapters.viewPagerAdapter;
import com.papasfritas.chat_firebase.Fragments.ChatsFragment;
import com.papasfritas.chat_firebase.Fragments.LoginFragment;
import com.papasfritas.chat_firebase.Fragments.ProfileFragment;
import com.papasfritas.chat_firebase.Fragments.RegisterFragment;
import com.papasfritas.chat_firebase.Fragments.UsersFragment;

public class StartActivity extends AppCompatActivity implements View.OnClickListener{

    Button login, registro;

    FirebaseUser firebaseUser;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser != null){
            startActivity(new Intent(StartActivity.this, MainActivity.class));
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        init();
    }

    public void init(){
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);

        viewPagerAdapter viewPagerAdapter = new viewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new LoginFragment(), "Login");
        viewPagerAdapter.addFragment(new RegisterFragment(), "Register");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

//        login = findViewById(R.id.login);
//        login.setOnClickListener(this);
//        registro = findViewById(R.id.registro);
//        registro.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.registro:
//                startActivity(new Intent(StartActivity.this, RegisterActivity.class));
//                break;
//
//            case R.id.login:
//                startActivity(new Intent(StartActivity.this, LoginActivity.class));
//                break;
        }

    }
}
