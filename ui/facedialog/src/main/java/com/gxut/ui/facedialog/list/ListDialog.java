package com.gxut.ui.facedialog.list;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gxut.ui.facedialog.R;
import com.gxut.ui.facedialog.common.DialogParamer;
import com.gxut.ui.facedialog.common.FaceDialogFragment;
import com.gxut.ui.facedialog.common.ListDialogParamer;
import com.gxut.ui.facedialog.common.OnItemSelectListener;
import com.gxut.ui.facedialog.common.OnMultiSelectListener;
import com.gxut.ui.facedialog.common.WidgetParamer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bitlike on 2018/1/25.
 */

public class ListDialog<T extends Parcelable> extends FaceDialogFragment implements View.OnClickListener {

    private final String KEY_TITLE = "KEY_TITLE";
    private final String KEY_MODELS = "KEY_MODELS";
    private final String KEY_CANCEL_PARAMER = "KEY_CANCEL_PARAMER";
    private ListAdapter mAdapter;

    @Override
    protected int getInflater() {
        boolean isBottom = false;
        DialogParamer mDialogParamer = getDialogParamer();
        if (mDialogParamer != null) {
            isBottom = mDialogParamer.gravity == Gravity.BOTTOM;
        }
        return isBottom ? R.layout.view_dialog_list_bottom : R.layout.view_dialog_list;
    }


