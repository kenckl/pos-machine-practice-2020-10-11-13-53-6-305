package pos.machine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PosMachine {

    public String printReceipt(List<String> barcodes) {
        List<Item> receiptItemList = convertToItems(barcodes);

        return renderReceipt(receiptItemList, calculateTotal(calculateSubTotal(receiptItemList, barcodes)));
    }

    private List<Item> convertToItems(List<String> barcodes){
        List<ItemInfo> listItemInfos = loadAllItemsInformation();
        List<ItemInfo> listOfItem = new ArrayList<>();
        List<Item> itemsWithDetail = new ArrayList<>();
        List<String> barcodeListDistinct = barcodes.stream().distinct().collect(Collectors.toList());


        for(int i = 0; i < barcodeListDistinct.size(); i++){
            String currentBarCode = barcodeListDistinct.get(i).toString();
            listOfItem = listItemInfos.stream().filter(listItemInfo -> listItemInfo.getBarcode().
                    equals(currentBarCode)).collect(Collectors.toList());

            for (ItemInfo itemInfo : listOfItem){
                Item newItem = new Item();
                newItem.setBarcode(itemInfo.getBarcode());
                newItem.setName(itemInfo.getName());
                newItem.setUnitPrice(itemInfo.getPrice());
                itemsWithDetail.add(newItem);
            }
        }
        return itemsWithDetail;
    }

    private List<ItemInfo> loadAllItemsInformation(){
        ItemDataLoader itemDataLoader = new ItemDataLoader();
        List<ItemInfo> loadItemsLists = itemDataLoader.loadAllItemInfos();
        return loadItemsLists;
    }

    private int calculateTotal(List<Item> itemLists) {
        int calculateTotalPrice = 0;

        for(int i = 0 ; i < itemLists.size(); i++){
            calculateTotalPrice += itemLists.get(i).getSubTotal();
        }
        return calculateTotalPrice;
    }

    private List<Item> calculateSubTotal(List<Item> itemLists, List<String> barcodes){
        List<ItemInfo> listItemInfos = loadAllItemsInformation();
        int[] quantity = {Collections.frequency(barcodes,listItemInfos.get(0).getBarcode()),
                Collections.frequency(barcodes,listItemInfos.get(1).getBarcode()),
                Collections.frequency(barcodes,listItemInfos.get(2).getBarcode())};

        for(int i = 0 ; i < itemLists.size(); i++){
            int currentQuantity = quantity[i];
            itemLists.get(i).setQuantity(currentQuantity);
            itemLists.get(i).setSubTotal(itemLists.get(i).getUnitPrice()*currentQuantity);
        }
        return itemLists;
    }

    private String renderReceipt(List<Item> itemLists, int totalPrice){
        StringBuilder generateReceipt = new StringBuilder();
        generateReceipt.append("***<store earning no money>Receipt***\n");
        for(int i = 0 ; i < itemLists.size(); i++) {
            generateReceipt.append(("Name: " + itemLists.get(i).getName() +", Quantity: "+
                    itemLists.get(i).getQuantity() +", Unit price: "+
                    itemLists.get(i).getUnitPrice() +" (yuan), Subtotal: "+
                    itemLists.get(i).getSubTotal()+" (yuan)"+"\n"));
        }
        generateReceipt.append("----------------------\n");
        generateReceipt.append("Total: "+totalPrice+ " (yuan)"+ "\n");
        generateReceipt.append("**********************");
        return generateReceipt.toString().trim();
    }
}
