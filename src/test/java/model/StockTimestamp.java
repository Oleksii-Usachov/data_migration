package model;

import java.util.Objects;

/*Had some issues with lombok library, all of this could be replaced with @Data annotation*/
public class StockTimestamp {

    private Long timestamp;
    private Long observationDate;
    private float stockPrice;

    public StockTimestamp(Long timestamp, Long observationDate, float stockPrice) {
        this.timestamp = timestamp;
        this.observationDate = observationDate;
        this.stockPrice = stockPrice;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Long getObservationDate() {
        return observationDate;
    }

    public float getStockPrice() {
        return stockPrice;
    }

    @Override
    public String toString() {
        return "StockTimestamp{" +
                "timestamp=" + timestamp +
                ", observationDate=" + observationDate +
                ", stockPrice=" + stockPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StockTimestamp that = (StockTimestamp) o;
        return Float.compare(that.stockPrice, stockPrice) == 0 && timestamp.equals(that.timestamp) && observationDate.equals(that.observationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, observationDate, stockPrice);
    }
}
