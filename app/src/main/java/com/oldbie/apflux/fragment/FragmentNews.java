package com.oldbie.apflux.fragment;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.oldbie.apflux.R;
import com.oldbie.apflux.adapter.NewsAdapter;
import com.oldbie.apflux.adapter.RecyclerItemClickListener;
import com.oldbie.apflux.model.News;
import com.oldbie.apflux.model.ResponseNews;
import com.oldbie.apflux.network.NetworkAPI;
import com.oldbie.apflux.network.ServiceAPI;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNews extends Fragment {

    private NetworkAPI api;
    private ArrayList<News> arrNew;
    private NewsAdapter adapter;
    private RecyclerView rvMain;

    public FragmentNews() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_news, container, false );
        api = ServiceAPI.newService( NetworkAPI.class );
        rvMain=(RecyclerView)view.findViewById( R.id.rvMain );
        getNews();
        return view;
    }

    private void getNews(){
        getActivity().runOnUiThread( new Runnable() {
            @Override
            public void run() {
                Call<ResponseNews> call = api.getNewData();
                call.enqueue( new Callback<ResponseNews>() {
                    @Override
                    public void onResponse(Call<ResponseNews> call, Response<ResponseNews>response) {
                        if (response.body().getResult()==0){
                            arrNew=response.body().getData();
                            for (int i=0;i<arrNew.size();i++){

                                adapter=new NewsAdapter(arrNew,getContext());
//                                GridLayoutManager mm = new GridLayoutManager( getContext(),GridLayoutManager.DEFAULT_SPAN_COUNT,GridLayoutManager.VERTICAL,false );
//                                rvMain.
//                                rvMain.setLayoutManager( mm );
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                                rvMain.setLayoutManager(layoutManager);
                                rvMain.setAdapter( adapter );
                            }
                        }
                        else {

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseNews> call, Throwable t) {

                    }
                } );
            }
        } );

        rvMain.addOnItemTouchListener( new RecyclerItemClickListener( getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String mContent = arrNew.get( position ).getmContent();
                String mTitle=arrNew.get( position ).getmTitle();
                AlertDialog.Builder mBuilder = new AlertDialog.Builder( view.getContext() );

                mBuilder.setTitle( mTitle ).setMessage( mContent ).setCancelable( true ).setNegativeButton( "DONE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        dialog.dismiss();
                    }
                } ).show();
            }
        }));
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
