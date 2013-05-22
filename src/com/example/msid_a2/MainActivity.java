package com.example.msid_a2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import Matrix.IllegalDimensionException;
import Matrix.NoSquareException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static final int STATE_ABC = 0;
	public static final int STATE_ZRI = 1;

	private int actState = 0; //TODO move to SharedPrefs

	private DrawView drawView;
	private Liczydlo liczydlo;
	private Litera A;
	private Litera B;
	private Litera C;
	List<Litera> litery;
	private Litera Z;
	private Litera r;
	private Litera I;

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

		prepareLetters1();
		//prepareLetters2();
	}

	private void prepareLetters1() {
		try {
			A = new Litera(readFromFile('A'),'A');
			B = new Litera(readFromFile('B'),'B');
			C = new Litera(readFromFile('C'),'C');

			A.setTheta( A.size()+B.size()+C.size());
			B.setTheta( A.size()+B.size()+C.size());
			C.setTheta( A.size()+B.size()+C.size());

		} catch (IllegalDimensionException e) {
			e.printStackTrace();
		}

		litery = new ArrayList<Litera>();
		litery.add(A);
		litery.add(B);
		litery.add(C);

	}
	private void prepareLetters2() {
		try {	
			Z = new Litera(readFromFile('Z'),'Z');
			r = new Litera(readFromFile('R'),'R');
			I = new Litera(readFromFile('I'),'I');

			Z.setTheta( Z.size()+r.size()+I.size());
			r.setTheta( Z.size()+r.size()+I.size());
			I.setTheta( Z.size()+r.size()+I.size());

		} catch (IllegalDimensionException e) {
			e.printStackTrace();
		}

		litery = new ArrayList<Litera>();
		litery.add(Z);
		litery.add(r);
		litery.add(I);

	}

	private List<WektorCech2> readFromFile(char name) {

		List<WektorCech2> przykladyDoNauki = new ArrayList<WektorCech2>(); 

		try {
			InputStream inputStream = openFileInput(name + ".txt");

			if ( inputStream != null ) {
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String receiveString = "";
				while ( (receiveString = bufferedReader.readLine()) != null ) {
					String w[] = receiveString.split(" ");
					WektorCech2 wc = new WektorCech2(Double.parseDouble(w[0]), Double.parseDouble(w[1]));
					przykladyDoNauki.add(wc);
					Log.d("k","RFF, adding "+wc);
				}

				inputStream.close();

			}
		}
		catch (FileNotFoundException e) {
			Log.e("k", "File not found: " + e.toString());
		} catch (IOException e) {
			Log.e("k", "Can not read file: " + e.toString());
		}
		finally{
			return przykladyDoNauki;
		}
	}

	private void writeToFile(List<WektorCech2> przykladyDoNauki, char name) {
		try {
			StringBuilder sb = new StringBuilder();
			for (WektorCech2 w : przykladyDoNauki) {
				sb.append(w.toString());
				sb.append("\n");
			}
			String data = sb.toString();
			Log.d("WTF","Write letter "+name+"\n"+data);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(name+".txt", Context.MODE_PRIVATE));
			outputStreamWriter.write(data);
			outputStreamWriter.close();
		}
		catch (IOException e) {
			Log.e("bleh", "File write failed: " + e.toString());
		} 

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
			if(actState==STATE_ABC){ 
				actState=STATE_ZRI;
				item.setTitle("Switch ZRI->ABC");
				prepareLetters2();
			} else if(actState == STATE_ZRI){
				actState=STATE_ABC;
				item.setTitle("Switch ABC->ZRI");
				prepareLetters1();
			} else item.setTitle("WTF?");

			return true;
		case R.id.action_save:
			for (Litera l:litery) {
				writeToFile(l.przykladyDoNauki, l.name);
			}
			return true;
		case R.id.action_load:
			//prepareLetters2(); //prepareLetters2(); //TODO
			return true;
		case R.id.action_clear:
			drawView.points.clear();
			drawView.invalidate();
			return true;
		case R.id.action_recognize:

			liczydlo.policzIJ(drawView.points);
			WektorCech2 wc ;
			if(actState==STATE_ABC) wc = new WektorCech2(liczydlo.cechaQ1(drawView.points), liczydlo.cechaQ2(drawView.points));
			 else  wc = new WektorCech2(liczydlo.mCechaQ1(drawView.points), liczydlo.mCechaQ2(drawView.points));//:D
			Litera rozpoznanaLitera;
			try {
				rozpoznanaLitera = liczydlo.klasyfikacja(litery, 2, wc.getMatrix());
				Toast.makeText(getApplicationContext(), "Rozpoznano: " + rozpoznanaLitera.name, Toast.LENGTH_LONG).show();
			} catch (IllegalDimensionException e) {
				e.printStackTrace();
			} catch (NoSquareException e) {
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
					String c = et.getText().toString().trim().toUpperCase();
					if(c==null || c.equals("") || c.equals(" ")) {
						Toast.makeText(getApplicationContext(), "Musisz cos wpisac!", Toast.LENGTH_SHORT).show();
						dialog.dismiss();
					}
					Toast.makeText(getApplicationContext(), "Podano: "+c, Toast.LENGTH_SHORT).show();
					Litera l = null;
					if(c.equals("A")) l = A;
					else if(c.equals("B")) l =B;
					else if(c.equals("C")) l =C;
					else if(c.equals("R")) l =r;
					else if(c.equals("Z")) l =Z;
					else if(c.equals("I")) l =I;
					liczydlo.policzIJ(drawView.points);
					WektorCech2 wc;
					if(actState==STATE_ABC) {
						wc = new WektorCech2(liczydlo.cechaQ1(drawView.points), liczydlo.cechaQ2(drawView.points));
						l.add(wc, (double)(A.size()+B.size()+C.size()+1));
					}
					 else  {
						 wc = new WektorCech2(liczydlo.mCechaQ1(drawView.points), liczydlo.mCechaQ2(drawView.points));
						 l.add(wc, (double)(r.size()+Z.size()+I.size()+1));
					 }	
					dialog.dismiss();
					Log.d("k", "xxx na ekranie liczba Puntow" +drawView.points.size());
				}
			});

			ab.show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
