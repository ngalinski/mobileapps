// code started from RvAdapter sample code file

package edu.neu.madcourse.numad20f_nicholasgalinski;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.LinkViewHolder> {
    private List<LinkItem> linkItems;
    private LinkItemListener itemListener;

    public void setItemListener(LinkItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public LinkAdapter(List<LinkItem> linkItems) {
        this.linkItems = linkItems;
    }

    public interface LinkItemListener {
        void onItemClick(int pos);
        void onCheckBoxClick(int position);
    }

    public static class LinkViewHolder extends RecyclerView.ViewHolder{
        public TextView linkName;
        public TextView linkContent;
        public CheckBox checkBox;

        public LinkViewHolder(@NonNull View itemView, final LinkItemListener listener) {
            super(itemView);
            linkContent = itemView.findViewById(R.id.link_content);
            linkName = itemView.findViewById(R.id.link_name);
            checkBox = itemView.findViewById(R.id.checkbox);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int pos = getLayoutPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            listener.onItemClick(pos);
                        }
                    }
                }
            });
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getLayoutPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onCheckBoxClick(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_link_list,
                parent, false);
        return new LinkViewHolder(view, itemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull LinkViewHolder holder, int position) {
        LinkItem linkItem = linkItems.get(position);
        holder.linkName.setText(linkItem.getName());
        holder.linkContent.setText(linkItem.getLinkURL());
        holder.checkBox.setChecked(linkItem.getStatus());
    }

    @Override
    public int getItemCount() {
        return linkItems.size();
    }
}