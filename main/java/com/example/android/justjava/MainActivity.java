package com.example.android.justjava;

/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.justjava;
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int numberOfCoffees = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String priceMessage = createOrderSummary(numberOfCoffees * (5 + toppingsPrice()));
        //displayMessage(priceMessage);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + nameEntered());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }

    public void increment(View view) {
        numberOfCoffees++;
        displayQuantity(numberOfCoffees);
    }


    private int toppingsPrice() {
        CheckBox WC = (CheckBox) findViewById(R.id.whipped_cream);
        CheckBox CH = (CheckBox) findViewById(R.id.Chocolate);
        int topPrice = 0;
        if (WC.isChecked()) {
            topPrice++;
        }
        if (CH.isChecked()) {
            topPrice += 2;
        }
        return topPrice;
    }

    public void decrement(View view) {
        Toast toast = Toast.makeText(getApplicationContext(), "You cannot order less than 1 coffe!", Toast.LENGTH_SHORT);
        if (numberOfCoffees > 1) {
            numberOfCoffees--;
        } else {
            numberOfCoffees = 1;
            toast.show();

        }
        displayQuantity(numberOfCoffees);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method displays the given text on the screen.
     */
    //private void displayMessage(String message) {
   //     TextView orderSummaryTextView = (TextView) findViewById(R.id.price_text_view);
  //      orderSummaryTextView.setText(message);
  //  }

    private String isChecked() {
        CheckBox WC = (CheckBox) findViewById(R.id.whipped_cream);
        CheckBox CH = (CheckBox) findViewById(R.id.Chocolate);
        String toppings = "Toppings:\n";
        if (WC.isChecked()) {
            toppings += "Whipped Cream\n";
        }
        if (CH.isChecked()) {
            toppings += "Chocolate\n";
        }
        if (!CH.isChecked() && !WC.isChecked()) {
            toppings += "No toppings\n";
        }

        return toppings;
    }

    private String nameEntered() {
        String name;
        EditText et = (EditText) findViewById(R.id.the_name_of_buyer);
        name = et.getText().toString();
        return name;
    }

    private String createOrderSummary(int price) {
        return String.format("%s \nQuantity: %d\n%sTotal: %d\n %s",
                getString(R.string.order_name, nameEntered()),
                numberOfCoffees,
                isChecked(),
                price,
                getString(R.string.thank_you));
    }
}