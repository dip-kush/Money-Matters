package com.example.moneymatters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ConnectIt {
	//static final int  min=7;
	public static final String ROW_ID2="_id";
	public static final String ROW_NAME2="name";
	public static final String ROW_PHONE2="phone";
	//public static final String ROW_AMOUNT="daily_amount";
	
	public static final String DATABASE_NAME="money_lender";
	public static final String TABLE_NAME2="lender";
	public static final int DATABASE_VERSION=2;
	
	private MyOpenHelper openHelper;
	private SQLiteDatabase database;
	
	public ConnectIt(Context context){
		openHelper=new MyOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		
	}
	public void open() throws SQLException{
		database=openHelper.getWritableDatabase();
	}
	//closing a database
	public void close(){
		if(database!=null){
			database.close();
		}
	}
		//inserting  values in the database
		public void insertValue(String name,int daily_amount,String dates,String phone){
			ContentValues newcon=new ContentValues();
			newcon.put(ROW_NAME2, name);
			newcon.put("daily_amount", daily_amount);
			newcon.put("dates", dates);
			newcon.put("phone", phone);
			open();
			database.insert(TABLE_NAME2, null, newcon);
			close();
		}
		
	//deleting value from the database
		
		public void update(long id,String name,int daily_amount,String dates,String phone){
			ContentValues editcon=new ContentValues();
			editcon.put(ROW_NAME2, name);
			editcon.put("daily_amount", daily_amount);
			editcon.put("dates", dates);
			editcon.put("phone", phone);
			open();
			database.update(TABLE_NAME2, editcon, "_id="+id, null);
			close();
			
		}
		
		//public void gettting all the names of borrowed money
		
		public Cursor getAllNames(){
		
			return database.query(TABLE_NAME2, null, null, null, null, null, "name");
	
		}
		public Cursor getOneName(long id){
			Cursor mcursor= database.query(TABLE_NAME2, null, "_id="+id, null,null,null,null);
		
			
			if(mcursor!=null){
				mcursor.moveToFirst();
			}
			return mcursor;
			
		}
		/*public Cursor getTheTime(){
			String query="Select _id,name,daily_amount,dates,phone from borrow where (julianday(Date('now')) - julianday(dates))>?";
			return database.rawQuery(query,new String[]{ "5"});
			
		}
	
		
		public Cursor getDistinctName(){
			Cursor cursor=database.rawQuery("Select distinct name,_id from borrow", null);
			if(cursor!=null){
				cursor.moveToFirst();
			}
			return cursor;
			
		}
		*/
		public void deleteContact(long id){
			open();
			database.delete(TABLE_NAME2, "_id="+id, null);
			close();
		}
		
}



