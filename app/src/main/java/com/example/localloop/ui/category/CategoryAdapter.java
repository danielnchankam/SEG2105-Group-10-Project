package com.example.localloop.ui.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localloop.R;
import com.example.localloop.model.Category;

import java.util.function.Consumer;

public class CategoryAdapter extends ListAdapter<Category, CategoryAdapter.VH> {

    interface EditClick extends Consumer<Integer>{}
    private final EditClick editClick;

    public CategoryAdapter(EditClick e) {
        super(DIFF);
        this.editClick = e;
    }

    /** --- FIXED block: both abstract methods implemented --- */
    private static final DiffUtil.ItemCallback<Category> DIFF =
            new DiffUtil.ItemCallback<Category>() {
                @Override
                public boolean areItemsTheSame(@NonNull Category oldItem,
                                               @NonNull Category newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull Category oldItem,
                                                  @NonNull Category newItem) {
                    return oldItem.getName().equals(newItem.getName()) &&
                            oldItem.getDescription().equals(newItem.getDescription());
                }
            };

    /* ---------- ViewHolder ---------- */
    static class VH extends RecyclerView.ViewHolder {
        TextView title, desc; Button edit;
        VH(View v) {
            super(v);
            title = v.findViewById(R.id.textTitle);
            desc  = v.findViewById(R.id.textDesc);
            edit  = v.findViewById(R.id.btnEdit);
        }
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup p, int vType) {
        View v = LayoutInflater.from(p.getContext())
                .inflate(R.layout.item_category, p, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Category c = getItem(pos);
        h.title.setText(c.getName());
        h.desc .setText(c.getDescription());
        h.edit.setOnClickListener(v -> editClick.accept(c.getId()));
    }
}
