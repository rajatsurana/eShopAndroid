package com.rajat.e_subzi;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rajat.e_subzi.Volley.VolleyClick;

import java.util.HashMap;


public class SignUp extends ActionBarActivity implements AdapterView.OnItemSelectedListener{
    private String[] dropDown={"Login as","Customer","Shopkeeper"};
    HashMap<String,String> typeofuser=new HashMap<String,String>();
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        typeofuser.put("Customer", "1");
        typeofuser.put("Shopkeeper", "2");
        spinner=(Spinner) findViewById(R.id.type);

        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, dropDown);
        adapter_state
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter_state);
        spinner.setOnItemSelectedListener(this);
        spinner.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_sign_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        spinner.setSelection(position);
        String selState = (String) spinner.getSelectedItem();
        Log.d("selected item", selState);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
    public void signup(View view){
        EditText user=(EditText) findViewById(R.id.username);
        EditText pass=(EditText) findViewById(R.id.password);
        EditText address=(EditText) findViewById(R.id.address);
        EditText phoneNum=(EditText) findViewById(R.id.phoneNumber);
        String userName =user.getText().toString();
        String password = pass.getText().toString();
        String addressss= address.getText().toString();
        String phoneNumber =phoneNum.getText().toString();
        if(!userName.equals("") && !password.equals("") && !addressss.equals("") && !phoneNumber.equals("")) {
            if (userName.length() > 5 && password.length() > 7 && addressss.length()>7 && phoneNumber.length()>6) {
            VolleyClick.signUp(user.getText().toString(), pass.getText().toString(), address.getText().toString(), phoneNum.getText().toString(), "Customer", this);
            }else{
                if(userName.length()<=5){
                    Toast.makeText(SignUp.this,"Username should have atleast 5 characters",Toast.LENGTH_SHORT).show();
                }else if(password.length()<=7){
                    Toast.makeText(SignUp.this,"Password should have atleast 7 characters",Toast.LENGTH_SHORT).show();
                }else if(addressss.length()<=7){
                    Toast.makeText(SignUp.this,"Address should have atleast 7 characters",Toast.LENGTH_SHORT).show();
                }else if(phoneNumber.length()<=6){
                    Toast.makeText(SignUp.this,"Phone number should have atleast 6 characters",Toast.LENGTH_SHORT).show();
                }
            }

        }else{
            Toast.makeText(SignUp.this,"Some fields are empty",Toast.LENGTH_SHORT).show();
        }
    }
}
