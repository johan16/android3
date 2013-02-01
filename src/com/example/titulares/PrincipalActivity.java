package com.example.titulares;

import java.util.HashMap;
import java.util.LinkedList;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

@SuppressLint("HandlerLeak")
public class PrincipalActivity extends Activity {
	// Constantes T_>titulo L->link
	static final String DATA_TITLE = "T";
	static final String DATA_LINK = "L";
	static final String DATA_DESCRIPTION = "D";

	// lista enlazada para guardar los datos
	private static LinkedList<HashMap<String, String>> data;

	// Archivo con su RSS
	private static String[] feedUrl = {	"http://alt1040.com/feed", 
										"http://rss.emol.com/rss.asp?canal=0",
										"http://rss.emol.com/rss.asp?canal=1",
										"http://rss.emol.com/rss.asp?canal=2",
										"http://rss.emol.com/rss.asp?canal=5"};

	// Cuadro de espera
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		this.setTitle("Lector de titulares de alt1040");
		
		Button btn_1 = (Button) this.findViewById(R.id.btn1);
		Button btn_2 = (Button) this.findViewById(R.id.btn2);
		Button btn_3 = (Button) this.findViewById(R.id.btn3);
		Button btn_4 = (Button) this.findViewById(R.id.btn4);
		Button btn_5 = (Button) this.findViewById(R.id.btn5);

		ListView lv = (ListView) this.findViewById(R.id.lstTitulares);
		lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			//ADAPTERVIEW ES EL NOMBRE DE LA CLASE GENERICA
			// <?> = RECIVE CUALQUIER TIPO DE DATO
			// INT LA POSICION DEL VALOR Q ESTAMOS PASANDO
			// LONG  ID DEL ELEMENTO
			public void onItemClick(AdapterView<?> arg0, View arg1, int posicion,
					long id) {
				// TODO Auto-generated method stub
				
				//lanzar link de noticia.
				HashMap<String, String> entry = data.get(posicion);
				
				Intent i = new Intent(Intent.ACTION_VIEW, 
									  Uri.parse(entry.get(DATA_LINK)));
				startActivity(i);
				
				
			}
			
		});
		cargarDatos(feedUrl[0]);
		btn_1.setOnClickListener( new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cargarDatos(feedUrl[0]);
				
			}
			
		});
		btn_2.setOnClickListener( new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cargarDatos(feedUrl[1]);
				
			}
			
		});
		btn_3.setOnClickListener( new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cargarDatos(feedUrl[2]);
				
			}
			
		});
		btn_4.setOnClickListener( new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cargarDatos(feedUrl[3]);
				
			}
			
		});
		btn_5.setOnClickListener( new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cargarDatos(feedUrl[4]);
				
			}
			
		});
	}

	/**
	 * Este método inicia la carga de datos. Muestra al usuario un diálogo de
	 * carga. Inicia un hilo con la carga
	 * 
	 */
	private void cargarDatos(final String url) {

		progressDialog = ProgressDialog.show(PrincipalActivity.this, "",
				"Por favor espere mientras se cargan los datos...", true);

		new Thread(new Runnable() {
			public void run() {
				XMLParser parser = new XMLParser(url);
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
						DATA_TITLE, DATA_LINK , DATA_DESCRIPTION}, new int[] {
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
