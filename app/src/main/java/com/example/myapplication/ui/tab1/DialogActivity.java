package com.example.myapplication.ui.tab1;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.fragment.dialog.BaseDialogFragment;
import com.example.myapplication.fragment.dialog.DialogViewHolder;
import com.example.myapplication.fragment.dialog.NiceDialogFragment;
import com.example.myapplication.util.DialogFragmentHelper;
import com.example.myapplication.util.IDialogResultListener;
import com.example.myapplication.widget.CommonDialogFragment;

import java.util.Calendar;

/**
 * Created by xieH on 2017/4/17 0017.
 */
public class DialogActivity extends AppCompatActivity implements View.OnClickListener {

    private DialogFragment mDialogFragment;

    private LinearLayout mDialogLl;

    private LinearLayout mDialogFragmentLl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        initView();
    }

    public void initView() {
        mDialogLl = (LinearLayout) findViewById(R.id.dialog_ll);

        int count = mDialogLl.getChildCount();
        for (int i = 0; i < count; i++) {
            Button button = (Button) mDialogLl.getChildAt(i);
            button.setTag(i);
            button.setOnClickListener(this);
        }

        mDialogFragmentLl = (LinearLayout) findViewById(R.id.dialog_fragment_ll);
        for (int i = 0; i < mDialogFragmentLl.getChildCount(); i++) {
            Button button = (Button) mDialogFragmentLl.getChildAt(i);
            button.setTag(i);
            button.setOnClickListener(mOnClickListener);
        }
    }

    @Override
    public void onClick(View view) {
        int pos = (int) view.getTag();

        switch (pos) {
            case 0:
                mDialogFragment = DialogFragmentHelper.showProgress(getSupportFragmentManager(), "正在加载中");
                break;
            case 1:
                DialogFragmentHelper.showTips(getSupportFragmentManager(), "你进入了无网的异次元中");
                break;
            case 2:
                showConfirmDialog();
                break;
            case 3:
                showListDialog();
                break;
            case 4:
                showDateDialog();
                break;
            case 5:
                showTimeDialog();
                break;
            case 6:
                showInsertDialog();
                break;
            case 7:
                showPasswordInsertDialog();
                break;
            case 8:
                showIntervalInsertDialog();
                break;
            default:
                break;
        }
    }


    /**
     * 选择时间的弹出窗
     */
    private void showTimeDialog() {
        String titleTime = "请选择时间";
        Calendar calendarTime = Calendar.getInstance();
        DialogFragmentHelper.showTimeDialog(getSupportFragmentManager(), titleTime, calendarTime, new IDialogResultListener<Calendar>() {
            @Override
            public void onDataResult(Calendar result) {

            }
        }, true);
    }

    /**
     * 输入密码的弹出窗
     */
    private void showPasswordInsertDialog() {
        String titlePassword = "请输入密码";
        DialogFragmentHelper.showPasswordInsertDialog(getSupportFragmentManager(), titlePassword, new IDialogResultListener<String>() {
            @Override
            public void onDataResult(String result) {

            }
        }, true);
    }

    /**
     * 显示列表的弹出窗
     */
    private void showListDialog() {
        String titleList = "选择哪种方向？";
        final String[] languages = new String[]{"Android", "iOS", "web 前端", "Web 后端", "老子不打码了"};

        DialogFragmentHelper.showListDialog(getSupportFragmentManager(), titleList, languages, new IDialogResultListener<Integer>() {
            @Override
            public void onDataResult(Integer result) {

            }
        }, true);
    }

    /**
     * 两个输入框的弹出窗
     */
    private void showIntervalInsertDialog() {
        String title = "请输入想输入的内容";
        DialogFragmentHelper.showIntervalInsertDialog(getSupportFragmentManager(), title, new IDialogResultListener<String[]>() {
            @Override
            public void onDataResult(String[] result) {

            }
        }, true);
    }

    private void showInsertDialog() {
        String titleInsert = "请输入想输入的内容";
        DialogFragmentHelper.showInsertDialog(getSupportFragmentManager(), titleInsert, new IDialogResultListener<String>() {
            @Override
            public void onDataResult(String result) {

            }
        }, true);
    }

    /**
     * 选择日期的弹出窗
     */
    private void showDateDialog() {
        String titleDate = "请选择日期";
        Calendar calendar = Calendar.getInstance();
        mDialogFragment = DialogFragmentHelper.showDateDialog(getSupportFragmentManager(), titleDate, calendar, new IDialogResultListener<Calendar>() {
            @Override
            public void onDataResult(Calendar result) {

            }
        }, true);
    }

    /**
     * 确认和取消的弹出窗
     */
    private void showConfirmDialog() {
        DialogFragmentHelper.showConfirmDialog(getSupportFragmentManager(), "是否选择 Android？", new IDialogResultListener<Integer>() {
            @Override
            public void onDataResult(Integer result) {

            }
        }, true, new CommonDialogFragment.OnDialogCancelListener() {
            @Override
            public void onCancel() {

            }
        });
    }

    ///////////////////////////////////////

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int pos = (int) view.getTag();

            switch (pos) {
                case 0:
                    NiceDialogFragment.init()
                            .setLayoutId(R.layout.dialog_share)
                            .setConvertListener(new BaseDialogFragment.ViewConvertListener() {
                                @Override
                                public void convertView(DialogViewHolder holder, final BaseDialogFragment dialog) {
                                    holder.setOnClickListener(R.id.wechat_tv, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Toast.makeText(DialogActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            })
                            .setDimAmount(0.3f)
                            .setShowBottom(true)
                            .setAnimStyle(R.style.EnterExitAnimation)
                            .show(getSupportFragmentManager());
                    break;
                case 1:
                    NiceDialogFragment.init()
                            .setLayoutId(R.layout.dialog_comment)
                            .setConvertListener(new BaseDialogFragment.ViewConvertListener() {
                                @Override
                                public void convertView(DialogViewHolder holder, final BaseDialogFragment dialog) {
                                    final EditText editText = holder.getView(R.id.edit_input);
                                    editText.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                            imm.showSoftInput(editText, 0);
                                        }
                                    });
                                }
                            })
                            .setShowBottom(true)
                            .setAnimStyle(R.style.EnterExitAnimation)
                            .show(getSupportFragmentManager());
                    break;
                case 2:
                    NiceDialogFragment.init()
                            .setLayoutId(R.layout.dialog_lucky_money)
                            .setConvertListener(new BaseDialogFragment.ViewConvertListener() {
                                @Override
                                public void convertView(DialogViewHolder holder, final BaseDialogFragment dialog) {
                                    holder.setOnClickListener(R.id.close, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });
                                }
                            })
                            .setWidth(210)
                            .setOutCancel(false)
                            .setAnimStyle(R.style.EnterExitAnimation)
                            .show(getSupportFragmentManager());
                    break;
                case 3:
                    NiceDialogFragment.init()
                            .setLayoutId(R.layout.dialog_loading11)
                            .setWidth(100)
                            .setHeight(100)
                            .setDimAmount(0)
                            .show(getSupportFragmentManager());
                    break;
                case 4:
                    NiceDialogFragment.init()
                            .setLayoutId(R.layout.dialog_friend_set)
                            .setConvertListener(new BaseDialogFragment.ViewConvertListener() {
                                @Override
                                public void convertView(DialogViewHolder holder, final BaseDialogFragment dialog) {

                                }
                            })
                            .setShowBottom(true)
                            .setHeight(310)
                            .setAnimStyle(R.style.EnterExitAnimation)
                            .show(getSupportFragmentManager());
                    break;
                case 5:
                    ConfirmDialog.newInstance("1")
                            .setMargin(60)
                            .setOutCancel(false)
                            .show(getSupportFragmentManager());
                    break;
                case 6:
                    ConfirmDialog.newInstance("2")
                            .setMargin(60)
                            .setOutCancel(false)
                            .show(getSupportFragmentManager());
                    break;
                default:
                    break;
            }
        }
    };

    public static class ConfirmDialog extends NiceDialogFragment {
        private String type;

        public static ConfirmDialog newInstance(String type) {
            Bundle bundle = new Bundle();
            bundle.putString("type", type);
            ConfirmDialog dialog = new ConfirmDialog();
            dialog.setArguments(bundle);
            return dialog;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle bundle = getArguments();
            if (bundle == null) {
                return;
            }
            type = bundle.getString("type");
        }

        @Override
        public int getLayoutId() {
            return R.layout.dialog_confirm;
        }

        @Override
        public void convertView(DialogViewHolder holder, final BaseDialogFragment dialog) {
            if ("1".equals(type)) {
                holder.setText(R.id.title, "提示");
                holder.setText(R.id.message, "您已支付成功！");
            } else if ("2".equals(type)) {
                holder.setText(R.id.title, "警告");
                holder.setText(R.id.message, "您的账号已被冻结！");
            }
            holder.setOnClickListener(R.id.cancel, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            holder.setOnClickListener(R.id.ok, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
    }
}
