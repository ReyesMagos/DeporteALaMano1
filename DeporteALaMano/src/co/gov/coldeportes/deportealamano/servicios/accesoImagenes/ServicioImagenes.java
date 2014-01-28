package co.gov.coldeportes.deportealamano.servicios.accesoImagenes;


import java.net.URL;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class ServicioImagenes extends AsyncTask<String, Integer, Boolean> {

	private String url;
	private Bitmap bmp;

	public ServicioImagenes(String s) {

		url = s;
	}

	protected Boolean doInBackground(String... params) {

		boolean resul = true;

		// HttpClient httpClient = new DefaultHttpClient();

	//	HttpGet del = new HttpGet(url);

		try {

			BitmapFactory.Options options=new BitmapFactory.Options();
			options.inSampleSize = 4;
			URL url7 = new URL(url);
			bmp = BitmapFactory.decodeStream(url7.openConnection()
					.getInputStream(),null, options);


		} catch (Exception ex) {
			Log.e("ServicioRest", "Error!", ex);
			resul = false;
		}
		return resul;
	}

	protected void onPostExecute(Boolean result) {
		if (result) {

		}

	}

	public Bitmap getBmp() {
		return bmp;
	}

	public void setBmp(Bitmap bmp) {
		this.bmp = bmp;
	}
}