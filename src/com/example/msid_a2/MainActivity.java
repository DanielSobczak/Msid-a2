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


/**
 * Ogolny zarys programu.
 * 
 * 1.Wyliczenie estymatorow dla kazdej z liter na podstawie podanego ciagu "do nauki" . 
 * 2.Przy rozpoznawaniu litery zostaje wyliczona wartosc prawdopodobienstwa p(phi/yk=1) modelu statystycznego dla każdej z liter.
 *   Wykorzystane sa tutaj estymatory wyliczone wczesniej
 * 3.Za pomoca twierdzenia Bayes'a wyliczamy dla kazdej litery prawdopodobienstwo p(y/phi) 
 * i podejmujemy decyzje tj. bierzemy najwieksze.
 * 
 * @author Adam
 *
 */
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

		prepareLetters();
	}

	private void prepareLetters() {
		//TODO Wczytywanie z pliku
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
			// do whatever
			return true;
		case R.id.action_save:
			for (Litera l:litery) {
				writeToFile(l.przykladyDoNauki, l.name);
			}
			return true;
		case R.id.action_load:
			prepareLetters();
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
					liczydlo.policzIJ(drawView.points);
					WektorCech2 wc =  new WektorCech2(liczydlo.cechaQ1(drawView.points), liczydlo.cechaQ2(drawView.points));//:D
					l.add(wc, (double)(A.size()+B.size()+C.size()+1));
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
