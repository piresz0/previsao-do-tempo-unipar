package renanpires.com.previsaodotempo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;
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

    // RECEBE O RESULTADO DO QR CODE
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show();
            } else {
                String cidade = result.getContents().trim();
                Toast.makeText(this, "Cidade: " + cidade, Toast.LENGTH_LONG).show();
                buscarPrevisao(cidade); // CHAMA A API MOCK
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    // MÉTODO MOCK (depois será API real)
    private void buscarPrevisao(String cidade) {
        List<WeatherItem> novaLista = new ArrayList<>();
        novaLista.add(new WeatherItem("Seg", "28°C", "Ensolarado em " + cidade, android.R.drawable.ic_menu_compass));
        novaLista.add(new WeatherItem("Ter", "26°C", "Nublado", android.R.drawable.ic_menu_compass));
        novaLista.add(new WeatherItem("Qua", "30°C", "Chuva leve", android.R.drawable.ic_menu_compass));
        novaLista.add(new WeatherItem("Qui", "27°C", "Parcialmente nublado", android.R.drawable.ic_menu_compass));
        novaLista.add(new WeatherItem("Sex", "29°C", "Ensolarado", android.R.drawable.ic_menu_compass));
        novaLista.add(new WeatherItem("Sáb", "25°C", "Tempestade", android.R.drawable.ic_menu_compass));
        novaLista.add(new WeatherItem("Dom", "31°C", "Ensolarado", android.R.drawable.ic_menu_compass));

        WeatherFragment fragment = (WeatherFragment) getSupportFragmentManager()
                .findFragmentByTag("f0"); // f0 = primeira aba
        if (fragment != null) {
            fragment.atualizarLista(novaLista, cidade); // PASSA A CIDADE
        }
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