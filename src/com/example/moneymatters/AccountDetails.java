package com.example.moneymatters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AccountDetails extends Activity {

	final Context context = this;
	private long RowID;
	private TextView name,money;
	int accountmoney;
	Button vdrw,dposit;
	//EditText input;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_details);
        
        name=(TextView)findViewById(R.id.bank_name);
        money=(TextView)findViewById(R.id.bank_balance);
        vdrw=(Button)findViewById(R.id.withdraw);
        dposit=(Button)findViewById(R.id.deposit);
        
       //input=(EditText)findViewById(R.id.transaction);
        
        
        Bundle extras=getIntent().getExtras();
        if(extras!=null){
        	RowID=extras.getLong(BankAccount.ROW_ID3);
        	}
        
        
        BankConnect connect= new BankConnect(this);
        connect.open();
        Cursor c=connect.getOneAccount(RowID);
        c.moveToFirst();
        
        name.setText(c.getString(1));
        accountmoney=c.getInt(2);
        money.setText(""+accountmoney);
        connect.close();
        
        
        
        vdrw.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
			
				LayoutInflater li = LayoutInflater.from(AccountDetails.this);
				View promptsView = li.inflate(R.layout.prompts, null);
 
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);
				alertDialogBuilder.setView(promptsView);
				 
				final EditText userInput = (EditText) promptsView
						.findViewById(R.id.editTextDialogUserInput);
				alertDialogBuilder.setTitle("WithDraw");
				alertDialogBuilder.setMessage("Enter the Withdrawal Amount");
				alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK",
				  new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog,int id) {
					// get user input and set it to result
					// edit text
				    int change=Integer.parseInt(userInput.getText().toString());
				    
					//input.setText(""+change);
					int total=accountmoney-change;
					BankConnect connect=new BankConnect(AccountDetails.this);
					connect.updateAccount(RowID, name.getText().toString(), total);
					finish();
					
				    }
				  })
				.setNegativeButton("Cancel",
				  new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog,int id) {
					dialog.cancel();
				    }
				  });

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();

 // TODO Auto-generated method stub
				
			}
		});
        
        
        dposit.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
			
				LayoutInflater li = LayoutInflater.from(context);
				View promptsView = li.inflate(R.layout.prompts, null);
 
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);
				alertDialogBuilder.setView(promptsView);
				 
				final EditText userInput = (EditText) promptsView
						.findViewById(R.id.editTextDialogUserInput);
				alertDialogBuilder.setTitle("Deposit");
				alertDialogBuilder.setMessage("Enter the Deposited Amount");
				
				alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK",
				  new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog,int id) {
					// get user input and set it to result
					// edit text
				    int change=Integer.parseInt(userInput.getText().toString());
				    
				//	input.setText(""+change);
					int total=accountmoney+change;
					BankConnect connect=new BankConnect(AccountDetails.this);
					connect.updateAccount(RowID, name.getText().toString(), total);
					finish();
					
				    }
				  })
				.setNegativeButton("Cancel",
				  new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog,int id) {
					dialog.cancel();
				    }
				  });

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();

 // TODO Auto-generated method stub
				
			}
		});
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.account_details, menu);
        return true;
    }
}
