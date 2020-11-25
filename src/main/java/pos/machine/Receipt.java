package pos.machine;

import java.util.List;

public class Receipt {
    private List<Item> itemDetailList;
    private int totalPrice;

    public Receipt() {
    }

    public List<Item> getItemDetailList() {
        return itemDetailList;
    }

    public void setItemDetailList(List<Item> itemDetailList) {
        this.itemDetailList = itemDetailList;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
