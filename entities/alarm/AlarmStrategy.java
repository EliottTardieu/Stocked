package com.stocked.entities.alarm;

import com.stocked.entities.stock.StockStatus;

public interface AlarmStrategy {

    public StockStatus checkStatus();

    public void modify(int threshold, int... time);

}
