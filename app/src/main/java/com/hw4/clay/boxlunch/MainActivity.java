package com.hw4.clay.boxlunch;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity {

    // Declare a boolean to check if the order has been calculated
    // Don't allow user to email unless the order is calculated.
    // declare this boolean here because it will be used in several methods
    boolean orderCalculated = false;

    // Declare the orderMessage here because it will be used in several methods
    String orderMessage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // The "placeOrder" method is called when the order button is clicked.
    public void placeOrder(View view) {

        // set the 'orderCalculated' boolean to false, and
        // set it to true once the order has been calculated.
        orderCalculated = false;

        // Check to see that a sandwich size has been selected - if not, send a toast message
        // (A sandwich size must be selected to calculate the total amount of the box lunch)

        RadioGroup sandwichSizeGp = (RadioGroup) findViewById(R.id.radioGroupSize);
        if (sandwichSizeGp.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(), "Please Select Sandwich Size", Toast.LENGTH_LONG).show();
        } else {
            // Continue on to calculate the total for the box lunch
            // Set Box Lunch Base Price
            double basePrice = 6.00;
            // Initialize the output message
            orderMessage = "";
            orderMessage += "\n" + "Your Box Lunch Order for Today:";

            // Get a reference to the order button
            final Button orderButton = (Button) findViewById(R.id.btnOrder);

            // Find out if the user wants a soft drink.
            // then add appropriate text to the order message
            CheckBox softDrinkCheckBox = (CheckBox) findViewById(R.id.ckDrink);
            boolean wantsSoftDrink = softDrinkCheckBox.isChecked();
            if (wantsSoftDrink) {
                orderMessage += "\n" + "-->  Soft Drink - the usual";
                basePrice += 1.50;
            } else {
                orderMessage += "\n" + "-->  No Soft Drink";
            }

            // TODO Find out if user wants a salad
            // If they do not want a salad, take 1.50 off the price
            // If they want a salad, no change in price.
            // then add appropriate text to the order message
            CheckBox saladCheckBox = (CheckBox)findViewById(R.id.ckSalad);
            boolean wantsSalad = saladCheckBox.isChecked();
            if(wantsSalad){
                orderMessage += "\n" +"--> Side Salad";
            } else {
                orderMessage += "\n" +"--> No Side Salad";
                basePrice += -1.50;
            }

            // TODO Overview:
            // Find out Which size sandwich is ordered, to do this:
            // --get selected radio button from radioGroup
            //     (A reference to the RadioGroup has already been declared above)
            // TODO Get the radio button id (it's an integer)
            int checkedRadio = sandwichSizeGp.getCheckedRadioButtonId();

            // TODO Find out which button was pressed by returned id
            View radioButton = sandwichSizeGp.findViewById(checkedRadio);

            // TODO Get the text of the radio button, and convert it to a string
            int radioId = sandwichSizeGp.indexOfChild(radioButton);
            RadioButton btn = (RadioButton) sandwichSizeGp.getChildAt(radioId);
            String sandwichText = (String) btn.getText();

            // TODO Check for a sandwich size of "Large" (be careful of case)
            // if the sandwich size is Large, add 2.00 to the price
            if(sandwichText.equals("Large")){
                basePrice += 2.00;
                orderMessage += "\n"+"--> Large Sandwich";
            } else {
                // TODO Add the appropriate text to the order message
                orderMessage += "\n"+"--> Regular Sandwich";
            }

            // TODO Find out if user wants soup
            // if they want soup, add 1 to the price
            // if they do not want soup, no change to price
            // then add appropriate text to the order message
            CheckBox soupCheck = (CheckBox) findViewById(R.id.ckSoup);
            boolean wantsSoup = soupCheck.isChecked();
            if(wantsSoup){
                basePrice += 1.00;
                orderMessage += "\n"+"--> Soup";
            }else{orderMessage += "\n"+"--> No Soup";}

            // TODO Find out if user wants dessert
            // if they want dessert, add 1 to the price
            // if they do not want dessert, no change to price
            // then add appropriate text to the order message
            CheckBox desertCheck = (CheckBox) findViewById(R.id.ckDesert);
            boolean wantsDesert = desertCheck.isChecked();
            if(wantsDesert){
                basePrice += 1.00;
                orderMessage += "\n"+"--> Desert";
            }else{orderMessage += "\n"+"--> No Desert";}


            // TODO Print the order message
            // use "String.format" to format the price to two decimal places
            // Be sure to add the correct text to the remainder of the message
            // (see the screen shots)
            String result =  orderMessage  + "\n Order Total: "+ String.format("%.2f", basePrice);
            TextView orderText = findViewById(R.id.tvOrder);
            orderText.setText(result);

            //TODO set the orderCalculated boolean to true
            orderCalculated = true;

        }

    }
    // The 'emailReceipt' method is called when user presses the email button
    public void emailReceipt(View view) {

        //TODO if the orderCalculated boolean is false, send the user a toast
        //  indicating that they must calculate the order first.
        if (!orderCalculated) {
            Context context = getApplicationContext();
            CharSequence text = "You must first calculate your total!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        //TODO the else will send the email (most of this is done for you)
        else {

            // Declare an Intent to launch an email app.
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            // Set data to URI "mailto:" so that only email apps handle this
            intent.setData(Uri.parse("mailto:"));
            // Set the email subject line
            intent.putExtra(Intent.EXTRA_SUBJECT, "Box Lunch Order Receipt");

            //TODO Send the order summary in the email body, AS EXTRA_TEXT

            // Start the intent
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }
}




