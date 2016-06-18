package com.nick.cpiherdemo;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawer;
    private Fragment[] mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Md5Fragment()).commit();

        NavigationView navigation = (NavigationView) findViewById(R.id.navigation);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer);

        navigation.setNavigationItemSelectedListener(this);

        mFragments = new Fragment[5];

        mFragments[0] = new Md5Fragment();
        mFragments[1] = new Base64Fragment();
        mFragments[2] = new DesFragment();
        mFragments[3] = new RsaFragment();
        mFragments[4] = new SignFragment();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (item.getItemId()) {
            case R.id.menu_md5:
                transaction.replace(R.id.container, mFragments[0]);
                break;
            case R.id.menu_base64:
                transaction.replace(R.id.container, mFragments[1]);
                break;
            case R.id.menu_des:
                transaction.replace(R.id.container, mFragments[2]);
                break;
            case R.id.menu_rsa:
                transaction.replace(R.id.container, mFragments[3]);
                break;
            case R.id.menu_sign:
                transaction.replace(R.id.container, mFragments[4]);
                break;
        }
        transaction.commit();

        mDrawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