    @Override
    protected void initView(View view) {
        Bundle args = getArguments();
        CharSequence title = "";
        String cancelText = "";
        int cancelTextSize = -1;
        int cancelTextColor = -1;
        boolean multi = false;
        List<ListModel<T>> models = null;
        if (args != null) {
            title = args.getCharSequence(KEY_TITLE, "");
            models = args.getParcelableArrayList(KEY_MODELS);
            WidgetParamer cancelWidgetParamer = args.getParcelable(KEY_CANCEL_PARAMER);
            if (cancelWidgetParamer != null) {
                cancelTextSize = cancelWidgetParamer.textSize;
                cancelText = cancelWidgetParamer.text;
                cancelTextColor = cancelWidgetParamer.textColor;
            }
            DialogParamer mDialogParamer = getDialogParamer();
            if (mDialogParamer != null && mDialogParamer instanceof ListDialogParamer) {
                ListDialogParamer mListDialogParamer = (ListDialogParamer) mDialogParamer;
                multi = mListDialogParamer.multi;
            }

        }
        TextView titleTv = view.findViewById(R.id.titleTv);
        TextView selectAllTv = view.findViewById(R.id.selectAllTv);
        TextView sureTv = view.findViewById(R.id.sureTv);
        RecyclerView mRecyclerView = view.findViewById(R.id.mRecyclerView);
        TextView cancelTv = view.findViewById(R.id.cancelTv);
        titleTv.setText(title);
        if (cancelTextSize > 0) {
            cancelTv.setTextSize(cancelTextSize);
        }
        cancelTv.setText(cancelText);

        if (cancelTextColor > 0) {
            cancelTv.setTextColor(cancelTextColor);
        }

        //处理item
        if (models != null && models.size() > 5) {
            ViewGroup.LayoutParams params = mRecyclerView.getLayoutParams();
            if (params != null) {
                DisplayMetrics dm = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
                params.height = dm.heightPixels / 2;
                mRecyclerView.setLayoutParams(params);
            }
        }
        if (multi) {
            selectAllTv.setVisibility(View.VISIBLE);
            sureTv.setVisibility(View.VISIBLE);
            selectAllTv.setOnClickListener(this);
            sureTv.setOnClickListener(this);
        } else {
            selectAllTv.setVisibility(View.GONE);
            sureTv.setVisibility(View.GONE);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new RecyclerItemDecoration(1));
        mAdapter = new ListAdapter(multi, models);
        mRecyclerView.setAdapter(mAdapter);
        cancelTv.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.cancelTv) {
            WidgetParamer cancelWidgetParamer = getWidgetParamer(KEY_CANCEL_PARAMER);
            if (cancelWidgetParamer != null) {
                View.OnClickListener cancelOnclickListener = cancelWidgetParamer.onClickListener;
                if (cancelOnclickListener != null) {
                    cancelOnclickListener.onClick(v);
                }
            }
            dismiss();
        } else if (id == R.id.selectAllTv && mAdapter != null) {
            if (!TextUtils.isEmpty(getSelectAllTv().getText())) {
                if (getSelectAllTv().getText().toString().equals("全不选")) {
                    mAdapter.selectAll(false);
                } else {
                    mAdapter.selectAll(true);
                }
            }
        } else if (id == R.id.sureTv && mAdapter != null) {
            List<ListModel<T>> selectModels = mAdapter.getSelectModels();
            DialogParamer mDialogParamer = getDialogParamer();
            if (mDialogParamer != null && mDialogParamer instanceof ListDialogParamer) {
                ListDialogParamer mListDialogParamer = (ListDialogParamer) mDialogParamer;
                OnMultiSelectListener onMultiSelectListener = mListDialogParamer.onMultiSelectListener;
                if (onMultiSelectListener != null) {
                    onMultiSelectListener.selected(selectModels);
                }
            }
            dismiss();
        }
    }


    private class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
        private List<ListModel<T>> selectModels;
        private List<ListModel<T>> models;
        private boolean multi;

        public ListAdapter(boolean multi, List<ListModel<T>> models) {
            this.models = models;
            this.multi = multi;
            if (multi) {
                selectModels = new ArrayList<>();
                if (this.models != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (ListModel<T> e : ListAdapter.this.models) {
                                if (e.isClicked()) {
                                    selectModels.add(e);
                                }
                            }
                        }
                    }).start();
                }
            }
        }

        public void selectAll(boolean selectAll) {
            for (ListModel<T> e : models) {
                e.setClicked(selectAll);
            }
            if (selectModels == null) {
                selectModels = new ArrayList<>();
            } else {
                selectModels.clear();
            }
            if (selectAll) {
                selectModels.addAll(models);
            }
            notifyDataSetChanged();
            updateSelectAllTv();
        }

        private void updateSelectAllTv() {
            if (multi) {
                if (selectModels.size() == models.size()) {
                    getSelectAllTv().setText("全不选");
                } else {
                    getSelectAllTv().setText("全选");
                }
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ListModel<T> model = models.get(position);
            holder.contentTv.setText(model.getContent());

            if (multi && model.isClicked()) {
                holder.itemView.setBackgroundResource(R.drawable.cyan_bg);
            } else {
                holder.itemView.setBackgroundResource(R.drawable.selector_white_hint_bg);
            }
            holder.itemView.setPadding(20, 40, 20, 40);
            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(onClickListener);
        }

        @Override
        public int getItemCount() {
            return models == null ? 0 : models.size();
        }

        public List<ListModel<T>> getSelectModels() {
            return selectModels;
        }

        private View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null && v.getTag() != null && v.getTag() instanceof Integer) {
                    int position = (int) v.getTag();
                    if (getItemCount() > position) {
                        ListModel<T> model = models.get(position);
                        if (multi) {
                            if (model.isClicked()) {
                                selectModels.remove(model);
                            } else {
                                selectModels.add(model);
                            }
                            model.setClicked(!model.isClicked());
                            notifyItemChanged(position);
                            updateSelectAllTv();
                        } else {
                            DialogParamer mDialogParamer = getDialogParamer();
                            if (mDialogParamer != null && mDialogParamer instanceof ListDialogParamer) {
                                ListDialogParamer mListDialogParamer = (ListDialogParamer) mDialogParamer;
                                OnItemSelectListener onItemSelectListener = mListDialogParamer.onItemSelectListener;
                                if (onItemSelectListener != null) {
                                    onItemSelectListener.click(model);
                                }
                            }
                            dismiss();
                        }
                    }
                }
            }
        };

        class ViewHolder extends RecyclerView.ViewHolder {
            AppCompatTextView contentTv;

            public ViewHolder(ViewGroup mViewGroup) {
                this(LayoutInflater.from(getActivity()).inflate(R.layout.item_list_dialog, mViewGroup, false));
            }

            public ViewHolder(View itemView) {
                super(itemView);
                contentTv = itemView.findViewById(R.id.contentTv);
            }
        }
    }

    private TextView getSelectAllTv() {
        return findViewById(R.id.selectAllTv);
    }

    public void show(FragmentManager mManager, CharSequence title, ArrayList<ListModel<T>> models, ListDialogParamer<T> mDialogParamer, WidgetParamer cancelWidgetParamer) {
        if (mDialogParamer != null && mDialogParamer.animationsStyle <= 0) {
            mDialogParamer.animationsStyle = R.style.DialogBottomAnim;
        }
        Bundle bundle = new Bundle();
        bundle.putCharSequence(KEY_TITLE, title);
        bundle.putParcelable(KEY_CANCEL_PARAMER, cancelWidgetParamer);
        bundle.putParcelableArrayList(KEY_MODELS, models);
        bundle.putParcelable(KEY_DIALOG_PARAMER, mDialogParamer);
        setArguments(bundle);
        show(mManager, TAG);
    }

}
