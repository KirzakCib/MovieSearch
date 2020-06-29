package com.example.moviesearch;

import android.app.Activity;
import android.app.ProgressDialog;

import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;

import java.util.Collections;

import java.util.HashSet;

import java.util.TreeMap;

import retrofit2.Call;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PageFragment_one extends Fragment {
    static final String BASE_URL = "https://s3-eu-west-1.amazonaws.com/sequeniatesttask/";
    ArrayAdapter<String> adapter;
    ArrayList<String> array;
    ListView listView;
    HashSet<String> hashSet;
    GridView gridView;
    ArrayList<String> url = new ArrayList<>();
    ArrayList<String> url_ = new ArrayList<>();
    final String IMAGE_URL = "image_url";
    final String TEXT_IMAGE = "text_image";
    ProgressDialog progressDialog;
    String pos;
    MySimpleAdapter sAdapter;
    ArrayList<TreeMap<String, Object>> data_;
    int l = 0;

    public interface OnSelectGridView{
        void onGridSelect(String name);
    }

    OnSelectGridView onSelectGridView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
            onSelectGridView = (OnSelectGridView) activity;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pager_one, container, false);

            Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();

            ParserJSON parserJSON = retrofit.create(ParserJSON.class);

            Call<Parser> call_ = parserJSON.filmParser();

            Call<Parser> call = parserJSON.filmParser();


            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Пожалуйста подождите");
            progressDialog.setMessage("Загрузка данных...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
            try {
                make_sort(call_);
                Thread.sleep(2000);
                if (url.size() == 0) {
                    Thread.sleep(1000);
                }
                make(call);
            } catch (Exception e) {
            }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = getActivity().findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = array.get(position).substring(0,1).toLowerCase()+array.get(position).substring(1);
                url_.removeAll(url_);
                gridView.removeAllViewsInLayout();
                Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create()).build();

                ParserJSON parserJSON = retrofit.create(ParserJSON.class);

                Call<Parser> call_1 = parserJSON.filmParser();
                Call<Parser> call_1_ = parserJSON.filmParser();
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle("Пожалуйста подождите");
                progressDialog.setMessage("Загрузка данных...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setIndeterminate(true);
                progressDialog.show();
                try {
                    make_sort_(call_1);
                    if(url_.size() == 0) {
                        Thread.sleep(1000);
                    }
                    make_(call_1_);
                }catch (Exception e) {
                }

            }
        });

        gridView = (GridView) getActivity().findViewById(R.id.grid_view);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onSelectGridView.onGridSelect(String.valueOf(data_.get(position).ceilingEntry(TEXT_IMAGE)).substring(11));
            }
        });
    }

    public void make_sort_ (Call <Parser> call_) {

        call_.enqueue(new Callback<Parser>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<Parser> call, Response<Parser> response) {
                if (response.isSuccessful()) {
                    Parser parser = response.body();
                    for (Film film : parser.getFilms()) {
                        for(int z = 0; z < film.getGenres().size(); z++) {
                            if (film.getGenres().get(z).equals(pos)) {
                                url_.add(String.valueOf(film.getLocalizedName()));
                            }
                        }
                    }
                    Collections.sort(url_);
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

    public void make_ (Call <Parser> call) {

        call.enqueue(new Callback<Parser>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<Parser> call, Response<Parser> response) {
                if (response.isSuccessful()) {
                    Parser parser = response.body();
                    int l = 0;
                    gridView = (GridView) getActivity().findViewById(R.id.grid_view);

                    ArrayList<TreeMap<String, Object>> data = new ArrayList<TreeMap<String, Object>>();
                    data_ = new ArrayList<TreeMap<String, Object>>();
                    TreeMap<String, Object> m = null;
                    int[] kk_ = new int[100];
                    ImageView imageView = getActivity().findViewById(R.id.icon);
                    for (Film film : parser.getFilms()) {
                        for(int z = 0; z < film.getGenres().size(); z++){
                            if(film.getGenres().get(z).equals(pos)){

                        m = new TreeMap<String, Object>();
                        if (film.getImageUrl() == null) {
                            m.put(IMAGE_URL, R.drawable.image);
                        } else {
                            m.put(IMAGE_URL, film.getImageUrl());
                        }
                        m.put(TEXT_IMAGE, film.getLocalizedName());
                        for (int i = 0; i < url_.size(); i++) {
                            if (film.getLocalizedName().equals(url_.get(i))) {
                                kk_[l] = i;
                                l++;
                            }
                        }
                        data.add(m);
                            }
                        }
                    }

                    String[] from = {IMAGE_URL, TEXT_IMAGE};

                    for (int a = 0; a < data.size(); a++) {
                        for (int y = 0; y < data.size(); y++) {
                            if (a == kk_[y])
                                data_.add(data.get(y));
                        }
                    }
                    int[] to = {R.id.icon, R.id.text};
                     sAdapter = new MySimpleAdapter(getActivity(), data_, R.layout.film, from, to);

                    gridView.setAdapter(sAdapter);

                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Parser> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("ERRORS:", String.valueOf(t));
                Toast.makeText(getActivity(), "Ошибка,пожалуйста, проверьте подключение к сети.\n С уважением.", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void make_sort (Call <Parser> call_) {

        call_.enqueue(new Callback<Parser>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<Parser> call, Response<Parser> response) {
                if (response.isSuccessful()) {
                    Parser parser = response.body();
                    for (Film film : parser.getFilms()) {
                        url.add(String.valueOf(film.getLocalizedName()));
                    }
                        Collections.sort(url);
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


    public void make(Call <Parser> call) {

        call.enqueue(new Callback<Parser>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<Parser> call, Response<Parser> response) {
                   if(response.isSuccessful()){
                       Parser parser = response.body();
                       listView = getActivity().findViewById(R.id.list);
                       array = new ArrayList<>();
                       hashSet = new HashSet<>();
                       adapter = new ArrayAdapter<>(getActivity(),R.layout.list, array);
                       listView.setAdapter(adapter);

                        Integer d = 0;

                       gridView = (GridView) getActivity().findViewById(R.id.grid_view);

                       ArrayList<TreeMap<String, Object>> data = new ArrayList<TreeMap<String, Object>>();
                       data_ = new ArrayList<TreeMap<String, Object>>();
                       TreeMap<String, Object> m = null;
                       int[] kk = new int[100];
                       ImageView imageView = getActivity().findViewById(R.id.icon);
                       for(Film film : parser.getFilms()) {

                           m = new TreeMap<String, Object>();
                           if(film.getImageUrl() == null){
                               m.put(IMAGE_URL,R.drawable.image);
                           }else {
                               m.put(IMAGE_URL, film.getImageUrl());
                           }
                            m.put(TEXT_IMAGE,film.getLocalizedName());
                               for(int i = 0; i < url.size(); i++) {

                                   if(film.getLocalizedName().equals(url.get(i))){
                                       kk[l] = i;
                                       l++;
                                   }
                               }
                            data.add(m);

                           d = film.getGenres().size();
                           String[] nn = new String[d];
                         nn = film.getGenres().toString().replace("[","").replace("]","").split("\\,\\s");
                          for(int i = 0; i< d;i++){
                              nn[i] = nn[i].substring(0,1).toUpperCase() + nn[i].substring(1);
                              hashSet.add(nn[i]);
                          }
                       }

                       for(int a = 0; a < data.size(); a++){
                           for(int y = 0; y < data.size(); y++){
                               if (a == kk[y])
                                   data_.add(data.get(y));
                           }
                       }
                       String[] from = {IMAGE_URL,TEXT_IMAGE};
                       int[] to = {R.id.icon,R.id.text};
                        sAdapter = new MySimpleAdapter(getActivity(),data_,R.layout.film,from,to);

                       gridView.setAdapter(sAdapter);

                   }

                array.addAll(hashSet);
                   Collections.sort(array);
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Parser> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("ERRORS:",String.valueOf(t));
                Toast.makeText(getActivity(),"Ошибка,пожалуйста, проверьте подключение к сети.\n С уважением.",Toast.LENGTH_LONG).show();
            }
        });

    }

}




