package com.example.androidsix.rvadimageview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidsix.R;

import java.util.ArrayList;
import java.util.List;

public class RVADActivity extends AppCompatActivity {

    private static final String TAG = "RVADActivity";
    private RecyclerView rvView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rvad);
        rvView = findViewById(R.id.rv_view);
        rvView.setLayoutManager(linearLayoutManager = new LinearLayoutManager(this));
        List<String> mockDatas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mockDatas.add(i+"");
        }
        rvView.setAdapter(new MyAdapter(R.layout.item_view, mockDatas, this));

        rvView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int fPos = linearLayoutManager.findFirstVisibleItemPosition();
                int lPos = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                for (int i = fPos; i <= lPos; i++) {
                    View view = linearLayoutManager.findViewByPosition(i);
                    AdImageView adImageView = view.findViewById(R.id.ad_view);
                    if (i > 0 && i % 6 == 0) {
                        int result = linearLayoutManager.getHeight() - view.getTop() - adImageView.getHeight();
                        adImageView.setDy(result);
                    }
                }
            }
        });
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

        private int layoutID = 0;
        private List<String> mockDatas;
        private Context context;

        public MyAdapter(int layoutID, List<String> mockDatas, Context context) {
            this.layoutID = layoutID;
            this.mockDatas = mockDatas;
            this.context = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(layoutID, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            if (position > 0 && position % 6 == 0){
                holder.showView(View.VISIBLE, View.GONE, View.GONE);
            }else {
                holder.showView(View.GONE, View.VISIBLE, View.VISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return mockDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{

            private AdImageView adImageView;
            private TextView mTitle;
            private TextView mMessage;

            public MyViewHolder(View itemView) {
                super(itemView);
                adImageView = itemView.findViewById(R.id.ad_view);
                mTitle = itemView.findViewById(R.id.tv_title);
                mMessage = itemView.findViewById(R.id.tv_message);
            }

           public void showView(int imgVisiable, int titleVisiable, int msgVisiable){
                adImageView.setVisibility(imgVisiable);
                mTitle.setVisibility(titleVisiable);
                mMessage.setVisibility(msgVisiable);
           }
        }

    }
}
