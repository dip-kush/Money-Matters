package com.example.moneymatters;



import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class BankAccount extends ListActivity {

public static final String ROW_ID3="row_id";
	private static final String TAG="bank";
	private SimpleCursorAdapter bAdapter;
	private ListView listb;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_bank_account);
        
      
        BankConnect acc = new BankConnect(this);
        Log.d(TAG, "here i am inserting ");
      // acc.addAccount("Surathkal", 300);
       //acc.addAccount("indore", 566);
        
      acc.open();
        Cursor c=acc.getAllAccounts();
        String[] from = new String[]{
        		"name","balance"
        };
        int[] to = new int[]{
        		R.id.acc_name,R.id.acc_balance
        };
        bAdapter=new SimpleCursorAdapter(this, R.layout.activity_bank_account, c, from, to);
        setListAdapter(bAdapter);
        
       listb = getListView();
       registerForContextMenu(listb);
       listb.setOnItemClickListener(new OnItemClickListener() {
    	   public void onItemClick(AdapterView<?> arg0, View arg1, int position , long id){
    		   Intent i=new Intent(BankAccount.this,AccountDetails.class);
				i.putExtra(ROW_ID3, id);
				startActivity(i);// TODO Auto-generated method stub

    		   
    	   }
    	   
	});
        
        acc.close();
        
        
       
        
        
    }
    
    protected void onResume(){
    	super.onResume();
    	new LoadNames().execute((Object[])null);
    	
    }
    protected void onStop(){
    	Cursor cursor=bAdapter.getCursor();
    	if(cursor!=null)
    		cursor.deactivate();
    	
    	bAdapter.changeCursor(null);
    	super.onStop();
    }
    
    //Asyncnchronous class
    public class LoadNames extends AsyncTask<Object, Object, Cursor> {

    	BankConnect	connect= new BankConnect(BankAccount.this);
		@Override
		protected Cursor doInBackground(Object... params) {
			
			connect.open();
			return connect.getAllAccounts();
		// TODO Auto-generated method stub
		
		}
		 
		protected void onPostExecute(Cursor result){
		bAdapter.changeCursor(result);
		connect.close();
			
		}
    	
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_borrow, menu);
        return true;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu,View view,ContextMenuInfo info){
    	super.onCreateContextMenu(menu, view, info);
    	getMenuInflater().inflate(R.menu.context_menu, menu);
    	
    }
    public boolean onContextItemSelected(MenuItem item) {
    	BankConnect connect= new BankConnect(BankAccount.this);
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.edit:
            	Intent i=new Intent(BankAccount.this,NewAccount.class);
				i.putExtra(ROW_ID3, info.id);
				startActivity(i);
                return true;
            case R.id.delete:
                
                connect.deleteAccount(info.id);
                new LoadNames().execute((Object[])null);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    
   public boolean onOptionsItemSelected(MenuItem item){
	   switch(item.getItemId()){
	   case R.id.add_row:
		   Intent i=new Intent(this,NewAccount.class);
		   startActivity(i);
		  return true;
		  
	
	   case R.id.total_money:
		   int total=0;
		   BankConnect connect=new BankConnect(BankAccount.this);
		   connect.open();
	        Cursor c=connect.getAllAccounts();
	        
	        if(c.moveToFirst()){
				do{
					total+=c.getInt(2);
				}while(c.moveToNext());
	        }
	        connect.close();
		  
	        Toast.makeText(this, "your total amount worth is "+total, Toast.LENGTH_SHORT).show();
	        
	        
	        
		
		   
	   }
	 return true;  
   }
}
