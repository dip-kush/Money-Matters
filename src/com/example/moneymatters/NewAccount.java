package com.example.moneymatters;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewAccount extends Activity {

	private long RowID3;
	private EditText name,bal;
	private Button add;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_account);
        name=(EditText)findViewById(R.id.accnt_name);
        bal=(EditText)findViewById(R.id.accnt_balance);
        add=(Button)findViewById(R.id.add_accnt);
        
        
        add.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
			
				AsyncTask<Object, Object, Object> AddNames=
						new AsyncTask<Object, Object, Object>(){

							@Override
							protected Object doInBackground(Object... params) {
								addAccount();// TODO Auto-generated method stub
								return null;
							}
							protected void onPostExecute(Object result){
								finish();
							}
					// TODO Auto-generated method stub
				};
				AddNames.execute((Object[])null);// TODO Auto-generated method stub
				
			}
		});
        
        
        Log.d("get", "value takes");
        Bundle extras=getIntent().getExtras();
        Log.d("got", "got the extra value");
       if(extras!=null){
    	   Log.d("gotton ", "extra not nulol");
        	RowID3=extras.getLong(BankAccount.ROW_ID3);
        	BankConnect con=new BankConnect(NewAccount.this);
        	con.open();
        	Cursor c=con.getOneAccount(RowID3);
        	c.moveToFirst();
        	name.setText(c.getString(1));
        	bal.setText(""+c.getInt(2));
        	con.close();
        	
        	}
        
    }

 public void addAccount(){
    	
	 BankConnect connect=new BankConnect(this);
    	if(getIntent().getExtras()==null){
    		connect.addAccount(name.getText().toString(),Integer.parseInt(bal.getText().toString()));
    	}
    	else
    	{
    		connect.updateAccount(RowID3, name.getText().toString(), Integer.parseInt(bal.getText().toString()));
    	}
    
    	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_account, menu);
        return true;
    }
}
