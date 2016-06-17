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
    private FragmentTransaction mTransaction;
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

        mFragments[0] = new DesFragment();
        mFragments[1] = new Base64Fragment();
        mFragments[2] = new DesFragment();
        mFragments[3] = new RsaFragment();
        mFragments[4] = new SignFragment();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        mTransaction = getSupportFragmentManager().beginTransaction();

        switch (item.getItemId()) {
            case R.id.menu_md5:
                mTransaction.replace(R.id.container, mFragments[0]);
                break;
            case R.id.menu_base64:
                mTransaction.replace(R.id.container, mFragments[1]);
                break;
            case R.id.menu_des:
                mTransaction.replace(R.id.container, mFragments[2]);
                break;
            case R.id.menu_rsa:
                mTransaction.replace(R.id.container, mFragments[3]);
                break;
            case R.id.menu_sign:
                mTransaction.replace(R.id.container, mFragments[4]);
                break;
        }
        mTransaction.commit();

        mDrawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
