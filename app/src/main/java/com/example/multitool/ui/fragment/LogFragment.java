package com.example.multitool.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.applandeo.materialcalendarview.CalendarDay;
import com.example.multitool.R;
import com.example.multitool.databinding.FragmentLogBinding;
import com.example.multitool.model.ChecklistItem;
import com.example.multitool.viewmodel.ShoppingMemoViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public class LogFragment extends Fragment {
    private ShoppingMemoViewModel shoppingMemoViewModel;
    private FragmentLogBinding binding;
    private TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shoppingMemoViewModel = new ViewModelProvider(requireActivity()).get(ShoppingMemoViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = view.findViewById(R.id.logText);
        CalendarView calendarView = view.findViewById(R.id.logCalender);
        calendarView.setDate(new Date().getTime());

        // カレンダーをクリックしたときの処理を登録
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int day) {
                updateTextForDate(year, month, day);
            }
        });

        // 今日の日付を選択
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        updateTextForDate(year, month, day);
    }

    // ログテキストを更新
    private void updateTextForDate(int year, int month, int day) {
        List<ChecklistItem> targetItems = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        for (ChecklistItem item : shoppingMemoViewModel.logItems){
            try {
                // ChecklistItemデータの日時を取得する
                Date date = sdf.parse(item.getUpdatedAt());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(Objects.requireNonNull(date));
                int dateYear = calendar.get(Calendar.YEAR);
                int dateMonth = calendar.get(Calendar.MONTH); // 0から始まるので注意
                int dateDay = calendar.get(Calendar.DAY_OF_MONTH);

                if (dateYear != year || dateMonth != month || dateDay != day) {
                    continue;
                }

                targetItems.add(item);  // 対象アイテムをリストに追加
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (targetItems.size() == 0){
            textView.setText("");
            return;
        }

        // テキストの生成
        StringBuilder text = new StringBuilder();
        if (targetItems.size() == 1){
            String format = "%s";  // 例: 24/01/01　米
            text = new StringBuilder(String.format(format, targetItems.get(0).getName()));
        } else {
            for (int j = 0; j < targetItems.size(); j++) {
                String format = "\n%s";
                if (j == 0){
                    format = "%s";
                }

                text.append(String.format(format, targetItems.get(j).getName()));
            }
        }

        // 表示
        textView.setText(text.toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}