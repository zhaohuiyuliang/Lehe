package com.xiangsheng.dao.impl;

import android.content.Context;

import com.xiangsheng.dao.TablePeopleProfile;
import com.xiangsheng.dao.TableWorks;
import com.xiangsheng.dao.TableWorksName;
import com.xiangsheng.dao.TableWorksSize;


/**
 * Created by wangliang on 2016/12/23.
 */

public class DaoManager {

    private Context context;

    private TablePeopleProfile mTablePeopleProfile;
    private TableWorksName mTableWorksName;
    private TableWorksSize mTableWorksSize;
    private TableWorks mTableWorks;

    public DaoManager(Context context) {
        this.context = context;
    }

    public TablePeopleProfile getTablePeopleProfile() {
        if (mTablePeopleProfile == null) {
            mTablePeopleProfile = new TablePeopleProfile(context);
        }
        return mTablePeopleProfile;
    }

    public TableWorksSize getTableWorksSize() {
        if (mTableWorksSize == null) {
            mTableWorksSize = new TableWorksSize(context);
        }
        return mTableWorksSize;

    }

    public TableWorksName getTableWorksName() {
        if (mTableWorksName == null) {
            mTableWorksName = new TableWorksName(context);
        }
        return mTableWorksName;
    }

    public TableWorks getTableWorks() {
        if (mTableWorks == null) {
            mTableWorks = new TableWorks(context);
        }
        return mTableWorks;
    }
}
