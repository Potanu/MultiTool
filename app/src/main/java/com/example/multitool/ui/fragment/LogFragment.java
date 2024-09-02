package com.example.multitool.ui.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.applandeo.materialcalendarview.CalendarDay;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.listeners.OnCalendarDayClickListener;
import com.example.multitool.R;
import com.example.multitool.databinding.FragmentLogBinding;
import com.example.multitool.model.ChecklistItem;
import com.example.multitool.viewmodel.ShoppingMemoViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
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
        calendarView.setCalendarDays(getMarkingDays());

        // カレンダーをクリックしたときの処理を登録
        calendarView.setOnCalendarDayClickListener(new OnCalendarDayClickListener() {
            @Override
            public void onClick(@NonNull CalendarDay calendarDay){
                Calendar clickedDayCalendar = calendarDay.getCalendar();
                int year = clickedDayCalendar.get(Calendar.YEAR);
                int month = clickedDayCalendar.get(Calendar.MONTH);
                int day = clickedDayCalendar.get(Calendar.DAY_OF_MONTH);
                updateTextForDate(year, month, day);
            }
        });

        // テキストエリアの背景色を設定
        Configuration config = getResources().getConfiguration();
        int nightMode = config.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        int logThemeColor;
        if (nightMode == Configuration.UI_MODE_NIGHT_YES) {
            // ダークモード
            logThemeColor = ContextCompat.getColor(view.getContext(), R.color.dark_gray);
        } else {
            // ライトモード
            logThemeColor = ContextCompat.getColor(view.getContext(), R.color.light_gray);
        }

        ScrollView scrollView = view.findViewById(R.id.logTextScrollView);
        scrollView.setBackgroundColor(logThemeColor);

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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
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

        String dateText = String.format(Locale.JAPAN, "%02d/%02d/%02d", year, month, day);
        SpannableString spannableString = new SpannableString(dateText);    // 日付に下線を引く
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        if (targetItems.size() == 0){
            textView.setText(spannableString);
            return;
        }

        // テキストの生成
        StringBuilder text = new StringBuilder();
        for (int j = 0; j < targetItems.size(); j++) {
            String format = "\r\n%s";
            text.append(String.format(format, targetItems.get(j).getName()));
        }

        SpannableString spannableString2 = new SpannableString(dateText + text);
        spannableString2.setSpan(new UnderlineSpan(), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString2);
    }

    // 購入した日にちのリストを返す
    private List<CalendarDay> getMarkingDays(){
        List<CalendarDay> calendarDayList = new ArrayList<>();

        // 重複しない日にちリストを作成する
        Set<Date> dateList = new LinkedHashSet<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        for (ChecklistItem item : shoppingMemoViewModel.logItems){
            try {
                Date date = sdf.parse(item.getUpdatedAt());
                dateList.add(date);
            } catch (ParseException e) {
                e.printStackTrace();
                return calendarDayList;
            }
        }

        for (Date date : dateList){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            CalendarDay day = new CalendarDay(calendar);
            day.setImageResource(R.drawable.calendar_icon);
            calendarDayList.add(day);
        }

        return calendarDayList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}