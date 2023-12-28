package io.notehive.notes.data.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import io.notehive.notes.data.entity.CategoryInfo;
import io.notehive.notes.data.entity.ScheduleInfo;

import io.notehive.notes.data.dao.CategoryInfoDao;
import io.notehive.notes.data.dao.ScheduleInfoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig categoryInfoDaoConfig;
    private final DaoConfig scheduleInfoDaoConfig;

    private final CategoryInfoDao categoryInfoDao;
    private final ScheduleInfoDao scheduleInfoDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        categoryInfoDaoConfig = daoConfigMap.get(CategoryInfoDao.class).clone();
        categoryInfoDaoConfig.initIdentityScope(type);

        scheduleInfoDaoConfig = daoConfigMap.get(ScheduleInfoDao.class).clone();
        scheduleInfoDaoConfig.initIdentityScope(type);

        categoryInfoDao = new CategoryInfoDao(categoryInfoDaoConfig, this);
        scheduleInfoDao = new ScheduleInfoDao(scheduleInfoDaoConfig, this);

        registerDao(CategoryInfo.class, categoryInfoDao);
        registerDao(ScheduleInfo.class, scheduleInfoDao);
    }
    
    public void clear() {
        categoryInfoDaoConfig.clearIdentityScope();
        scheduleInfoDaoConfig.clearIdentityScope();
    }

    public CategoryInfoDao getCategoryInfoDao() {
        return categoryInfoDao;
    }

    public ScheduleInfoDao getScheduleInfoDao() {
        return scheduleInfoDao;
    }

}