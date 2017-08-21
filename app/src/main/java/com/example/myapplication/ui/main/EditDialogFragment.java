package com.example.myapplication.ui.main;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.myapplication.R;

/**
 * Created by xieh on 16/6/17.
 */

public class EditDialogFragment extends AppCompatDialogFragment implements AdapterView.OnItemSelectedListener {

    private Context mContext;

    private EditText textEt;

    private LinearLayout colorLl;

    private Spinner spinner;

    private int[] colors = new int[]{R.color.color_01, R.color.color_02, R.color.color_03, R.color.color_04};

    private int curColor = 0;
    private int select = 0;
    private int[] sizes = new int[]{16, 18, 20, 25, 30};

    OnAddClickListener onAddClickListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("添加水印");
        LayoutInflater factory = LayoutInflater.from(mContext);
        final View dialogView = factory.inflate(R.layout.fragment_edit, null);
        initView(dialogView);
        builder.setView(dialogView);

        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("添加", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //取出输入字符串 没有输入用hint文本作为默认值
                String input = textEt.getText().toString();
                if (TextUtils.isEmpty(input)) {
                    input = textEt.getHint().toString();
                }

                onAddClickListener.add(input, sizes[select], ContextCompat.getColor(mContext, colors[curColor]));
            }
        });

        return builder.create();
    }

    private void initView(View dialogView) {

        textEt = (EditText) dialogView.findViewById(R.id.water_image_text_et);
        colorLl = (LinearLayout) dialogView.findViewById(R.id.water_image_color_ll);
        spinner = (Spinner) dialogView.findViewById(R.id.spinner);

        for (int i = 0; i < colorLl.getChildCount(); i++) {

            ImageView imageView = (ImageView) colorLl.getChildAt(i);

            imageView.setTag(i);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    curColor = (int) view.getTag();
                    textEt.setTextColor(ContextCompat.getColor(mContext, colors[curColor]));
                }
            });

        }


        initSpinner();
    }


    private void initSpinner() {
        String sizes[] = this.getResources().getStringArray(R.array.sizes);
        ArrayAdapter adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, sizes);

        // 为adapter设置下拉菜单样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // spinner设置adapter
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        select = i;
        textEt.setTextSize(sizes[select]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public interface OnAddClickListener {
        public void add(String inputText, int textSize, int color);
    }

    public void setOnAddClickListener(OnAddClickListener onAddClickListener) {
        this.onAddClickListener = onAddClickListener;
    }


}
