package com.example.kovenkopr29;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView timePick;
    Button btnTime, btnDate, btnCustomDialog;
    TextView textResult;

    // Календарь для хранения текущей даты и времени
    Calendar dateAndTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Привязка элементов ЗАДАНИЯ 2
        timePick = findViewById(R.id.time_pick);
        btnDate = findViewById(R.id.button_date);
        btnTime = findViewById(R.id.button_time);

        // Привязка элементов ЗАДАНИЯ 3
        btnCustomDialog = findViewById(R.id.button_custom_dialog);
        textResult = findViewById(R.id.text_result);

        // Установка слушателей нажатий
        btnDate.setOnClickListener(this);
        btnTime.setOnClickListener(this);
        btnCustomDialog.setOnClickListener(this);

        // Инициализация начального времени
        setInitialDateTime();
    }

    // Метод для отображения даты и времени в TextView
    private void setInitialDateTime() {
        timePick.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));
    }

    // Обработчик нажатий на кнопки
    @Override
    public void onClick(View v) {
        // В новых версиях Android Studio вместо switch (view.getId()) лучше использовать if-else
        int id = v.getId();

        if (id == R.id.button_date) {
            // Диалог выбора даты
            new DatePickerDialog(MainActivity.this, d,
                    dateAndTime.get(Calendar.YEAR),
                    dateAndTime.get(Calendar.MONTH),
                    dateAndTime.get(Calendar.DAY_OF_MONTH))
                    .show();
        }
        else if (id == R.id.button_time) {
            // Диалог выбора времени
            new TimePickerDialog(MainActivity.this, t,
                    dateAndTime.get(Calendar.HOUR_OF_DAY),
                    dateAndTime.get(Calendar.MINUTE), true)
                    .show();
        }
        else if (id == R.id.button_custom_dialog) {
            // ЗАДАНИЕ 3: Кастомный диалог с передачей данных
            showCustomDialog();
        }
    }

    // Установка обработчика выбора времени
    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };

    // Установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

    // ========================================================
    // ЗАДАНИЕ 3: Реализация диалогового окна (AlertDialog)
    // ========================================================
    private void showCustomDialog() {
        // 1. Создаем Builder для диалога
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Подключаем нашу разметку (вдуваем LayoutInflater)
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_custom, null);
        builder.setView(dialogView);

        // Получаем доступ к EditText внутри диалога
        EditText editTextInput = dialogView.findViewById(R.id.edit_text_input);

        // ПЕРЕДАЧА ДАННЫХ В ДИАЛОГ (передаем текущий текст из TextView в EditText)
        editTextInput.setText(textResult.getText().toString());

        // 3. Настраиваем кнопки
        builder.setPositiveButton("Применить", (dialog, which) -> {
            // ПЕРЕДАЧА ДАННЫХ ОБРАТНО В АКТИВИТИ
            String userInput = editTextInput.getText().toString();
            textResult.setText(userInput);
        });

        builder.setNegativeButton("Отмена", (dialog, which) -> dialog.dismiss());

        // 4. Показываем диалог
        builder.show();
    }
}