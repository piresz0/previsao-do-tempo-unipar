package renanpires.com.previsaodotempo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class WeatherFragment extends Fragment {

    private RecyclerView recyclerView;
    private WeatherAdapter adapter;
    private List<WeatherItem> weatherList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        recyclerView = view.findViewById(R.id.recycler_weather);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Dados mock
        weatherList = new ArrayList<>();
        weatherList.add(new WeatherItem("Seg", "28°C", "Ensolarado", android.R.drawable.ic_menu_compass));
        weatherList.add(new WeatherItem("Ter", "25°C", "Nublado", android.R.drawable.ic_menu_compass));
        weatherList.add(new WeatherItem("Qua", "30°C", "Chuva", android.R.drawable.ic_menu_compass));
        weatherList.add(new WeatherItem("Qui", "27°C", "Parcialmente nublado", android.R.drawable.ic_menu_compass));
        weatherList.add(new WeatherItem("Sex", "29°C", "Ensolarado", android.R.drawable.ic_menu_compass));
        weatherList.add(new WeatherItem("Sáb", "26°C", "Tempestade", android.R.drawable.ic_menu_compass));
        weatherList.add(new WeatherItem("Dom", "31°C", "Ensolarado", android.R.drawable.ic_menu_compass));

        adapter = new WeatherAdapter(weatherList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}