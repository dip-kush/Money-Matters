package com.example.moneymatters;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BorrowDetails extends Activity {

	String name;
	private long rowId=0;
//	private long RowId=0;
	private TextView name_view;
	private TextView amount_view;
	int totalAmount=0;
	int aggregate=0;
	String phoneno;
	Button call,text;
	//private TextView date_view;
	//private TextView test_view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        name_view=(TextView)findViewById(R.id.name_view);
        amount_view=(TextView)findViewById(R.id.amount_view);
      //  test_view=(TextView)findViewById(R.id.test_view);
      //  date_view=(TextView)findViewById(R.id.date_view);
        call=(Button)findViewById(R.id.call);
        text=(Button)findViewById(R.id.text_msg);
        
        
        Bundle extras=getIntent().getExtras();
        
        rowId=extras.getLong(ViewBorrow.ROW_ID);
        if(rowId!=0){
        //
        DbConnector connect=new DbConnector(this);
        connect.open();
        Cursor crsr=connect.getOneName(rowId);
        crsr.moveToFirst();
        phoneno=crsr.getString(4);
        name=crsr.getString(1);
        
        connect.close();
        }
        
        Toast.makeText(this, "This is the total money which you owe " +name,Toast.LENGTH_SHORT).show();
        
       
        
        
        
        
        
        //calling
        
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
		    	smsIntent.putExtra("sms_body","Hey Mr. "+name+ "I will return your " +aggregate+ " Rs. shortly " );
		    	startActivity(smsIntent);	// TODO Auto-generated method stub
				
			}
		});
        
        
        
     }
    
    public void calculateAmount(){
    	DbConnector connect=new DbConnector(this);
    	connect.open();
    	Cursor c=connect.getAllNames();
    	Cursor cursor=connect.getOneName(rowId);
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
    	new loadContact().execute(rowId);
    	}
    
    
    private class loadContact extends AsyncTask<Long, Object, Cursor>{

    	
    	DbConnector connect=new DbConnector(BorrowDetails.this);
    	
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
        getMenuInflater().inflate(R.menu.details, menu);
        return true;
    }
}
