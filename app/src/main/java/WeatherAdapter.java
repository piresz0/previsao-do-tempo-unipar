package renanpires.com.previsaodotempo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private List<WeatherItem> weatherList;

    public WeatherAdapter(List<WeatherItem> weatherList) {
        this.weatherList = weatherList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_weather, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeatherItem item = weatherList.get(position);
        holder.tvDay.setText(item.getDay());
        holder.tvTemp.setText(item.getTemp());
        holder.tvCondition.setText(item.getCondition());
        holder.ivIcon.setImageResource(item.getIcon());
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDay, tvTemp, tvCondition;
        ImageView ivIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tv_day);
            tvTemp = itemView.findViewById(R.id.tv_temp);
            tvCondition = itemView.findViewById(R.id.tv_condition);
            ivIcon = itemView.findViewById(R.id.iv_icon);
        }
    }
}