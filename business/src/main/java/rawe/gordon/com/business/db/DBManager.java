package rawe.gordon.com.business.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.gordon.rawe.business.models.CartOrder;
import com.gordon.rawe.business.models.CartOrderDao;
import com.gordon.rawe.business.models.DaoMaster;
import com.gordon.rawe.business.models.DaoSession;
import com.gordon.rawe.business.models.DeliveryInformation;
import com.gordon.rawe.business.models.DeliveryInformationDao;
import com.gordon.rawe.business.models.User;
import com.gordon.rawe.business.models.UserDao;

import java.util.List;

import de.greenrobot.dao.query.Query;

/**
 * Created by gordon on 16/4/23.
 */
public class DBManager {
    public static final String SPLITTER = ";";
    public static final String DB_NAME = "fruit.market.db";
    private static DBManager ourInstance = new DBManager();
    private DaoMaster master;
    private DaoSession session;


    public static DBManager getInstance() {
        return ourInstance;
    }

    private DBManager() {
    }

    public void configure(Context context) {
        final DaoMaster.OpenHelper helper = new DaoMaster.OpenHelper(context, DB_NAME, null) {
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                DaoMaster.dropAllTables(db, false);
                DaoMaster.createAllTables(db, false);
            }
        };
        master = new DaoMaster(helper.getWritableDatabase());
        session = master.newSession();
    }

    public void createAddress(String province, String city, String street, String detail, String zip, String phone) {
        final DeliveryInformation address = new DeliveryInformation();
        address.setProvince(province);
        address.setCity(city);
        address.setStreet(street);
        address.setDetail(detail);
        address.setZip(zip);
        address.setPhone(phone);
        final DeliveryInformationDao dao = session.getDeliveryInformationDao();
        session.runInTx(new Runnable() {
            @Override
            public void run() {
                dao.insert(address);
            }
        });
    }

    public List<DeliveryInformation> getAllAddresses() {
        DeliveryInformationDao dao = session.getDeliveryInformationDao();
        Query builder = dao.queryBuilder().distinct().build();
        return builder.list();
    }

    public void deleteAddresses() {
        final DeliveryInformationDao dao = session.getDeliveryInformationDao();
        session.runInTx(new Runnable() {
            @Override
            public void run() {
                dao.deleteAll();
            }
        });
    }

    public void createOrder(String uuid, int amount, String color, String size, String thumbnail, float price, String name) {
        final CartOrder order = new CartOrder();
        order.setCommodityId(uuid);
        order.setAmount(amount);
        order.setColor(color);
        order.setSize(size);
        order.setPrice(price);
        order.setThumbnail(thumbnail);
        order.setName(name);
        final CartOrderDao dao = session.getCartOrderDao();
        session.runInTx(new Runnable() {
            @Override
            public void run() {
                dao.insert(order);
            }
        });
    }

    public List<CartOrder> getCartOrders() {
        CartOrderDao dao = session.getCartOrderDao();
        Query builder = dao.queryBuilder().distinct().build();
        return builder.list();
    }

    public void removeAllOrders() {
        final CartOrderDao dao = session.getCartOrderDao();
        session.runInTx(new Runnable() {
            @Override
            public void run() {
                dao.deleteAll();
            }
        });
    }

    public void createUser(String uuid, String username, String thumbnail, String phone,
                           String address, String gender, String creditCardNumber, String deliveryAddresses) {
        final User user = new User();
        user.setUuid(uuid);
        user.setUsername(username);
        user.setThumbnail(thumbnail);
        user.setPhone(phone);
        user.setAddress(address);
        user.setGender(gender);
        user.setDeliveryAddresses(deliveryAddresses);
        user.setCreditCardNumber(creditCardNumber);
        final UserDao userDao = session.getUserDao();
        session.runInTx(new Runnable() {
            @Override
            public void run() {
                userDao.insert(user);
            }
        });
    }

    public List<User> getUsers() {
        UserDao userDao = session.getUserDao();
        Query builder = userDao.queryBuilder().distinct().build();
        return builder.list();
    }

    public void removeAllUsers() {
        final UserDao userDao = session.getUserDao();
        session.runInTx(new Runnable() {
            @Override
            public void run() {
                userDao.deleteAll();
            }
        });
    }

//    public void createPerson(String name, String content) {
//        final Person person = new Person();
//        person.setComment(content);
//        person.setName(name);
//        final PersonDao dao = session.getPersonDao();
//        session.runInTx(new Runnable() {
//            @Override
//            public void run() {
//                dao.insert(person);
//            }
//        });
//    }
//
//    public List<Person> getAllPeople() {
//        PersonDao dao = session.getPersonDao();
//        Query builder = dao.queryBuilder().distinct().build();
//        List<Person> allPeople = builder.list();
//        return allPeople;
//    }
//
//    public void deleteAllPeople() {
//        final PersonDao dao = session.getPersonDao();
//        session.runInTx(new Runnable() {
//            @Override
//            public void run() {
//                dao.deleteAll();
//            }
//        });
//    }
//
//    public Person getPersonByName(String name) {
//        final PersonDao dao = session.getPersonDao();
//        Query query = dao.queryBuilder().where(PersonDao.Properties.Name.eq(name)).build();
//        List all = query.list();
//        if (all != null) return (Person) all.get(0);
//        else return null;
//    }
//
//    public void deletePersonByName(String name) {
//        final PersonDao dao = session.getPersonDao();
//        final Person person = getPersonByName(name);
//        session.runInTx(new Runnable() {
//            @Override
//            public void run() {
//                dao.delete(person);
//            }
//        });
//    }
}
