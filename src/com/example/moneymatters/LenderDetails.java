package com.example.moneymatters;



import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LenderDetails extends Activity {

	String name;
    private long RowId=0;
	private TextView name_view;
	private TextView amount_view;
	int totalAmount=0;
	int aggregate=0;
	String phoneno;
	Button call,text;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
       // Toast.makeText(this, "This is the total money which this ", Toast.LENGTH_SHORT).show();
        name_view=(TextView)findViewById(R.id.name_view);
        amount_view=(TextView)findViewById(R.id.amount_view);
      //  test_view=(TextView)findViewById(R.id.test_view);
      //  date_view=(TextView)findViewById(R.id.date_view);
        call=(Button)findViewById(R.id.call);
        text=(Button)findViewById(R.id.text_msg);
        
        
        Bundle extras=getIntent().getExtras();
        
        RowId=extras.getLong(ViewLender.ROW_ID2);
        
        //
        ConnectIt connect=new ConnectIt(this);
        connect.open();
        Cursor crsr=connect.getOneName(RowId);
        crsr.moveToFirst();
        phoneno=crsr.getString(4);
        name=crsr.getString(1);
        connect.close();
        
        
        Toast.makeText(this, "This is the total money which Mr. " +name+" owe to you", Toast.LENGTH_SHORT).show();
        
        
  call.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
			
			
		    	Intent callIntent=new Intent(Intent.ACTION_VIEW,Uri.parse("tel:"+phoneno));
		    	startActivity(callIntent);
// TODO Auto-generated method stub
				
			}
		});
        
        //texting
        
        text.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
		    	Intent smsIntent = new Intent(Intent.ACTION_VIEW);
		    	smsIntent.setType("vnd.android-dir/mms-sms");
		    	//smsIntent.setData(Uri.parse("sms:"));
		    	smsIntent.putExtra("address", phoneno);
		    	smsIntent.putExtra("sms_body","Hey Mr." +name+  "You owe me a sum of "+aggregate+ "Rs");
		    	startActivity(smsIntent);	// TODO Auto-generated method stub
				
			}
		});
        
    }
	
	
	 public void calculateAmount(){
	    	ConnectIt connect=new ConnectIt(this);
	    	connect.open();
	    	Cursor c=connect.getAllNames();
	    	Cursor cursor=connect.getOneName(RowId);
	    	String borrwr=cursor.getString(1);
	    	if(c.moveToFirst()){
	    		do{
	    		if(borrwr.equals(c.getString(1)))
	    			aggregate+=c.getInt(2);
	    		
	    	}while(c.moveToNext());
	    	
	    	}	
	    	connect.close();
	    }
	    
	 
	 protected void onResume(){
	    	super.onResume();
	    	new loadallContact().execute(RowId);
	    	}

	    private class loadallContact extends AsyncTask<Long, Object, Cursor>{

	    	
	    	ConnectIt connect= new ConnectIt(LenderDetails.this);
	    	
			@Override
			protected Cursor doInBackground(Long... params) {
				
				connect.open();
				calculateAmount();
				return connect.getOneName(params[0]);
				// TODO Auto-generated method stub
				
			}
			protected void onPostExecute(Cursor result){
				
				super.onPostExecute(result);
				result.moveToFirst();
				
				int nameIndex=result.getColumnIndex("name");
				int amountIndex=result.getColumnIndex("daily_amount");
				
			//	int dateIndex=result.getColumnIndex("dates");
				
				name_view.setText(result.getString(nameIndex).toUpperCase());
				//date_view.setText(result.getString(dateIndex));
				totalAmount=result.getInt(amountIndex);
				amount_view.setText(""+aggregate);
			  
				
				
				result.close();
				connect.close();
				
			}
	    	
	    }
	 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lender_details, menu);
        return true;
    }
}
