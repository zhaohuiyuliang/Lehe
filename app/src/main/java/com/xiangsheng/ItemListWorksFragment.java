package com.xiangsheng;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xiangsheng.dao.model.People;
import com.xiangsheng.dao.model.Works;

import java.util.List;

import static com.xiangsheng.ControlApplication.getApplication;


/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListPeopleActivity}
 * in two-pane mode (on tablets) or a {@link ItemListWorksActivity}
 * on handsets.
 */
public class ItemListWorksFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private People mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemListWorksFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = (People) getArguments().getSerializable(ARG_ITEM_ID);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        // Show the dummy content as text in a TextView.

        View recyclerView = rootView.findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        return rootView;
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        List<Works> items = getApplication().getDaoManager().getTableWorks().queryDatasByAuthorID(mItem.getID());
        if (items == null || items.isEmpty()) {

        } else {
            recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(items));
        }
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<ViewHolder> {

        private final List<Works> mValues;
        private ImageLoader imageLoader;
        private DisplayImageOptions options_img; // 设置图片显示相关参数


        public SimpleItemRecyclerViewAdapter(List<Works> items) {
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
                    .inflate(R.layout.item_list_works, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mTvName.setText(mValues.get(position).getName());
            if (position % 2 == 0) {
                holder.mView.setBackgroundColor(getResources().getColor(R.color.light));
                holder.mTvName.setTextColor(getResources().getColor(R.color.dark));
            } else {
                holder.mTvName.setTextColor(getResources().getColor(R.color.dark));
                holder.mView.setBackgroundColor(getResources().getColor(R.color.light));
            }

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra(ItemListWorksFragment.ARG_ITEM_ID, holder.mItem);

                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTvName;
        public final ImageView mTvHead;
        public Works mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTvName = (TextView) view.findViewById(R.id.tv_works_name);
            mTvHead = (ImageView) view.findViewById(R.id.img_video);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTvName.getText() + "'";
        }
    }
}
