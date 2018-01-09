package com.example.myapplication.ui.tab2;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;

import com.example.myapplication.R;
import com.example.myapplication.model.Item;
import com.example.myapplication.widget.loadmoreadapter.BaseNormalAdapter;
import com.example.myapplication.widget.loadmoreadapter.ViewHolder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by xieH on 2017/10/24 0024.
 */
public class DeviceInfoActivity extends AppCompatActivity {

    private TelephonyManager phone;
    private WifiManager wifi;
    private Display display;
    private DisplayMetrics metrics;

    //////////

    private RecyclerView mRecyclerView;
    private BaseNormalAdapter<Item> mBaseNormalAdapter;
    private List<Item> mItemList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);

        initView();

        getData();
    }

    private void initView() {
        phone = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        display = getWindowManager().getDefaultDisplay();

        metrics = getResources().getDisplayMetrics();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();
        mRecyclerView.setAdapter(mBaseNormalAdapter);

    }

    private void initAdapter() {
        mBaseNormalAdapter = new BaseNormalAdapter<Item>(this, mItemList, false) {
            @Override
            protected void convert(ViewHolder holder, Item item, int position) {
                holder.setText(R.id.item_title_tv, item.getTitle());
                holder.setText(R.id.item_text_tv, item.getText());
            }

            @Override
            protected int getItemLayoutId() {
                return R.layout.item_recycler_device_info;
            }
        };
    }


    private void getData() {
        HashMap<String, String> map = new HashMap<>(20);

        DisplayMetrics densityDpi = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(densityDpi);

        try {
            Class localClass = Class.forName("android.os.SystemProperties");
            Object localObject1 = localClass.newInstance();
            Object localObject2 = localClass.getMethod("get", new Class[]{String.class, String.class}).invoke(localObject1, new Object[]{"gsm.version.baseband", "no message"});
            Object localObject3 = localClass.getMethod("get", new Class[]{String.class, String.class}).invoke(localObject1, new Object[]{"ro.build.display.id", ""});

            map.put("get", localObject2 + "");
            map.put("osVersion", localObject3 + "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //获取网络连接管理者
        ConnectivityManager connectionManager = (ConnectivityManager)
                getSystemService(CONNECTIVITY_SERVICE);
        //获取网络的状态信息，有下面三种方式
        NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();

        map.put("联网方式", networkInfo.getType() + "");
        map.put("联网方式名称", networkInfo.getTypeName());
        map.put("imei号", phone.getDeviceId());
        map.put("系统版本", phone.getDeviceSoftwareVersion());
        map.put("imsi号", phone.getSubscriberId());
        map.put("手机号", phone.getLine1Number());
        map.put("手机卡序列号", phone.getSimSerialNumber());
        map.put("运营商", phone.getSimOperator());
        map.put("运营商名称", phone.getSimOperatorName());
        map.put("国家iso代码", phone.getSimCountryIso());
        map.put("网络类型", phone.getNetworkType() + "");
        map.put("手机卡国家", phone.getNetworkCountryIso());
        map.put("网络运营商类型", phone.getNetworkOperator());
        map.put("网络类型名", phone.getNetworkOperatorName());

        map.put("固件版本", android.os.Build.getRadioVersion());
        map.put("mac地址", wifi.getConnectionInfo().getMacAddress());
        map.put("无线路由名", wifi.getConnectionInfo().getSSID());
        map.put("无线路由地址", wifi.getConnectionInfo().getBSSID());
        map.put("ip地址", wifi.getConnectionInfo().getIpAddress() + "");
        map.put("蓝牙地址", BluetoothAdapter.getDefaultAdapter().getAddress());
        map.put("蓝牙名称", BluetoothAdapter.getDefaultAdapter().getName());

        map.put("android id", Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
        map.put("串口序列号", android.os.Build.SERIAL);
        map.put("品牌", android.os.Build.BRAND);
        map.put("描述build的标签", android.os.Build.TAGS);
        map.put("设备名", android.os.Build.DEVICE);
        map.put("指纹", android.os.Build.FINGERPRINT);
        map.put("引导程序", Build.BOOTLOADER);
        map.put("系统版本", Build.VERSION.RELEASE);
        map.put("系统版本", Build.VERSION.SDK);
        map.put("系统版本", Build.VERSION.SDK_INT + "");
        map.put("固件开发版本代号", Build.VERSION.CODENAME);
        map.put("源码控制版本号", Build.VERSION.INCREMENTAL);
        map.put("cpu型号", getCpuName());
        map.put("cpu指令集", android.os.Build.CPU_ABI);
        map.put("cpu指令集2", android.os.Build.CPU_ABI2);
        map.put("主板", android.os.Build.BOARD);
        map.put("型号", android.os.Build.MODEL);
        map.put("产品名称", android.os.Build.PRODUCT);
        map.put("设备版本类型", android.os.Build.TYPE);
        map.put("设备用户名", android.os.Build.USER);
        map.put("显示屏幕参数", android.os.Build.DISPLAY);
        map.put("硬件名称", android.os.Build.HARDWARE);
        map.put("设备主机地址", android.os.Build.HOST);
        map.put("制造商", android.os.Build.MANUFACTURER);
        map.put("手机类型", phone.getPhoneType() + "");
        map.put("手机卡状态", phone.getSimState() + "");
        map.put("id", Build.ID);
        map.put("build时间", android.os.Build.TIME + "");
        map.put("屏幕宽度", display.getWidth() + "");
        map.put("屏幕高度", display.getHeight() + "");
        map.put("dpi", densityDpi.densityDpi + "");
        map.put("density", densityDpi.density + "");
        map.put("x像素", densityDpi.xdpi + "");
        map.put("y像素", densityDpi.ydpi + "");
        map.put("字体缩小比例", densityDpi.scaledDensity + "");
        map.put("UUID", getMyUUID());

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        map.put("当前分辨率", width + "");
        map.put("当前分辨率", height + "");

        for (Map.Entry<String, String> entry : map.entrySet()) {
            Item item = new Item();
            item.setTitle(entry.getKey());
            item.setText(entry.getValue());

            mItemList.add(item);
        }

        mBaseNormalAdapter.refresh(mItemList);

    }


    /**
     * 获取CPU型号
     *
     * @return
     */
    public static String getCpuName() {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr);
            while ((str2 = localBufferedReader.readLine()) != null) {
                if (str2.contains("Hardware")) {
                    return str2.split(":")[1];
                }
            }
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return null;
    }


    /**
     * 设备 uuid
     *
     * @return
     */
    private String getMyUUID() {
        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        System.out.println("hhh---uuid=" + uniqueId);
        return uniqueId;
    }
}
