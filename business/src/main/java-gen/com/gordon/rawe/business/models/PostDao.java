package com.gordon.rawe.business.models;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.gordon.rawe.business.models.Post;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "POST".
*/
public class PostDao extends AbstractDao<Post, Long> {

    public static final String TABLENAME = "POST";

    /**
     * Properties of entity Post.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Uuid = new Property(1, String.class, "uuid", false, "UUID");
        public final static Property Data = new Property(2, String.class, "data", false, "DATA");
        public final static Property CreateTime = new Property(3, String.class, "createTime", false, "CREATE_TIME");
        public final static Property PostName = new Property(4, String.class, "postName", false, "POST_NAME");
        public final static Property ThumbPath = new Property(5, String.class, "thumbPath", false, "THUMB_PATH");
    };


    public PostDao(DaoConfig config) {
        super(config);
    }
    
    public PostDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"POST\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"UUID\" TEXT," + // 1: uuid
                "\"DATA\" TEXT," + // 2: data
                "\"CREATE_TIME\" TEXT," + // 3: createTime
                "\"POST_NAME\" TEXT," + // 4: postName
                "\"THUMB_PATH\" TEXT);"); // 5: thumbPath
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"POST\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Post entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String uuid = entity.getUuid();
        if (uuid != null) {
            stmt.bindString(2, uuid);
        }
 
        String data = entity.getData();
        if (data != null) {
            stmt.bindString(3, data);
        }
 
        String createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindString(4, createTime);
        }
 
        String postName = entity.getPostName();
        if (postName != null) {
            stmt.bindString(5, postName);
        }
 
        String thumbPath = entity.getThumbPath();
        if (thumbPath != null) {
            stmt.bindString(6, thumbPath);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Post readEntity(Cursor cursor, int offset) {
        Post entity = new Post( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // uuid
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // data
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // createTime
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // postName
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // thumbPath
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Post entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUuid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setData(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCreateTime(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPostName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setThumbPath(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Post entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Post entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
