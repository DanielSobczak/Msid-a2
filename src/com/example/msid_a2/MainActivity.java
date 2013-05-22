package com.example.msid_a2;

import java.util.ArrayList;
import java.util.List;

import Matrix.IllegalDimensionException;
import Matrix.NoSquareException;
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
	private Liczydlo liczydlo;
	private Litera A;
	private Litera B;
	private Litera C;
	  List<Litera> litery;

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
        liczydlo = new Liczydlo();
        
        //TODO Wczytywanie z pliku
        
        A = new Litera('A');
        B = new Litera('B');
        C = new Litera('C');
        
         litery = new ArrayList<Litera>();
        litery.add(A);
        litery.add(B);
        litery.add(C);
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
                drawView.points.clear();
                drawView.invalidate();
                return true;
            case R.id.action_recognize:
            	liczydlo.policzIJ(drawView.points);
            	WektorCech2 wc =  new WektorCech2(liczydlo.cechaQ1(drawView.points), liczydlo.cechaQ2(drawView.points));//:D
			Litera rozpoznanaLitera;
			try {
				rozpoznanaLitera = liczydlo.klasyfikacja(litery, 2, wc.getMatrix());
				Toast.makeText(getApplicationContext(), "Rozpoznano: " + rozpoznanaLitera.name, Toast.LENGTH_LONG).show();
			} catch (IllegalDimensionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSquareException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            	
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
						Litera l = null;
					    if(c.equals("A")) l = A;
					    else if(c.equals("B")) l =B;
					    else if(c.equals("C")) l =C;
					    liczydlo.policzIJ(drawView.points);
		            	WektorCech2 wc =  new WektorCech2(liczydlo.cechaQ1(drawView.points), liczydlo.cechaQ2(drawView.points));//:D
					    l.add(wc, (double)(A.size()+B.size()+C.size()+1));
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
