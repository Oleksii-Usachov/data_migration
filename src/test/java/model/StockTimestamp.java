package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockTimestamp {

    private Long timestamp;
    private Long observationDate;
    private float stockPrice;
}
