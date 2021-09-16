package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
//Displaying data from the model in the different recycler view
public class ItemsNotify extends RecyclerView.Adapter<ItemsNotify.ViewHolder> {
    public  interface OnClickListener {
        void onItemClicked(int position);
    }

    public interface OnLongClickListener{
        void onItemLongClicked(int position);
    }
    List<String> itemArray;
    OnLongClickListener longClickListener;
    OnClickListener clickListener;

    public ItemsNotify(List<String> itemArray, OnLongClickListener longClickListener, OnClickListener clickListener){
        this.itemArray = itemArray;
        this.longClickListener = longClickListener;
        this.clickListener = clickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        //The layout inflate a view
        View todolist = LayoutInflater.from(parent.getContext()). inflate(android.R.layout.simple_list_item_1, parent, false);
        //place it inside a viewholder and return it
        return new ViewHolder(todolist);
    }
//Binding data to a viewholder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        String itemsArray = itemArray.get(position);
        holder.bind(itemsArray);
    }

    @Override
    public int getItemCount(){
        return itemArray.size();
    } //show how many of the RV items in the list
    class ViewHolder extends  RecyclerView.ViewHolder{
        TextView itemTxt;
        
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTxt = itemView.findViewById(android.R.id.text1);
        }
        //Shows the update of the view holder with this data
        public void bind(String itemArray){
            itemTxt.setText(itemArray);
            itemTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClicked(getAdapterPosition());

                }
            });
            itemTxt.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return  true; // Help remove any items in the recycler view
                }
            });
        }
    }


}

