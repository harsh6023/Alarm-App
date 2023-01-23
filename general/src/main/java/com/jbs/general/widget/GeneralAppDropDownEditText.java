package com.jbs.general.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.jbs.general.R;

import java.util.ArrayList;


public class GeneralAppDropDownEditText extends AppCompatTextView implements View.OnClickListener {

    private ArrayList<String> options = new ArrayList<>();
    private int selectedOptions = -1;
    private OnItemClickListener mListener;

    public GeneralAppDropDownEditText(Context context) {
        super(context);
        initView();
    }

    public GeneralAppDropDownEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public GeneralAppDropDownEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        this.setOnClickListener(this);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private PopupWindow popupWindowsort(Context context) {

        // initialize a pop up window type
        PopupWindow popupWindow = new PopupWindow(context);
        popupWindow.setWidth(this.getWidth());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.item_drop_down_text,
                options);
        // the drop down list is a list view
        ListView listViewSort = new ListView(context);

        // set our adapter and pass our pop up window contents
        listViewSort.setAdapter(adapter);

        // set on item selected
        listViewSort.setOnItemClickListener((parent, view, position, id) -> {
            this.setText(options.get(position));
            if (mListener != null) {
                this.mListener.onItemClick(position);
            }
            selectedOptions = position;
            popupWindow.dismiss();
        });

        // some other visual settings for popup window
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_background));
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        // set the listview as popup content
        popupWindow.setContentView(listViewSort);

        return popupWindow;
    }

    @Override
    public void onClick(View v) {
        if (v == this) {
            PopupWindow window = popupWindowsort(v.getContext());
            window.showAsDropDown(v, 0, 0);
        }
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public int getSelectedPosition(){
        return selectedOptions;
    }
}
