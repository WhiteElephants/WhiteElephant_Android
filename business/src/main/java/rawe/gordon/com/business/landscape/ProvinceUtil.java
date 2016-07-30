package rawe.gordon.com.business.landscape;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rawe.gordon.com.business.R;

/**
 * Created by gordon on 16/5/26.
 */
public class ProvinceUtil {
    public static Map<String, Map<String, List<String>>> getProvinces(Context context) {
        String landscape = context.getResources().getString(R.string.landscape).replace("*", "'");
        Map<String, JSONObject> provinces = JSON.parseObject(landscape, new HashMap<String, JSONObject>().getClass());
        Map<String, Map<String, List<String>>> provinceMap = new HashMap<>();
        for (String cityKey : provinces.keySet()) {
            JSONObject city = provinces.get(cityKey);
            Map<String, List<String>> cityMap = new HashMap<>();
            for (String countyKey : city.keySet()) {
                List<String> counties = new ArrayList<>();
                for (Object tmpValue : (JSONArray) city.get(countyKey)) {
                    counties.add(tmpValue.toString());
                }
                cityMap.put(countyKey, counties);
                Log.d("gordon", counties.toString());
            }
            provinceMap.put(cityKey, cityMap);
        }
        return provinceMap;
    }
}
