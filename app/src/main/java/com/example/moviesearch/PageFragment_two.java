package com.example.moviesearch;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PageFragment_two extends Fragment {
    String name;
    static final String BASE_URL = "https://s3-eu-west-1.amazonaws.com/sequeniatesttask/";
    ProgressDialog progressDialog;
    String imageView_;
    String name_text_;
    String year_;
    String reitin_;
    String text_;

    public TextView setText(String text){
        this.name = text;
        return null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pager_two, container, false);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        ParserJSON parserJSON = retrofit.create(ParserJSON.class);


        Call<Parser> call = parserJSON.filmParser();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Пожалуйста подождите");
        progressDialog.setMessage("Загрузка данных...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        make(call);
        return view;
    }

    public void make (Call <Parser> call) {

        call.enqueue(new Callback<Parser>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<Parser> call, Response<Parser> response) {
                if (response.isSuccessful()) {
                    Parser parser = response.body();

                    ImageView imageView = getActivity().findViewById(R.id.icon2);
                    TextView name_text = getActivity().findViewById(R.id.name);
                    TextView year = getActivity().findViewById(R.id.year);
                    TextView reitin = getActivity().findViewById(R.id.reiting);
                    TextView text = getActivity().findViewById(R.id.text3);
                    TextView textView = getActivity().findViewById(R.id.text2);
                    textView.setText(name);
                    for (Film film : parser.getFilms()) {

                        if(film.getLocalizedName().equals(name)) {
                            name_text_ = film.getName();
                            year_ = film.getYear().toString();
                            if (film.getRating() != null){
                                    reitin_ = film.getRating().toString();
                        }else {
                            reitin_ = "0";
                        }
                            text_ = film.getDescription();
                            if(film.getImageUrl() != null)
                           imageView_ = film.getImageUrl().toString();

                        }
                        Picasso.with(getActivity()).load(String.valueOf(imageView_)).fit().centerCrop().error(R.drawable.image).into(imageView);
                        name_text.setText(name_text_);
                        year.setText("Год: "+year_);
                        reitin.setText(reitin_);
                        text.setText(text_);
                            progressDialog.dismiss();
                        }
                    }
               }

            @Override
            public void onFailure(Call<Parser> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("ERRORS:", String.valueOf(t));
                Toast.makeText(getActivity(), "Ошибка,пожалуйста, проверьте подключение к сети.\n С уважением.", Toast.LENGTH_LONG).show();
            }
        });

    }

}
