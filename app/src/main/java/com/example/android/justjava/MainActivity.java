package com.example.android.justjava;

import android.content.Context;
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
    int qty;
    int price;
    int base;
    boolean creamstatus;
    boolean chocolate;
    String myname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        qty=1;
        creamstatus=false;
        chocolate=false;
        myname="";
    }
    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(int quantity) {
        base=5;
        if(creamstatus){
            base=base+1;
        }
        if(chocolate){
            base+=2;
        }
        int price = quantity * base;
        return price;
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //int numberOfCoffees=5;
        //display(numberOfCoffees);

        //String priceMsg="Total: $ "+price+"\n\nThank you! :)";
        //displayPrice(calculatePrice(qty));


        checkCream();
        getName();
        checkChocolate();
        price=calculatePrice(qty);

        String str=createOrderSummary();
//        displayMessage(str);

        String mysub="JustJava order for "+myname;

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
       // intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, mysub);
        intent.putExtra(Intent.EXTRA_TEXT,str);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method is to get the name of the user
     */
    private void getName(){
        EditText mytxt=(EditText) findViewById(R.id.entername);
        myname=mytxt.getText().toString();
    }

    /**
     * This method is to check status of checkbox for whipped cream
     */
    private void checkCream(){
        creamstatus = ((CheckBox) findViewById(R.id.checkbox1)).isChecked();
    }

    /**
     * This method is to check status of checkbox for chocolate
     */
    private void checkChocolate(){
        chocolate = ((CheckBox) findViewById(R.id.checkbox2)).isChecked();
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private void displayMessage(String msg) {
        TextView quantityTextView = (TextView) findViewById(R.id.order_summary_text_view);
        quantityTextView.setText(msg);
    }

    public void increment(View view)
    {
        if(qty<=39)
            qty++;
        else{
            Context context = getApplicationContext();
            CharSequence text = "Cannot order more than 40 cups";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        display(qty);
    }

    public void decrement(View view)
    {
        if(qty>1)
            qty--;
        else{
            Context context = getApplicationContext();
            CharSequence text = "Minimum order is 1 cup";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        display(qty);
    }
    private String createOrderSummary(){
        String s="Name: "+myname+"\nAdd whipped cream? "+creamstatus+"\nAdd chocolate? "+chocolate+"\nQuantity: "+qty+"\nTotal: $ "+price+"\n\nThank You! :)";
        return s;
    }
    /**
     * This method displays the given price on the screen.
     */
   /* private void displayPrice(int number) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }*/
}
