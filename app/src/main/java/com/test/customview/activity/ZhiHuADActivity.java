package com.test.customview.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.test.customview.R;
import com.test.customview.bean.Picture;
import com.test.customview.utils.DataProvider;
import com.test.customview.utils.Utils;

import butterknife.BindView;

/**
 * Created by hhj on 2018/3/19.
 */

public class ZhiHuADActivity extends BaseActivity {
    @BindView(R.id.recycleView)
    EasyRecyclerView recyclerView;
    private ImageAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.ac_zhihu_ad;
    }

    @Override
    public void initView() {
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                adapter.addAll(DataProvider.getPictures(0));
                recyclerView.setRefreshing(false);
            }
        });
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter=new ImageAdapter(this));
        //更多加载
        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                addData();
            }
        });

        adapter.setNoMore(R.layout.view_nomore);
        SpaceDecoration itemDecoration = new SpaceDecoration((int) convertDpToPixel(8,this));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        recyclerView.addItemDecoration(itemDecoration);


    }
    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }
    private void addData(){
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.addAll(DataProvider.getPictures(0));
            }
        },1000);
    }

    public class ImageAdapter extends RecyclerArrayAdapter<Picture> {
        public ImageAdapter(Context context) {
            super(context);
        }

        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new ImageViewHolder(parent);
        }

        @Override
        public int getViewType(int position) {
            return super.getViewType(position);
        }

        @Override
        public void onViewAttachedToWindow(BaseViewHolder holder) {
            super.onViewAttachedToWindow(holder);
        }
    }

    public class ImageViewHolder extends BaseViewHolder<Picture> {
        ImageView imgPicture;
        private int mLastPosition = -1;
        private boolean isFirstOnly = true;


        public ImageViewHolder(ViewGroup parent) {
            super(new ImageView(parent.getContext()));
            imgPicture = (ImageView) itemView;
            imgPicture.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            imgPicture.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        public void startAnim(){
            if (!isFirstOnly ||  getAdapterPosition() > mLastPosition) {
                for (Animator anim : getAnimators(itemView)) {
                    anim.setDuration(500).start();
                    anim.setInterpolator(new LinearInterpolator());
                }
                // imgPicture.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.img_anim));
                mLastPosition =  getAdapterPosition();
            } else {
                Utils.clear(itemView);
            }
        }
        @Override
        public void setData(Picture data) {
            ViewGroup.LayoutParams params = imgPicture.getLayoutParams();

            DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
            int width = dm.widthPixels/2;//宽度为屏幕宽度一半
            int height = data.getHeight()*width/data.getWidth();//计算View的高度

            params.height = height;
            imgPicture.setLayoutParams(params);
            //imgPicture.setImageResource(R.mipmap.ic_launcher);
            Glide.with(getContext())
                    .load(data.getSrc())
                    .asBitmap()
                    .animate(animator)
                    .into(new SimpleTarget<Bitmap>() {

                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            imgPicture.setImageBitmap(resource);
                            startAnim();
                        }
                    });
        }
    }
    protected Animator[] getAnimators(View view) {
        return new Animator[] { ObjectAnimator.ofFloat(view, "alpha", 0f, 1f) ,ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f)
        ,ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f)};
    }
    ViewPropertyAnimation.Animator animator = new ViewPropertyAnimation.Animator() {
        @Override
        public void animate(View view) {
            view.setAlpha( 0f );

            ObjectAnimator fadeAnim = ObjectAnimator.ofFloat( view, "alpha", 0f, 1f );
            fadeAnim.setDuration( 2500 );
            fadeAnim.start();
        }
    };

    public class ListHolder extends BaseViewHolder<String> {
        private TextView mTv_name;


        public ListHolder(ViewGroup parent) {
            super(parent,R.layout.item_list);
            mTv_name = $(R.id.title);
        }

        @Override
        public void setData(final String s){
            mTv_name.setText(s);
        }
    }
}
