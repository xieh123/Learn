package com.example.myapplication.ui.tab2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.example.myapplication.R;
import com.example.myapplication.ui.tab2.contacts.CommonUtil;
import com.example.myapplication.ui.tab2.contacts.ContactAdapter;
import com.example.myapplication.ui.tab2.contacts.ContactBean;
import com.example.myapplication.ui.tab2.contacts.CustomItemDecoration;
import com.example.myapplication.ui.tab2.contacts.SideBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieH on 2017/6/22 0022.
 */
public class ContactsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ContactAdapter mAdapter;
    private CustomItemDecoration decoration;
    private LinearLayoutManager layoutManager;

    private SideBar mSideBar;
    private List<ContactBean> nameList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        initView();

        setData();
    }

    public void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mSideBar = (SideBar) findViewById(R.id.side_bar);

        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new ContactAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        decoration = new CustomItemDecoration(this);
        mRecyclerView.addItemDecoration(decoration);
//          mRecyclerView.setItemAnimator(new SlideInOutLeftItemAnimator(mRecyclerView));

        mSideBar.setIndexChangeListener(new SideBar.indexChangeListener() {
            @Override
            public void indexChanged(String tag) {
                if (TextUtils.isEmpty(tag) || nameList.size() <= 0) return;
                for (int i = 0; i < nameList.size(); i++) {
                    if (tag.equals(nameList.get(i).getIndexTag())) {
                        layoutManager.scrollToPositionWithOffset(i, 0);
//                        layoutManager.scrollToPosition(i);
                        return;
                    }
                }
            }
        });
    }

    private void setData() {
        String[] names = {"孙尚香", "安其拉", "白起", "不知火舞", "@小马快跑", "_德玛西亚之力_", "妲己", "狄仁杰", "典韦", "韩信",
                "老夫子", "刘邦", "刘禅", "鲁班七号", "墨子", "孙膑", "孙尚香", "孙悟空", "项羽", "亚瑟",
                "周瑜", "庄周", "蔡文姬", "甄姬", "廉颇", "程咬金", "后羿", "扁鹊", "钟无艳", "小乔", "王昭君", "虞姬",
                "李元芳", "张飞", "刘备", "牛魔", "张良", "兰陵王", "露娜", "貂蝉", "达摩", "曹操", "芈月", "荆轲", "高渐离",
                "钟馗", "花木兰", "关羽", "李白", "宫本武藏", "吕布", "嬴政", "娜可露露", "武则天", "赵云", "姜子牙",};
        for (String name : names) {
            ContactBean bean = new ContactBean();
            bean.setName(name);
            nameList.add(bean);
        }

        // 对数据源进行排序
        CommonUtil.sortData(nameList);
        // 返回一个包含所有Tag字母在内的字符串并赋值给tagsStr
        String tagsStr = CommonUtil.getTags(nameList);

        // 设置索引
//        mSideBar.setIndexStr(tagsStr);
        decoration.setData(nameList, tagsStr);
        mAdapter.addAll(nameList);
    }
}
