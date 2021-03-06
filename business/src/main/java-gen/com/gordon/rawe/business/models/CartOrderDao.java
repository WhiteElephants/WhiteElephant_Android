package com.gordon.rawe.business.models;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.gordon.rawe.business.models.CartOrder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CART_ORDER".
*/
public class CartOrderDao extends AbstractDao<CartOrder, Long> {

    public static final String TABLENAME = "CART_ORDER";

    /**
     * Properties of entity CartOrder.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property CommodityId = new Property(1, String.class, "commodityId", false, "COMMODITY_ID");
        public final static Property Amount = new Property(2, Integer.class, "amount", false, "AMOUNT");
        public final static Property Color = new Property(3, String.class, "color", false, "COLOR");
        public final static Property Size = new Property(4, String.class, "size", false, "SIZE");
        public final static Property Price = new Property(5, Float.class, "price", false, "PRICE");
        public final static Property Thumbnail = new Property(6, String.class, "thumbnail", false, "THUMBNAIL");
        public final static Property Name = new Property(7, String.class, "name", false, "NAME");
    };


    public CartOrderDao(DaoConfig config) {
        super(config);
    }
    
    public CartOrderDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CART_ORDER\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"COMMODITY_ID\" TEXT," + // 1: commodityId
                "\"AMOUNT\" INTEGER," + // 2: amount
                "\"COLOR\" TEXT," + // 3: color
                "\"SIZE\" TEXT," + // 4: size
                "\"PRICE\" REAL," + // 5: price
                "\"THUMBNAIL\" TEXT," + // 6: thumbnail
                "\"NAME\" TEXT);"); // 7: name
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CART_ORDER\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, CartOrder entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String commodityId = entity.getCommodityId();
        if (commodityId != null) {
            stmt.bindString(2, commodityId);
        }
 
        Integer amount = entity.getAmount();
        if (amount != null) {
            stmt.bindLong(3, amount);
        }
 
        String color = entity.getColor();
        if (color != null) {
            stmt.bindString(4, color);
        }
 
        String size = entity.getSize();
        if (size != null) {
            stmt.bindString(5, size);
        }
 
        Float price = entity.getPrice();
        if (price != null) {
            stmt.bindDouble(6, price);
        }
 
        String thumbnail = entity.getThumbnail();
        if (thumbnail != null) {
            stmt.bindString(7, thumbnail);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(8, name);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public CartOrder readEntity(Cursor cursor, int offset) {
        CartOrder entity = new CartOrder( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // commodityId
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // amount
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // color
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // size
            cursor.isNull(offset + 5) ? null : cursor.getFloat(offset + 5), // price
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // thumbnail
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7) // name
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, CartOrder entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCommodityId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setAmount(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setColor(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setSize(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPrice(cursor.isNull(offset + 5) ? null : cursor.getFloat(offset + 5));
        entity.setThumbnail(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setName(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(CartOrder entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(CartOrder entity) {
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
