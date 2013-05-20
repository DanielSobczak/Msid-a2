package com.example.msid_a2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private DrawView drawView;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set full screen view
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                         WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        drawView = new DrawView(this);
        setContentView(drawView);
        drawView.requestFocus();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.action_settings:
                  // do whatever
                  return true;
            case R.id.action_clear:
                drawView = new DrawView(this);
                setContentView(drawView);
                drawView.requestFocus();
                return true;
            case R.id.action_recognize:
                // do whatever
                return true;
            case R.id.action_learn:
                AlertDialog.Builder ab = new AlertDialog.Builder(this);
                final EditText et = new EditText(this);
                InputFilter[] FilterArray = new InputFilter[1];
                FilterArray[0] = new InputFilter.LengthFilter(1);
                et.setFilters(FilterArray);
                ab.setTitle("To byla litera: ")
                .setView(et)
                .setPositiveButton("OK", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String c = et.getText().toString().trim();
						Toast.makeText(getApplicationContext(), "Podano: "+c, Toast.LENGTH_SHORT).show();
						dialog.dismiss();
					}
				});

                ab.show();
                return true;
            default:
                  return super.onOptionsItemSelected(item);
        }
    }

}
