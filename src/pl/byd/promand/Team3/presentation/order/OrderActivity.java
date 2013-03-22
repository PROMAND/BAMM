package pl.byd.promand.Team3.presentation.order;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;

import pl.byd.promand.Team3.R;
import pl.byd.promand.Team3.infrastructure.data.GlobalState;
import pl.byd.promand.Team3.infrastructure.data.MenuItem;
import pl.byd.promand.Team3.infrastructure.data.MyDAO;
import pl.byd.promand.Team3.infrastructure.data.Restaurant;
//import pl.byd.promand.Team3.infrastructure.order.OrderAdapter;
import pl.byd.promand.Team3.presentation.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends SherlockActivity {
    private String name;
    private String phoneNumber;

    private EditText nameET;
    private EditText phoneNumberET;

    private ListView orderListView;

    private TextView numOfSitsTV;
    private TextView textViewTime;
    private TextView textViewDate;
    private TextView fillFormTV;
    private TextView selectSitsTV;

    private Button confirmButton;
    private Button datePickButton;
    private Button timePickButton;
    private ImageButton btnPlus;
    private ImageButton btnMinus;

    private TimePickerDialog timePicker;
    private DatePickerDialog datePickerDialog;

    private Integer year;
    private Integer month;
    private Integer day;
    private Integer hours;
    private Integer minutes;
    private Integer numOfSits;

    private final int TEXT_SIZE = 20;

    private MyDAO myDAO;
    private Restaurant restaurant;

    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent home = new Intent(this, MainActivity.class);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home);
                return true;
            case R.id.location:
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?q=" + restaurant.Localization_x + "," + restaurant.Localization_y));
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);
        // Change app action bar title

        getSupportActionBar().setTitle("RestoPesto: Order");

        int resId = getIntent().getExtras().getInt("RestaurantId");
        restaurant = MyDAO.getInstance().getRestaurant(resId);

        MyDAO.getInstance().getMenuItemArray(restaurant.Restaurant_ID);

        confirmButton = (Button) findViewById(R.id.orderConfirmButton);
        datePickButton = (Button) findViewById(R.id.btnDatePicker);
        timePickButton = (Button) findViewById(R.id.btnTimePicker);
        btnMinus = (ImageButton) findViewById(R.id.orderBtnMinus);
        btnPlus = (ImageButton) findViewById(R.id.orderBtnPlus);

        textViewDate = (TextView) findViewById(R.id.orderDate);
        textViewTime = (TextView) findViewById(R.id.orderTime);
        numOfSitsTV = (TextView) findViewById(R.id.orderEditTextSits);
        fillFormTV = (TextView) findViewById(R.id.orderTextView);
        selectSitsTV = (TextView) findViewById(R.id.orderTextViewSits);

        orderListView = (ListView) findViewById(R.id.orderListView);

        nameET = (EditText) findViewById(R.id.orderEditTextName);
        phoneNumberET = (EditText) findViewById(R.id.orderEditTextPhoneNumber);

        numOfSitsTV.setText("0");
        numOfSitsTV.setTextSize(TEXT_SIZE);
        fillFormTV.setTextSize(TEXT_SIZE);
        selectSitsTV.setTextSize(TEXT_SIZE);

        ArrayList<Pair<Integer,Integer>> orderedItemList = GlobalState.getInstance().getOrderByRestaurant(restaurant.Restaurant_ID);

        OrderAdapter adapter = new OrderAdapter(OrderActivity.this, orderedItemList);
        orderListView.setAdapter(adapter);

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numOfSits = Integer.valueOf(numOfSitsTV.getText().toString());
                if (numOfSits > 0) {
                    numOfSits--;
                }
                numOfSitsTV.setText(String.valueOf(numOfSits));
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numOfSits = Integer.valueOf(numOfSitsTV.getText().toString());
                if (numOfSits < 10) {
                    numOfSits++;
                }
                numOfSitsTV.setText(String.valueOf(numOfSits));
            }
        });

        timePickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker = new TimePickerDialog(OrderActivity.this, onTimeSetListener, 19, 00, true);
                timePicker.show();

            }
        });

        datePickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(OrderActivity.this, onDateSetListener, 2012, 2, 10);
                datePickerDialog.show();

            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameET.getText().toString();
                phoneNumber = phoneNumberET.getText().toString();

                String mailText = "Name: " + name + " Phone: " + phoneNumber + " Order date: " + textViewDate.getText()
                        + " Order time: " + textViewTime.getText() + " Number of sits: " + numOfSitsTV.getText();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{restaurant.Contact_email});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Table Reservation");
                intent.putExtra(Intent.EXTRA_TEXT, mailText);

                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });

    }

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

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
                    String selectedTime = String.format("%02d:%02d", hours, minutes);
                    textViewTime.setText(selectedTime);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.order_menu, menu);
        return true;
    }

    public class OrderAdapter extends ArrayAdapter {

        List<Pair<Integer, Integer>> objects;

        public OrderAdapter(Context context, List<Pair<Integer, Integer>> objects) {
            super(context, android.R.layout.simple_list_item_1, android.R.id.text1, objects);
            this.objects = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null){
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.order_listview_layout, null);
                TextView itemId = (TextView) convertView.findViewById(R.id.orderItemID_TV);

                MenuItem item = MyDAO.getInstance().getMenuItem(objects.get(position).first);

                itemId.setText(item.getName());
                TextView itemQuantity = (TextView) convertView.findViewById(R.id.orderItemQuantity_TV);
                itemQuantity.setText(String.valueOf(objects.get(position).second));
                itemQuantity.setPadding(10, 0, 0, 0);

            }
            return convertView;
        }
    }
}