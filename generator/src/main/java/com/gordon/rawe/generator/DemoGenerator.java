package com.gordon.rawe.generator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by gordon on 16/4/23.
 */
public class DemoGenerator {
    public static void main(String[] args) {
        Schema schema = new Schema(1, "com.gordon.rawe.business.models");
        Entity address = schema.addEntity("DeliveryInformation");
        address.addIdProperty();
        address.addStringProperty("province");
        address.addStringProperty("city");
        address.addStringProperty("street");
        address.addStringProperty("detail");
        address.addStringProperty("zip");
        address.addStringProperty("phone");
        Entity order = schema.addEntity("CartOrder");
        order.addIdProperty();
        order.addStringProperty("commodityId");
        order.addIntProperty("amount");
        order.addStringProperty("color");
        order.addStringProperty("size");
        order.addFloatProperty("price");
        order.addStringProperty("thumbnail");
        order.addStringProperty("name");
        Entity user = schema.addEntity("User");
        user.addIdProperty();
        user.addStringProperty("uuid");
        user.addStringProperty("username");
        user.addStringProperty("thumbnail");
        user.addStringProperty("phone");
        user.addStringProperty("address");
        user.addStringProperty("email");
        user.addStringProperty("gender");
        user.addStringProperty("creditCardNumber");
        user.addStringProperty("deliveryAddresses");//本地段存储分隔符分割的地址
        Entity post = schema.addEntity("Post");
        post.addIdProperty();
        post.addStringProperty("uuid");
        post.addStringProperty("data");
        post.addStringProperty("createTime");
        post.addStringProperty("postName");
        post.addStringProperty("thumbPath");
        post.addStringProperty("tag");
        post.addStringProperty("category");
        try {
            new DaoGenerator().generateAll(schema, "../business/src/main/java-gen");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
