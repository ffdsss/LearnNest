package org.spendwise.coinkeep.data.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import org.spendwise.coinkeep.data.entity.AccountInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "accountInfo".
*/
public class AccountInfoDao extends AbstractDao<AccountInfo, Long> {

    public static final String TABLENAME = "accountInfo";

    /**
     * Properties of entity AccountInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Money = new Property(1, String.class, "money", false, "money");
        public final static Property Category = new Property(2, String.class, "category", false, "category");
        public final static Property IsIncome = new Property(3, String.class, "isIncome", false, "is_income");
        public final static Property Remark = new Property(4, String.class, "remark", false, "remark");
        public final static Property Date = new Property(5, String.class, "date", false, "date");
        public final static Property Time = new Property(6, String.class, "time", false, "time");
    }


    public AccountInfoDao(DaoConfig config) {
        super(config);
    }
    
    public AccountInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"accountInfo\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"money\" TEXT," + // 1: money
                "\"category\" TEXT," + // 2: category
                "\"is_income\" TEXT," + // 3: isIncome
                "\"remark\" TEXT," + // 4: remark
                "\"date\" TEXT," + // 5: date
                "\"time\" TEXT);"); // 6: time
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"accountInfo\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AccountInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String money = entity.getMoney();
        if (money != null) {
            stmt.bindString(2, money);
        }
 
        String category = entity.getCategory();
        if (category != null) {
            stmt.bindString(3, category);
        }
 
        String isIncome = entity.getIsIncome();
        if (isIncome != null) {
            stmt.bindString(4, isIncome);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(5, remark);
        }
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(6, date);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(7, time);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AccountInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String money = entity.getMoney();
        if (money != null) {
            stmt.bindString(2, money);
        }
 
        String category = entity.getCategory();
        if (category != null) {
            stmt.bindString(3, category);
        }
 
        String isIncome = entity.getIsIncome();
        if (isIncome != null) {
            stmt.bindString(4, isIncome);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(5, remark);
        }
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(6, date);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(7, time);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public AccountInfo readEntity(Cursor cursor, int offset) {
        AccountInfo entity = new AccountInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // money
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // category
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // isIncome
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // remark
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // date
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // time
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AccountInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMoney(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCategory(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setIsIncome(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setRemark(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setDate(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setTime(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(AccountInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(AccountInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(AccountInfo entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
