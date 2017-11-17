package com.example.roberto.tp_final;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DetalleActivity extends AppCompatActivity {

    TextView tvTemperatura, tvHumedad, tvPresion, tvUbicacion, tvTMax, tvTMin;


    //Manejo de Menues
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_alta_ciudad, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuVolver:
                Intent pantalla = new Intent(this, MainActivity.class);
                pantalla.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(pantalla);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        Bundle params = getIntent().getExtras();
        int Id = params.getInt("ID");

        tvTemperatura = (TextView) findViewById(R.id.tvTemperatura);
        tvPresion = (TextView) findViewById(R.id.tvPresion);
        tvHumedad = (TextView) findViewById(R.id.tvHumedad);
        tvUbicacion = (TextView) findViewById(R.id.tvUbicacion);
        tvTMax = (TextView) findViewById(R.id.tvTMax);
        tvTMin = (TextView) findViewById(R.id.tvTMin);

        final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
        final String KEY_API = "7881949798fd64853e56c0d1e7226aba";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherService service = retrofit.create(WeatherService.class);

        Call<City> cityCall = service.getCity(Id, KEY_API, "metric", "es");

        cityCall.enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                City city = response.body();

                tvUbicacion.setText(String.valueOf(city.getName()));
                tvTemperatura.setText(String.valueOf(city.getClima().getTemp())+" Celsius");
                tvHumedad.setText(String.valueOf(city.getClima().getHumidity())+" %");
                tvPresion.setText(String.valueOf(city.getClima().getPressure())+" HPA");
                tvTMax.setText(String.valueOf(city.getClima().getTemp_max())+" Celsius");
                tvTMin.setText(String.valueOf(city.getClima().getTemp_min())+" Celsius");
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {
                Toast.makeText(DetalleActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
