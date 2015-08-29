package Utilities;

import org.json.JSONArray;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataCache {


    public static Map<String, JSONArray> map = new ConcurrentHashMap<String, JSONArray>();

    // HashMap<String, ArrayList<TreeMenuItem>>();

    public static JSONArray get(String key) {

        // if(map == null){
        // map = new HashMap<String, ArrayList<TreeMenuItem>>();
        // }

        return map.get(key);
    }

    public static void set(String key, JSONArray adapter) {

        // if(map == null){
        // map = new HashMap<String, ArrayList<TreeMenuItem>>();
        // }
        if (!map.containsKey(key)) {
            map.put(key, adapter);
        }
    }
}
