package be.florien.teambuilder.adapter;

import android.util.SparseArray;
import android.view.View;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class AbstractBaseAdapter<T> extends BaseAdapter {
    
    public static class ViewHolder {
        // I added a generic return type to reduce the casting noise in client code
        @SuppressWarnings("unchecked")
        public static <T extends View> T get(View view, int id) {
            SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
            if (viewHolder == null) {
                viewHolder = new SparseArray<View>();
                view.setTag(viewHolder);
            }
            View childView = viewHolder.get(id);
            if (childView == null) {
                childView = view.findViewById(id);
                viewHolder.put(id, childView);
            }
            return (T) childView;
        }
    }
    
    private List<T> mItems;

    @Override
    public int getCount() {
        return mItems == null? 0 : mItems.size();
    }

    @Override
    public T getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    
    public void setItems(List<T> items){
        mItems = items;
        notifyDataSetChanged();
    }


}
