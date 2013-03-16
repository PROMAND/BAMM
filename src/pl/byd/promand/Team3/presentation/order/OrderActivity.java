package pl.byd.promand.Team3.presentation.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.actionbarsherlock.app.SherlockActivity;
import pl.byd.promand.Team3.R;
import pl.byd.promand.Team3.infrastructure.order.OrderBean;

public class OrderActivity extends SherlockActivity {

    private String name;
    private String phoneNumber;
    private OrderBean order;

    private EditText nameET;
    private EditText phoneNumberET;

    private Button button;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);

        button = (Button)findViewById(R.id.orderConfirmButton);
        nameET = (EditText)findViewById(R.id.orderEditTextName);
        phoneNumberET = (EditText)findViewById(R.id.orderEditTextPhoneNumber);

        //order = new OrderBean();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameET.getText().toString();
                phoneNumber = phoneNumberET.getText().toString();

                String mailText = name + "" + phoneNumber;

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, "tosha123@inbox.lv");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Table Reservation");
                intent.putExtra(Intent.EXTRA_TEXT, mailText);

                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });

    }
}