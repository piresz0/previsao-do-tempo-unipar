package renanpires.com.previsaodotempo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FAB
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> scanQRCode());

        // ViewPager + Tabs
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        viewPager.setAdapter(new ViewPagerAdapter(this));

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText("Previsão");
                tab.setIcon(R.drawable.ic_weather);
            } else {
                tab.setText("Mapa");
                tab.setIcon(R.drawable.ic_map);
            }
        }).attach();
    }

    private void scanQRCode() {
        new IntentIntegrator(this)
                .setOrientationLocked(false)
                .setPrompt("Escaneie o QR Code da cidade")
                .setCaptureActivity(CaptureActivity.class)
                .initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show();
            } else {
                String cidade = result.getContents().trim();
                Toast.makeText(this, "Cidade: " + cidade, Toast.LENGTH_LONG).show();
                buscarPrevisao(cidade);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void buscarPrevisao(String cidade) {
        String apiKey = "f6bbd8fdf0d6ddbe0fd125b7ef1eb83e"; // ← CHAVE
        String url = "https://api.openweathermap.org/data/2.5/forecast?q="
                + cidade + "&appid=" + apiKey + "&units=metric&lang=pt_br";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        List<WeatherItem> novaLista = new ArrayList<>();
                        JSONArray list = response.getJSONArray("list");

                        for (int i = 0; i < 40; i += 8) {
                            JSONObject day = list.getJSONObject(i);
                            JSONObject main = day.getJSONObject("main");
                            JSONArray weatherArray = day.getJSONArray("weather");
                            JSONObject weather = weatherArray.getJSONObject(0);

                            String temp = Math.round(main.getDouble("temp")) + "°C";
                            String desc = weather.getString("description");
                            desc = Character.toUpperCase(desc.charAt(0)) + desc.substring(1);

                            String dia = switch (i / 8) {
                                case 0 -> "Hoje";
                                case 1 -> "Amanhã";
                                case 2 -> "Depois";
                                default -> "Dia " + (i / 8);
                            };

                            novaLista.add(new WeatherItem(dia, temp, desc, android.R.drawable.ic_menu_compass));
                        }

                        WeatherFragment fragment = (WeatherFragment) getSupportFragmentManager()
                                .findFragmentByTag("f0");
                        if (fragment != null) {
                            fragment.atualizarLista(novaLista, cidade);
                        }

                        MapFragment mapFragment = (MapFragment) getSupportFragmentManager()
                                .findFragmentByTag("f1");
                        if (mapFragment != null) {
                            mapFragment.moverParaCidade(cidade);
                        }

                    } catch (Exception e) {
                        Toast.makeText(this, "Erro ao processar dados", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Cidade não encontrada ou erro de rede!", Toast.LENGTH_LONG).show()
        );

        Volley.newRequestQueue(this).add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}