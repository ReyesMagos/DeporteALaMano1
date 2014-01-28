package co.gov.coldeportes.deportealamano.presentacion.actividades.tabs;

import co.gov.coldeportes.deportealamano.presentacion.R;
import co.gov.coldeportes.deportealamano.presentacion.R.layout;
import co.gov.coldeportes.deportealamano.presentacion.R.menu;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.ViewFlipper;
import android.widget.TabHost.TabSpec;

public class Tab_Principal extends TabActivity {

	public static TabHost tabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tabs_principal);
		iniciaTabs();
	}

	@SuppressWarnings("deprecation")
	private void iniciaTabs() {
		tabHost = getTabHost();
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				if(!Comunicador.isBusco()){
					tabHost.setCurrentTab(1);
				}
				
			}
		});

		// Tab for Mapa
		TabSpec mapaspec = tabHost.newTabSpec("Mapa");
		Intent escenariosIntent = new Intent(this, Tab_Mapa.class);
		mapaspec.setIndicator("Mapa",null)
				.setContent(escenariosIntent);

		TabSpec busquedaspec = tabHost.newTabSpec("Búsqueda");

		Intent busquedaIntent = new Intent(this, Tab_Busqueda.class);
		busquedaspec.setIndicator("Búsqueda",null).setContent(
				busquedaIntent);

		// Adding all TabSpec to TabHost

		tabHost.addTab(mapaspec); // Adding escenarios tab
		tabHost.addTab(busquedaspec); // Adding eventos tab
		tabHost.setCurrentTab(1);
	}

	private void cambiarTab() {
		tabHost.setCurrentTab(1);
	}
	
	public static void cambiaTabAMApa(){
		tabHost.setCurrentTab(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		return true;
	}


}
