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

public class ViewBorrow extends ListActivity {

	private static final String TAG="gettingnames";
	public static final String ROW_ID="row_id";
	private ListView listv;
	private SimpleCursorAdapter sAdapter;
	//private static final String TAG="just for tiem pass";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.view_borrow);
        
        DbConnector connect=new DbConnector(this);
      /*  connect.open();
        
        Cursor c=connect.getOneName(4);
        	c.moveToFirst();
        	displayContact(c);
        connect.close();
        */	
        //connect.insertValue("mohan", 100, "98 june");
       // connect.insertValue("Suresh", 56, "54 march");
        //connect.insertValue("suraj", 54, "78 feb");
       //   connect.update(2, "deepak", 60, "34 june");
      //connect.insertValue("deepak", 50, "26 june");
        
        //connect.insertValue("ram", 55, "24 june");
      /*  connect.open();
        Cursor c=connect.getAllNames();
        
        if(c.moveToFirst()){
			do{
				displayContact(c);
			}while(c.moveToNext());
        }
        connect.close();
        */
        
        //printing the values of the database in the list........
        connect.open();
        Cursor c=connect.getTheTime();
        String [] names=c.getColumnNames();
        for(int i=0;i<names.length;i++){
        Log.d(TAG, names[i]);	
        }
        
        String[] from=new String[]{"name","daily_amount","dates"};
        
        int[] to=new int[]{R.id.textView1,R.id.textView2,R.id.textView4 };
        sAdapter=new SimpleCursorAdapter(this, R.layout.view_borrow, c, from, to);
        setListAdapter(sAdapter);
        listv=getListView();
        registerForContextMenu(listv);
        	listv.setOnItemClickListener(new OnItemClickListener() {
        		
        		public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long id) {
					Intent i=new Intent(ViewBorrow.this,BorrowDetails.class);
					i.putExtra(ROW_ID, id);
					startActivity(i);
        			// TODO Auto-generated method stub
				}	
				});
        connect.close();
        
        
     /*   
        //test code
        DbConnector con=new DbConnector(this);
        con.open();
        Cursor cursor=con.getTheTime();
        if(cursor.moveToFirst()){
        
    			do{
    				Log.d(TAG, cursor.getString(cursor.getColumnIndex("diff")));
    			}while(c.moveToNext());
            
        
        }
        con.close();
        
       */ 
        //finishes the code
       }
    //finishes the code
    
    
    //on resume of this activity 
    protected void onResume(){
    	super.onResume();
    	new LoadNames().execute((Object[])null);
    	
    }
    protected void onStop(){
    	Cursor cursor=sAdapter.getCursor();
    	if(cursor!=null)
    		cursor.deactivate();
    	
    	sAdapter.changeCursor(null);
    	super.onStop();
    }
    
    //Asyncnchronous class
    public class LoadNames extends AsyncTask<Object, Object, Cursor> {

    	DbConnector connect= new DbConnector(ViewBorrow.this);
		@Override
		protected Cursor doInBackground(Object... params) {
			
			connect.open();
			return connect.getAllNames();
		// TODO Auto-generated method stub
		
		}
		 
		protected void onPostExecute(Cursor result){
		sAdapter.changeCursor(result);
		connect.close();
			
		}
    	
    }
    
    

    public void displayContact(Cursor c){
    	Toast.makeText(this,"id: "+c.getString(0)+"\n"+
    							"Name:"+c.getString(1)+c.getInt(2),Toast.LENGTH_SHORT).show();
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
    	DbConnector connect= new DbConnector(ViewBorrow.this);
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.edit:
            	Intent i=new Intent(ViewBorrow.this,AddBorrower.class);
				i.putExtra(ROW_ID, info.id);
				startActivity(i);
                return true;
            case R.id.delete:
                
                connect.deleteContact(info.id);
                new LoadNames().execute((Object[])null);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    
   public boolean onOptionsItemSelected(MenuItem item){
	   switch(item.getItemId()){
	   case R.id.add_row:
		   Intent i=new Intent(this,AddBorrower.class);
		   startActivity(i);
		  return true;
		  
	
	   case R.id.total_money:
		   int total=0;
		   DbConnector connect=new DbConnector(ViewBorrow.this);
		   connect.open();
	        Cursor c=connect.getAllNames();
	        
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
