package filipovic.youtube_api_demo_app.adapter;

import android.content.Context;
import android.util.Log;
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

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.Row> {

    private List<Result> videos;
    private LayoutInflater layoutInflater;
    private ItemClickInterface itemClickInterface;

    public VideosAdapter(Context context, ItemClickInterface itemClickInterface) {
        this.layoutInflater = LayoutInflater.from(context);
        this.itemClickInterface= itemClickInterface;
    }

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
    }

    @Override
    public int getItemCount() {
        return Objects.nonNull(videos) ? videos.size() : 0;
    }

    public void setVideos(List<Result> videos) {
        this.videos = videos;
    }

    public class Row extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivThumbnail;
        private TextView tvTitle;

        public Row(@NonNull View itemView) {
            super(itemView);

            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            tvTitle = itemView.findViewById(R.id.tvTitle);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (Objects.nonNull(itemClickInterface)) {
                itemClickInterface.onItemClick(videos.get(getAdapterPosition()));
            } else return;
        }
    }

    public interface ItemClickInterface {
        void onItemClick(Result video);
    }

}
