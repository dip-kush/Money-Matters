package com.example.moneymatters;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class AddBorrower extends Activity {

	private long RowID;
	EditText dateDisplay;
	static final int DATE_DIALOG_ID=0;
	EditText nameborrow,amountborrow;
	Button btn;
	String amount;
	int amnt;
	EditText phonefield;
	Button add_contact;
	static final int PICK_CONTACT_REQUEST=1;
	String newyear;
	String day;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_borrow);
        dateDisplay=(EditText)findViewById(R.id.date_field);
        nameborrow=(EditText)findViewById(R.id.name_field);
        amountborrow=(EditText)findViewById(R.id.amount_field);
        phonefield=(EditText)findViewById(R.id.phone_field);
        btn=(Button)findViewById(R.id.borrow_btn);
        add_contact=(Button)findViewById(R.id.contacts);
       
       
        //adding from contact
        add_contact.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
	            pickContactIntent.setType(Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
	            startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);	// TODO Auto-generated method stub
				
			}
		});
        
        
        
        
        
        
        
        
        //displaying a calender on touching a  date Edittext
        
        dateDisplay.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				if(v==dateDisplay)
					showDialog(DATE_DIALOG_ID);
				
					// TODO Auto-generated method stub
				return false;
			}
		});
        
        //finishes here
        //action of button
        btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				AsyncTask<Object, Object, Object> AddNames=
						new AsyncTask<Object, Object, Object>(){

							@Override
							protected Object doInBackground(Object... params) {
								addNames();// TODO Auto-generated method stub
								return null;
							}
							protected void onPostExecute(Object result){
								finish();
							}
					// TODO Auto-generated method stub
				};
				AddNames.execute((Object[])null);
				
			}
		});
    
        //finishes here
        Bundle extras=getIntent().getExtras();
        if(extras!=null){
        	RowID=extras.getLong(ViewBorrow.ROW_ID);
        	DbConnector connect=new DbConnector(this);
        	connect.open();
        	Cursor c=connect.getOneName(RowID);
        	nameborrow.setText(c.getString(1));
        	amountborrow.setText(""+c.getInt(2));
        	dateDisplay.setText(c.getString(3));
        	phonefield.setText(c.getString(4));
        	
        	
        	
        }
        
        
        
        }
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request it is that we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Get the URI that points to the selected contact
                Uri contactUri = data.getData();
                // We only need the NUMBER column, because there will be only one row in the result
                String[] projection = { Phone.NUMBER,ContactsContract.Contacts.DISPLAY_NAME};

                // Perform the query on the contact to get the NUMBER column
                // We don't need a selection or sort order (there's only one result for the given URI)
                // CAUTION: The query() method should be called from a separate thread to avoid blocking
                // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
                // Consider using CursorLoader to perform the query.
                Cursor cursor = getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                // Retrieve the phone number from the NUMBER column
                int column = cursor.getColumnIndex(Phone.NUMBER);
               int col=cursor.getColumnIndex( ContactsContract.Contacts.DISPLAY_NAME);
                String number = cursor.getString(column);
                String name=cursor.getString(col);
                

                phonefield.setText(number);
                nameborrow.setText(name);
                
                // Do something with the phone number...
            }
        }
    }
    

    
    //Add the data to the table
    
    public void addNames(){
    	
    	DbConnector connect=new DbConnector(this);
    	if(getIntent().getExtras()==null){
    		connect.insertValue(nameborrow.getText().toString(),Integer.parseInt(amountborrow.getText().toString()),dateDisplay.getText().toString(),phonefield.getText().toString());
    	}
    	else
    	{
    		connect.update(RowID, nameborrow.getText().toString(), Integer.parseInt(amountborrow.getText().toString()), dateDisplay.getText().toString(),phonefield.getText().toString());
    	}
    
    	}
    
    
    
    //Dialog create method
    @Override
    protected Dialog onCreateDialog(int id){
    	Calendar c=Calendar.getInstance();
    	int year=c.get(Calendar.YEAR);
    	int month=c.get(Calendar.MONTH);
    	int day=c.get(Calendar.DAY_OF_MONTH);
    	switch(id){
    	case DATE_DIALOG_ID:
    		return new DatePickerDialog(this, setDateListener, year, month, day);
    		
    	}
    	return null;
    }
    private DatePickerDialog.OnDateSetListener setDateListener=new DatePickerDialog.OnDateSetListener() {
		
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			if(monthOfYear<10){
			 newyear="0"+String.valueOf(monthOfYear+1);
			}
			else
			{
			newyear=String.valueOf(monthOfYear+1);	
			}
			
			if(dayOfMonth<10){
				day="0"+String.valueOf(dayOfMonth);
			}
			else{
				day=String.valueOf(dayOfMonth);
			}
			
			String date_selected=String.valueOf(year)+"-"+newyear+"-"+day;
			dateDisplay.setText(date_selected);
			
		}
	};

	//finishes
	@Override 
	public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_borrow, menu);
        return true;
    }
}
