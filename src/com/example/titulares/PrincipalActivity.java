package com.example.titulares;

import java.util.HashMap;
import java.util.LinkedList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class PrincipalActivity extends Activity {
	// Constantes T_>titulo L->link
	static final String DATA_TITLE = "T";
	static final String DATA_LINK = "L";

	// lista enlazada para guardar los datos
	private static LinkedList<HashMap<String, String>> data;

	// Archivo con su RSS
	private static String feedUrl = "http://alt1040.com/feed";

	// Cuadro de espera
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		this.setTitle("Lector de titulares de AL140");

		ListView lv = (ListView) this.findViewById(R.id.lstTitulares);
		cargarDatos();
	}

	/**
	 * Este método inicia la carga de datos. Muestra al usuario un diálogo de
	 * carga. Inicia un hilo con la carga
	 * 
	 */
	private void cargarDatos() {

		progressDialog = ProgressDialog.show(PrincipalActivity.this, "",
				"Por favor espere mientras se cargan los datos...", true);

		new Thread(new Runnable() {
			public void run() {
				XMLParser parser = new XMLParser(feedUrl);
				Message msg = progressHandler.obtainMessage();
				msg.obj = parser.parse();
				progressHandler.sendMessage(msg);
			}
		}).start();
	}

	/**
	 * 
	 * Método de adminstración de datos del formato xml.
	 * Invoca al método setData
	 */

	private final Handler progressHandler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			if (msg.obj != null) {
				data = (LinkedList<HashMap<String, String>>) msg.obj;
				setData(data);
			}
			progressDialog.dismiss();
		}
	};

	/**
	 * Recibe la lista enlazada y procesa dos valores: título y link
	 * 
	 * @param data
	 */

	private void setData(LinkedList<HashMap<String, String>> data) {

		SimpleAdapter sAdapter = new SimpleAdapter(getApplicationContext(),
				data, android.R.layout.simple_list_item_2, new String[] {
						DATA_TITLE, DATA_LINK }, new int[] {
						android.R.id.text1, android.R.id.text2 });

		ListView lv = (ListView) findViewById(R.id.lstTitulares);
		lv.setAdapter(sAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_principal, menu);
		return true;
	}

}
