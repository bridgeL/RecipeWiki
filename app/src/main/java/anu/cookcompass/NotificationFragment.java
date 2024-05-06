package anu.cookcompass;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import anu.cookcompass.model.PopMsg;
import anu.cookcompass.model.ThemeColor;
import anu.cookcompass.model.ThemeConfig;

public class NotificationFragment extends Fragment {
    private View rootView;
    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private List<PopMsg> notificationList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_search, container, false);
        //change the color when create
        ThemeConfig themeConfig = ((MainActivity) requireActivity()).getThemeConfig();
        rootView.setBackgroundColor(Color.parseColor(themeConfig.getTheme()));
        System.out.println("theme config in notification" + themeConfig.getTheme());

        recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new NotificationAdapter(notificationList);
        recyclerView.setAdapter(adapter);

        // set initial theme
        rootView.setBackgroundColor(Color.parseColor(ThemeColor.getThemeColor()));
        return rootView;
    }

    public void onNewNotification(PopMsg popMsg){
        notificationList.add(0, popMsg);
        adapter.notifyItemInserted(0);
        recyclerView.smoothScrollToPosition(0);
    }

    private class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
        private List<PopMsg> dataSet;

        public NotificationAdapter(List<PopMsg> dataSet) {
            this.dataSet = dataSet;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
            return new RecyclerView.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            PopMsg popMsg = dataSet.get(position);
            String message = String.format("%s from %s just favorite the recipe %s.", popMsg.getUsername(), popMsg.getLocation(), popMsg.getTitle());
            holder.notificationText.setText(message);

            // Set the layout parameters to position the item on the left or right side
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
            if (position % 2 == 0) {
                params.gravity = Gravity.START;
            } else {
                params.gravity = Gravity.END;
            }
            holder.itemView.setLayoutParams(params);

            holder.itemView.setOnClickListener(v -> {
                // Handle item click and dismissal
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    dataSet.remove(adapterPosition);
                    notifyItemRemoved(adapterPosition);
                }
            });
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView notificationText;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                notificationText = itemView.findViewById(R.id.notification_text);
            }
        }
    }


}