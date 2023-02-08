package filipovic.youtube_api_demo_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import filipovic.youtube_api_demo_app.adapter.VideosAdapter;
import filipovic.youtube_api_demo_app.dto.Response;
import filipovic.youtube_api_demo_app.dto.Result;
import filipovic.youtube_api_demo_app.exception.ApiCallFailedException;

public class MainActivity extends AppCompatActivity implements VideosAdapter.ItemClickInterface {

    private RecyclerView recyclerView;
    private VideosAdapter videosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectComponents();
        initAdapter();
        getVideosFromApi();
    }

    private void connectComponents() {
        recyclerView = findViewById(R.id.rvList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initAdapter() {
        videosAdapter = new VideosAdapter(this, this);
        recyclerView.setAdapter(videosAdapter);
    }

    private void getVideosFromApi() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(() -> {
            try {
                URL url = new URL(getString(R.string.api_url));

                HttpURLConnection httpURLConnection =
                        (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.addRequestProperty(getString(R.string.api_key_header_key), getString(R.string.api_key_header_value));
                httpURLConnection.addRequestProperty(getString(R.string.api_host_header_key), getString(R.string.api_host_header_value));
                httpURLConnection.setReadTimeout(15000);
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.connect();

                InputStreamReader inputStreamReader =
                        new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                Response response = new Gson().fromJson(bufferedReader, Response.class);

                bufferedReader.close();
                inputStreamReader.close();

                if (Objects.isNull(response)) {
                    throw new ApiCallFailedException(getString(R.string.api_url));
                }

                handler.post(() -> {
                    videosAdapter.setVideos(response.getResults());
                    videosAdapter.notifyDataSetChanged();
                });

            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ApiCallFailedException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onItemClick(Result video) {
        Intent intent = new Intent(this, VideoDetails.class);
        intent.putExtra("video", video);
        startActivity(intent);
    }
}