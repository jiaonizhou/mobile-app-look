package mobile.mycloset;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

import mobile.mycloset.model.Closet;
import mobile.mycloset.model.TopBottomSuite;

public class FavFragment extends Fragment {
    private static FavFragment fragment;

    private View view;

    public static FavFragment getInstance() {
        if (fragment == null) {
            fragment = new FavFragment();
        }

        return fragment;
    }

    public static class FavViewAdapter extends RecyclerView.Adapter<FavViewAdapter.FavCardHolder> {
        public static class FavCardHolder extends RecyclerView.ViewHolder {
            protected ImageView bagView;
            protected ImageView shoeView;
            protected ImageView topView;
            protected ImageView bottomView;

            public FavCardHolder(View v) {
                super(v);
                bagView = (ImageView)v.findViewById(R.id.bag_placeholder);
                shoeView = (ImageView)v.findViewById(R.id.shoe_placeholder);
                topView = (ImageView)v.findViewById(R.id.top_placeholder);
                bottomView = (ImageView)v.findViewById(R.id.bottom_placeholder);

            }
        }

        protected List<TopBottomSuite> suites;
        protected Context context;

        public FavViewAdapter(List<TopBottomSuite> suites, Context context) {
            this.suites = suites;
            this.context = context;
        }

        @Override
        public int getItemCount() {
            return suites.size();
        }

        @Override
        public void onBindViewHolder(FavCardHolder contactViewHolder, int i) {
            TopBottomSuite suite = suites.get(i);
            contactViewHolder.bagView.setImageBitmap(suite.bag.getBitmap(context));
            contactViewHolder.shoeView.setImageBitmap(suite.shoe.getBitmap(context));
            contactViewHolder.topView.setImageBitmap(suite.top.getBitmap(context));
            contactViewHolder.bottomView.setImageBitmap(suite.bottom.getBitmap(context));
        }

        @Override
        public FavCardHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.suite_view, viewGroup, false);

            return new FavCardHolder(itemView);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fav_suite_view, container, false);
        initView(view);
        return view;
    }

    public void initView(View view) {
        final RecyclerView favView = (RecyclerView)view.findViewById(R.id.favView);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        favView.setLayoutManager(llm);

        FavViewAdapter adapter = new FavViewAdapter(Closet.getCloset().topBottomSuites, getActivity());
        favView.setAdapter(adapter);
    }
}
