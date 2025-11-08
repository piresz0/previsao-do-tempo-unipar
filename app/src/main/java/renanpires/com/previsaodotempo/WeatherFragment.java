package renanpires.com.previsaodotempo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class WeatherFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView tvTitle;
    private String cidadeAtual = ""; // ← VAZIO NO INÍCIO
    private WeatherAdapter adapter;
    private List<WeatherItem> weatherList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        recyclerView = view.findViewById(R.id.recycler_weather);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        tvTitle = view.findViewById(R.id.tvTitle);
        atualizarTitulo(); // ← Mostra "Previsão para 7 dias"


        weatherList = new ArrayList<>();
        weatherList.add(new WeatherItem("Seg", "--°C", "Aguardando cidade...", android.R.drawable.ic_menu_info_details));
        weatherList.add(new WeatherItem("Ter", "--°C", "Aguardando cidade...", android.R.drawable.ic_menu_info_details));
        weatherList.add(new WeatherItem("Qua", "--°C", "Aguardando cidade...", android.R.drawable.ic_menu_info_details));
        weatherList.add(new WeatherItem("Qui", "--°C", "Aguardando cidade...", android.R.drawable.ic_menu_info_details));
        weatherList.add(new WeatherItem("Sex", "--°C", "Aguardando cidade...", android.R.drawable.ic_menu_info_details));
        weatherList.add(new WeatherItem("Sáb", "--°C", "Aguardando cidade...", android.R.drawable.ic_menu_info_details));
        weatherList.add(new WeatherItem("Dom", "--°C", "Aguardando cidade...", android.R.drawable.ic_menu_info_details));

        adapter = new WeatherAdapter(weatherList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void atualizarTitulo() {
        if (cidadeAtual.isEmpty()) {
            tvTitle.setText("Previsão para 7 dias");
        } else {
            tvTitle.setText("Previsão para 7 dias - " + cidadeAtual);
        }
    }

    public void atualizarLista(List<WeatherItem> novaLista, String cidade) {
        this.cidadeAtual = cidade;
        weatherList.clear();
        weatherList.addAll(novaLista);
        adapter.notifyDataSetChanged();
        atualizarTitulo(); // ← Atualiza título com a cidade
    }
}