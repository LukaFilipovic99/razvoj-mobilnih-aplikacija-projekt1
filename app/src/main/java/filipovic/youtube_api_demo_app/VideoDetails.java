package filipovic.youtube_api_demo_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import filipovic.youtube_api_demo_app.dto.Result;

public class VideoDetails extends AppCompatActivity {

    private ImageView ivThumbnail;
    private TextView tvTitle;
    private TextView tvDuration;
    private TextView tvViews;
    private ImageView ivChannelPhoto;
    private TextView tvChannelName;
    private Button btnWatch;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_details);

        connectComponents();
        showData();
    }

    private void connectComponents() {
        ivThumbnail = findViewById(R.id.ivThumbnail);
        tvTitle = findViewById(R.id.tvTitle);
        tvDuration = findViewById(R.id.tvDuration);
        tvViews = findViewById(R.id.tvViews);
        ivChannelPhoto = findViewById(R.id.ivChannelPhoto);
        tvChannelName = findViewById(R.id.tvChannelName);
        btnWatch = findViewById(R.id.btnWatch);
        btnBack = findViewById(R.id.btnBack);
    }

    private void showData() {
        Intent intent = getIntent();
        Result video = (Result) intent.getSerializableExtra("video");

        Picasso.with(this)
                .load(video.getThumbnail().getUrl())
                .fit()
                .into(ivThumbnail);
        tvTitle.setText(video.getTitle());
        tvDuration.setText("Trajanje: " + video.getDurationFormatted());
        tvViews.setText("Broj pregleda: " + video.getViews());
        Picasso.with(this)
                .load(video.getChannel().getIcon())
                .fit()
                .into(ivChannelPhoto);
        tvChannelName.setText(video.getChannel().getName());

        btnBack.setOnClickListener(view -> finish());
        btnWatch.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(video.getUrl()))));
    }


}
