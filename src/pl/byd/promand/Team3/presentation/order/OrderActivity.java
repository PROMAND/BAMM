package pl.byd.promand.Team3.presentation.order;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import pl.byd.promand.Team3.R;

public class OrderActivity extends SherlockActivity {
    private String name;
    private String phoneNumber;

    private EditText nameET;
    private EditText phoneNumberET;

    private TextView textViewTime;
    private TextView textViewDate;

    private Button confirmButton;
    private Button datePickButton;
    private Button timePickButton;

    private TimePickerDialog timePicker;
    private DatePickerDialog datePickerDialog;


    private Integer year;
    private Integer month;
    private Integer day;
    private Integer hours;
    private Integer minutes;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cart:

                break;
            case R.id.location:

                break;

            default:
                break;
        }

        return true;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);

        confirmButton = (Button)findViewById(R.id.orderConfirmButton);
        datePickButton = (Button)findViewById(R.id.btnDatePicker);
        timePickButton = (Button)findViewById(R.id.btnTimePirkcer);

        textViewDate = (TextView) findViewById(R.id.orderDate);
        textViewTime = (TextView) findViewById(R.id.orderTime);

        nameET = (EditText)findViewById(R.id.orderEditTextName);
        phoneNumberET = (EditText)findViewById(R.id.orderEditTextPhoneNumber);

        timePickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker = new TimePickerDialog(OrderActivity.this, onTimeSetListener,19,00,true);
                timePicker.show();

            }
        });

        datePickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(OrderActivity.this, onDateSetListener,2012,2,10);
                datePickerDialog.show();

            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameET.getText().toString();
                phoneNumber = phoneNumberET.getText().toString();

                String mailText = "Name: " + name + "Phone: " + phoneNumber + " Order date: " + textViewDate.getText() + " Order time: " + textViewTime.getText();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"tosha123@inbox.lv"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Table Reservation");
                intent.putExtra(Intent.EXTRA_TEXT, mailText);

                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });

    }

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            setYear(year);
            setMonth(month);
            setDay(day);
            textViewDate.setText(String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day));

        }
    };

    private TimePickerDialog.OnTimeSetListener onTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
            setHours(hours);
            setMinutes(minutes);
            textViewTime.setText(String.valueOf(hours) + ":" + String.valueOf(minutes));
        }
    };


    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getSupportMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

}