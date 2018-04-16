package com.example.askar.KanatTole;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.askar.KanatTole.Fragments.HomeFragment.ChartFragment;
import com.example.askar.KanatTole.Fragments.MoreFragment.ProductListFragment;
import com.example.askar.KanatTole.Fragments.QrCodeFragment.QRScannerFragment;
import com.example.askar.KanatTole.Fragments.SettingsFragment.MainFragment;
import com.example.askar.task3bottomnavigationview.R;
import com.example.askar.KanatTole.Utils.BottomNavigationViewEx;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private int count =0;

    private FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFragment(new ChartFragment());
        setupNavigationView();

    }

    //BottomNavigationView
    private void setupNavigationView(){
        Log.d(TAG, "setupNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx)findViewById(R.id.bottomNavViewBar);
        final Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);




        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()){
                    case R.id.ic_home:
                        count=0;
                        setFragment(new ChartFragment());
                        MenuItem menuItem0 = menu.getItem(0);
                        menuItem0.setChecked(true);
                        break;
                    case R.id.ic_qr_code:
                        if(count==0 || count==2) {
                            Bundle bundle = new Bundle();
                            QRScannerFragment fragment = new QRScannerFragment();
                            bundle.putBoolean("BOOL", true);
                            fragment.setArguments(bundle);
                            setFragment(fragment);
                            MenuItem menuItem1 = menu.getItem(1);
                            menuItem1.setChecked(true);
                            count=3;
                        }
                        break;
                    case R.id.ic_add:
                        if(count==0 || count == 3){
                        Bundle bundle2 = new Bundle();
                        QRScannerFragment fragment1 = new QRScannerFragment();
                        bundle2.putBoolean("BOOL",false);
                        fragment1.setArguments(bundle2);
                        setFragment(fragment1);
                        MenuItem menuItem3 = menu.getItem(2);
                        menuItem3.setChecked(true);
                        count=2;}
                        break;
                    case R.id.ic_more:
                        count=0;
                        setFragment(new ProductListFragment());
                        MenuItem menuItem2 = menu. getItem(3);
                        menuItem2.setChecked(true);
                        break;

                    case R.id.ic_settings:
                        count=0;
                        setFragment(new MainFragment());
                        MenuItem menuItem4 = menu.getItem(4);
                        menuItem4.setChecked(true);
                        break;
                }

                return false;
            }
        });
    }
    private void setFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);

        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
        else {
        }
    }
}
