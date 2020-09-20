package com.example.freechef;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DishAdapter extends ArrayAdapter<Dish> {
    private Context mContext;
    private List<Dish> DishesList = new ArrayList<>()   ;

    public DishAdapter(@NonNull Context context, @LayoutRes ArrayList<Dish> list) {
        super(context, 0 , list);
        mContext = context;
        DishesList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.activity_list,parent,false);

        Dish currentDish = DishesList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.Item_Name);
        name.setText(currentDish.getName());

        TextView description = (TextView) listItem.findViewById(R.id.Item_Description);
        description.setText(currentDish.getDescription());

        TextView price = (TextView) listItem.findViewById(R.id.Item_Price);
        price.setText(currentDish.getPrice());

        TextView rate = (TextView) listItem.findViewById(R.id.Item_Rate);
        rate.setText(currentDish.getRate());

        ImageView image = (ImageView)listItem.findViewById(R.id.Item_Image);
        Bitmap bmp= BitmapFactory.decodeByteArray(currentDish.getImage(), 0 , currentDish.getImage().length);
        image.setImageBitmap(bmp);

        return listItem;
    }
}
