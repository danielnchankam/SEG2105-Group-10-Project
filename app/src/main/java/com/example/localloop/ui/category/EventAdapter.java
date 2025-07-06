package com.example.localloop.ui.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localloop.R;
import com.example.localloop.model.Event;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private final List<Event> eventList;
    private final OnEditClickListener listener;

    public interface OnEditClickListener {
        void onEditClick(Event event);
    }

    public EventAdapter(List<Event> eventList, OnEditClickListener listener) {
        this.eventList = eventList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.textViewName.setText(event.getName());
        holder.textViewDateTime.setText(event.getDate() + " " + event.getTime());
        holder.buttonEdit.setOnClickListener(v -> listener.onEditClick(event));
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewDateTime;
        Button buttonEdit;

        ViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDateTime = itemView.findViewById(R.id.textViewDateTime);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
        }
    }
}
