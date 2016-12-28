package com.xiangsheng;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xiangsheng.dao.TablePeopleProfile;
import com.xiangsheng.dao.model.People;
import com.xiangsheng.runtimepermissions.PermissionsManager;
import com.xiangsheng.runtimepermissions.PermissionsResultAction;

import java.util.List;


/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemListWorksActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListPeopleActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        requestPermissions();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        TablePeopleProfile tablePeopleProfile = ControlApplication.getApplication().getDaoManager().getTablePeopleProfile();
        List<People> radicalsList = tablePeopleProfile.queryAllData();
        if (radicalsList == null || radicalsList.isEmpty()) {
            ControlApplication.getApplication().getController().requestPeopleProfile();
        } else {
            recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(radicalsList));
        }
    }

    @TargetApi(23)
    private void requestPermissions() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this,
                new PermissionsResultAction() {
                    @Override
                    public void onGranted() {
                        TablePeopleProfile tablePeopleProfile = ControlApplication.getApplication().getDaoManager().getTablePeopleProfile();
                        List<People> radicalsList = tablePeopleProfile.queryAllData();
                        if (radicalsList == null || radicalsList.isEmpty()) {
                            ControlApplication.getApplication().getController().requestPeopleProfile();
                        } else {
                        }
                    }

                    @Override
                    public void onDenied(String permission) {
                    }
                });
    }


    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<People> mValues;
        private ImageLoader imageLoader;
        private DisplayImageOptions options_img; // 设置图片显示相关参数


        public SimpleItemRecyclerViewAdapter(List<People> items) {
            mValues = items;
            imageLoader = ImageLoader.getInstance();
            options_img = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub)
                    .showImageForEmptyUri(R.drawable.ic_stub)
                    .showImageOnFail(R.drawable.ic_stub)
                    .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                    .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                    .displayer(new RoundedBitmapDisplayer(360)) // 设置成圆角图片
                    .build(); // 构建完成
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_people, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mTvName.setText(mValues.get(position).getName());
            String headUrl = mValues.get(position).getHeadUrl();
            imageLoader.displayImage(headUrl, holder.mTvHead, options_img);
            if (position % 2 == 0) {
                holder.mView.setBackgroundColor(getResources().getColor(R.color.light));
                holder.mTvName.setTextColor(getResources().getColor(R.color.dark));
            } else {
                holder.mTvName.setTextColor(getResources().getColor(R.color.light));
                holder.mView.setBackgroundColor(getResources().getColor(R.color.dark));
            }

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(ItemListWorksFragment.ARG_ITEM_ID, holder.mItem.getName());
                        ItemListWorksFragment fragment = new ItemListWorksFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, ItemListWorksActivity.class);
                        intent.putExtra(ItemListWorksFragment.ARG_ITEM_ID, holder.mItem);
                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mTvName;
            public final ImageView mTvHead;
            public People mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mTvName = (TextView) view.findViewById(R.id.tv_name);
                mTvHead = (ImageView) view.findViewById(R.id.img_head);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTvName.getText() + "'";
            }
        }

    }
}
