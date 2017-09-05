package com.example.myapplication.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.utils.SnackbarUtils;
import com.example.myapplication.widget.GuideLayout;
import com.example.myapplication.widget.RotatableButton;
import com.example.myapplication.widget.RotatableImageButton;
import com.zhl.userguideview.UserGuideView;

import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created by xieH on 2017/2/9 0009.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private View mRootView;

    private String[] accounts = {"18236593333", "13463373657", "18235784765", "18234637686"};

    private TextInputLayout mAccountInputLayout, mPasswordInputLayout;
    private AppCompatAutoCompleteTextView mAccountEt;
    private EditText mPasswordEt;

    private Button mSignInBtn;

    private String account, password;

    private View mTipView;

    private RotatableButton mRotatableButton;
    private RotatableImageButton mRotatableImageButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        initGuideView();

        initGuideView11();
    }

    public void initView() {

        mRootView = findViewById(R.id.login_root_view);
        mTipView = findViewById(R.id.login_tip_bt);

        ////////////////////

        mRotatableButton = (RotatableButton) findViewById(R.id.rotatable_button);
        mRotatableButton.setAngle(10.0f);

        mRotatableImageButton = (RotatableImageButton) findViewById(R.id.rotatable_imageButton);
        mRotatableImageButton.setAngle(30.0f);

        mAccountInputLayout = (TextInputLayout) findViewById(R.id.account_inputLayout);
        mPasswordInputLayout = (TextInputLayout) findViewById(R.id.password_inputLayout);

        mAccountEt = (AppCompatAutoCompleteTextView) findViewById(R.id.account_et);
        mPasswordEt = (EditText) findViewById(R.id.password_et);

        mSignInBtn = (Button) findViewById(R.id.sign_in_btn);
        mSignInBtn.setOnClickListener(this);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, accounts);
        mAccountEt.setAdapter(arrayAdapter);   // 输入至少两个字符才会提示

        mAccountEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adjustSignInBtnState();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mAccountInputLayout.getError() != null) {
                    mAccountInputLayout.setError(null);
                }
            }
        });

        mPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adjustSignInBtnState();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mPasswordInputLayout.getError() != null) {
                    mPasswordInputLayout.setError(null);
                }
            }
        });
    }

    /**
     * 动态监测调整登录按钮状态
     */
    public void adjustSignInBtnState() {
        account = mAccountEt.getText().toString();
        password = mPasswordEt.getText().toString();

        if (account.equals("") || password.equals("")) {
            mSignInBtn.setClickable(false);
            mSignInBtn.setTextColor(ContextCompat.getColor(this, R.color.gray));
        } else {
            mSignInBtn.setClickable(true);
            mSignInBtn.setTextColor(ContextCompat.getColor(this, R.color.color_white));
        }
    }

    private boolean isAccountValid(String name) {
        Pattern pattern = Pattern.compile("^(13[0-9]|14[5|7]|15\\d|17[6|7]|18[\\d])\\d{8}$");
        return pattern.matcher(name).matches();
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    @Override
    public void onClick(View view) {
//        account = mAccountEt.getText().toString();
//        password = mPasswordEt.getText().toString();

        if (TextUtils.isEmpty(account) || !isAccountValid(account)) {
            mAccountInputLayout.setError("无效手机号");
        }

        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPasswordInputLayout.setError("密码小于6位");
        }
    }

    /**
     * 使用Java在一个区间内产生随机整数数
     *
     * @param min
     * @param max
     * @return
     */
    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    /**
     * 退出应用
     *
     * @param v
     */
    public void exit(View v) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("isExit", true);
        startActivity(intent);
    }

    public void showError(View v) {
        SnackbarUtils.showNetworkErrorSnackBar(this, mRootView, "网络错误 请检查网络", "设置");
    }


    ////////////////////////////////////////
    ////////////////////////////////////////
    private GuideLayout mGuide01Layout, mGuide02Layout;

    public void initGuideView() {
        final View mGuide01View = View.inflate(this, R.layout.layout_guide_01, null);
        mGuide01Layout = new GuideLayout(this);
        mGuide01Layout.setCustomView(mGuide01View);
        mGuide01Layout.setOnExitClickListener(R.id.guide_exit_view, new GuideLayout.OnExitClickListener() {
            @Override
            public void onClick() {
                mGuide01Layout.hide();
                mGuide02Layout.show();
            }
        });

        final View mGuide02View = View.inflate(this, R.layout.layout_guide_02, null);
        mGuide02Layout = new GuideLayout(this);
        mGuide02Layout.setCustomView(mGuide02View);
        mGuide02Layout.setOnExitClickListener(R.id.guide_exit_view, new GuideLayout.OnExitClickListener() {
            @Override
            public void onClick() {
                mGuide02Layout.hide();
            }
        });

        mGuide01Layout.show();
    }

    private UserGuideView mGuideView;
    private Button mGuideBtn, mGuide11Btn;

    public void initGuideView11() {
        mGuideView = (UserGuideView) findViewById(R.id.guideView);
        mGuideBtn = (Button) findViewById(R.id.guide_bt);
        mGuide11Btn = (Button) findViewById(R.id.guide11_bt);

        mGuideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGuideView.setHighLightView(mGuideBtn);
            }
        });

        mGuide11Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGuideView.setHighLightView(mGuide11Btn);
            }
        });
    }
}
