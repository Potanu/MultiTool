package com.example.multitool;

import com.example.multitool.viewmodel.ShoppingMemoViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.multitool.databinding.ActivityMainBinding;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(findViewById(R.id.toolbar));

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_shopping_memo, R.id.navigation_log)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    protected void onStop() {
        // dbに保存
        ShoppingMemoViewModel shoppingMemoViewModel = new ViewModelProvider(this).get(ShoppingMemoViewModel.class);
        shoppingMemoViewModel.saveChecklistItem();
        super.onStop();
    }

    // PCチェック用
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // dキーが押されたとき
        if (keyCode == KeyEvent.KEYCODE_D) {
            // アクティビティを終了する
            finish();
            return true; // イベントを消費したことを示す
        }
        return super.onKeyDown(keyCode, event); // 他のキーイベントは通常の動作
    }
}