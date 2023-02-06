package filipovic.youtube_api_demo_app.adapter;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import filipovic.youtube_api_demo_app.R;
import filipovic.youtube_api_demo_app.dto.Result;
import filipovic.youtube_api_demo_app.model.Video;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.Row> {

    private List<Result> videos;
    private LayoutInflater layoutInflater;


    @NonNull
    @Override
    public Row onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_row, parent, false);

        return new Row(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Row holder, int position) {
        Result video = videos.get(position);

        Picasso.with(layoutInflater.getContext())
                .load(video.getThumbnail().getUrl())
                .fit()
                .into(holder.ivThumbnail);
        holder.tvTitle.setText(video.getTitle());
        holder.tvDuration.setText("Trajanje: " + video.getDurationFormatted());
    }

    @Override
    public int getItemCount() {
        return Objects.nonNull(videos) ? videos.size() : 0;
    }

    public void setVideos(List<Result> videos) {
        this.videos = videos;
    }

    public class Row extends RecyclerView.ViewHolder {

        private ImageView ivThumbnail;
        private TextView tvTitle;
        private TextView tvDuration;

        public Row(@NonNull View itemView) {
            super(itemView);

            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDuration = itemView.findViewById(R.id.tvDuration);
        }

    }

}