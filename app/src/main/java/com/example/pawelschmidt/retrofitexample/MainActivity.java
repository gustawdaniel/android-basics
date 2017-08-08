package com.example.pawelschmidt.retrofitexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = (TextView) findViewById(R.id.text);

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final GitHubService gitHubService = retrofit.create(GitHubService.class);

        final Call<List<Repo>> listReposCall = gitHubService.listRepos("pawel-schmidt");

        listReposCall.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(final Call<List<Repo>> call, final Response<List<Repo>> response) {
                final List<Repo> bodyResponse = response.body();
                String text = "";
                for (final Repo line : bodyResponse) {
                    text += line.getId() + ": " + line.getName() + "\n";
                }
                textView.setText(text);
            }

            @Override
            public void onFailure(final Call<List<Repo>> call, final Throwable t) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }
}
