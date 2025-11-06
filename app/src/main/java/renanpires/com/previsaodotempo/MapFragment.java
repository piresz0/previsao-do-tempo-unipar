package renanpires.com.previsaodotempo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.HashMap;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final HashMap<String, LatLng> cidades = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        // Inicia o mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Adiciona cidades com coordenadas
        cidades.put("São Paulo", new LatLng(-23.5505, -46.6333));
        cidades.put("Rio de Janeiro", new LatLng(-22.9068, -43.1729));
        cidades.put("Curitiba", new LatLng(-25.4296, -49.2713));
        cidades.put("Belo Horizonte", new LatLng(-19.9167, -43.9345));
        cidades.put("Salvador", new LatLng(-12.9716, -38.5014));
        cidades.put("Brasília", new LatLng(-15.7939, -47.8828));
        cidades.put("Fortaleza", new LatLng(-3.7172, -38.5431));

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Centraliza no Brasil inicialmente
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-14.2350, -51.9253), 4));
    }

    // MÉTODO CHAMADO PELO MainActivity QUANDO O QR CODE É LIDO
    public void moverParaCidade(String cidade) {
        if (mMap == null) return;

        LatLng posicao = cidades.getOrDefault(cidade, new LatLng(-23.5505, -46.6333)); // São Paulo como padrão
        mMap.clear(); // Remove marcadores antigos
        mMap.addMarker(new MarkerOptions()
                .position(posicao)
                .title(cidade));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(posicao, 10));
    }
}