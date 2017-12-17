package com.rajat.e_subzi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rajat.e_subzi.Volley.VolleyClick;


public class Login extends ActionBarActivity {
    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String tokens=pref.getString("token","no tokens");
        if(!tokens.equals("no tokens")){
            if(pref.getString("type","").equals("Shopkeeper")){
                VolleyClick.findProductsClick(pref.getString("userId",""),this);
            }
            else{
                VolleyClick.findDiscountsClick(pref.getString("userId",""),this);
            }
        }
        setContentView(R.layout.activity_login2);
        getSupportActionBar().hide();

//        getActionBar().hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
    public void login(View view){
        EditText user=(EditText) findViewById(R.id.username);
        EditText pass=(EditText) findViewById(R.id.password);
        String userName =user.getText().toString();
        String password = pass.getText().toString();
        if(!userName.equals("") && !password.equals("") ) {
        VolleyClick.loginClick(user.getText().toString(), pass.getText().toString(),this);
        }else{
            Toast.makeText(Login.this, "Some fields are empty", Toast.LENGTH_SHORT).show();
        }
    }
    public void signup(View view){
        Intent intent=new Intent(this,SignUp.class);
        startActivity(intent);
    }
}
