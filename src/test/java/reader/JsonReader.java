package reader;

import model.StockTimestamp;
import org.apache.commons.lang3.ObjectUtils;
import org.json.JSONObject;
import utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JsonReader {

    public static List<StockTimestamp> readFromFile(String filePath) throws Exception {
        String fullJson = Utils.readFileContent(filePath);
        List<StockTimestamp> stockTimestampList = new ArrayList<>();

        JSONObject object = new JSONObject(fullJson);
        String[] keys = JSONObject.getNames(object);

        if (Objects.isNull(keys))
            return Collections.emptyList();
        for (String key : keys) {
            JSONObject keyObject = (JSONObject) object.get(key);
            Map<String, Object> curMap = keyObject.toMap();

            curMap.forEach((key1, value) -> {
                float stockPrice = Float.parseFloat(ObjectUtils.toString(value, "0"));

                Long unixObservationDateKey = Utils.formatDateToUnix(key1);

                Long unixTimestampKey = Utils.formatDateToUnix(key);
                StockTimestamp stockTimestamp = new StockTimestamp(unixObservationDateKey, unixTimestampKey, stockPrice);
                stockTimestampList.add(stockTimestamp);
            });
        }

        return stockTimestampList;
    }
}
