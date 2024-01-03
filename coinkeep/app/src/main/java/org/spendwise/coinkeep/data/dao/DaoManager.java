package org.spendwise.coinkeep.data.dao;


import org.spendwise.coinkeep.MyApp;

public class DaoManager {

    private static final String DB_Name = "coinkeepDB.db";

    private static volatile DaoManager INSTANCE;

    private DaoMaster daoMaster;

    private DaoSession daoSession;

    private DaoManager() {
        if (INSTANCE == null) {
            DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(MyApp.context,DB_Name);
            daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
            daoSession = daoMaster.newSession();
        }
    }

    public static DaoManager getInstance() {
        if (INSTANCE == null) {
            synchronized (DaoManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DaoManager();
                }
            }
        }
        return INSTANCE;
    }

    public DaoMaster getDaoMaster() {
        return daoMaster;
    }


    public DaoSession getDaoSession() {
        return daoSession;
    }

    public DaoSession getNewSession() {
        daoSession = daoMaster.newSession();
        return daoSession;
    }
}
