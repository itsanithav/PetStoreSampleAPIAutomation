package Utility;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestPayload {
    public Map<String,Object> placeOrderPayload(int id, int petId,int quantity,String date) {
        LinkedHashMap<String,Object> map = new LinkedHashMap<>();
        map.put("id",id);
        map.put("petId",petId);
        map.put("quantity",quantity);
        map.put("shipDate",date);
        map.put("status","placed");
        map.put("complete",true);
        return map;
    }
}
