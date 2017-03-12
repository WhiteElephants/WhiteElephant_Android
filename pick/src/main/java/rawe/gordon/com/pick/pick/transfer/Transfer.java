package rawe.gordon.com.pick.pick.transfer;

import java.util.HashMap;
import java.util.Map;

public class Transfer {

    private Map<String, Object> objectMap = new HashMap<>();

    private Transfer() {
    }

    public static class Holder {
        public static Transfer instance = new Transfer();
    }

    public static Transfer getInstance() {
        return Holder.instance;
    }

    public void putObject(String token, Object value) {
        objectMap.put(token, value);
    }

    public Object getObject(String token) {
        return objectMap.get(token);
    }

    public Object cleanObject(String token) {
        return objectMap.remove(token);
    }

    public Object getObjectAndClean(String token) {
        Object object = getObject(token);
        cleanObject(token);
        return object;
    }
}
