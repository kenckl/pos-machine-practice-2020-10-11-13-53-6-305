package pos.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PosMachine {

    /*
    P: 15mins
    D: 30mins
    C: check if can get the item details correctly
    A: --
    */
    public String printReceipt(List<String> barcodes) {

        List<String> receiptItems = new ArrayList<>();;
        List<ItemInfo> itemInfoList = ItemDataLoader.loadAllItemInfos();
        List<String> filterBarcodes = barcodes.stream().distinct().collect(Collectors.toList());
        int total = 0;
        
        // todo variable x rename
        // total and receipt items seperate
        for(int x = 0; x < filterBarcodes.size(); x++){
            ItemDetails itemDetails = getBarcodeData(filterBarcodes.get(x), itemInfoList);
            int itemQuantity = countItems(itemDetails, barcodes);

            itemDetails.setQuantity(itemQuantity);
            itemDetails.setSubtotal(calculateItemsSubtotal(itemDetails, itemQuantity));

            receiptItems.add(formatReceiptLine(itemDetails));
            total += itemDetails.getSubtotal();
        }
        return formatReceipt(receiptItems, total);
    }

    /*
    P: 5mins
    D: 8mins
    C: Check the final string printed is in correct format or not
    A: Fix lining issue
    */
    private String formatReceipt(List<String> receiptItems, int total) {
        String outputStr = "***<store earning no money>Receipt***\n";
        for(String item: receiptItems){
            outputStr += item;
        }
        outputStr += "----------------------\n" + "Total: "+total+" (yuan)\n" + "**********************";
        return outputStr;
    }

    /*
    P: 2mins
    D: 3mins
    C: Check the string output in each line
    A: N/A
    */
    private String formatReceiptLine(ItemDetails barcodeDetail) {
        return "Name: "+barcodeDetail.getName()+", Quantity: "+barcodeDetail.countItems()+", Unit price: "+barcodeDetail.getPrice()+" (yuan), Subtotal: "+barcodeDetail.getSubtotal()+" (yuan)\n";
    }

    /*
    P: 5mins
    D: 5mins
    C: N/A
    A: N/A
    */
    private int calculateItemsSubtotal(ItemDetails barcodeDetails, int itemQuantity) {
        int subtotal = 0;
        subtotal = barcodeDetails.getPrice() * itemQuantity;
        return subtotal;
    }

    // variable x
    private int countItems(ItemDetails barcode, List<String> barcodes) {
        int quantity = 0;
        for(int x = 0; x < barcodes.size(); x++){
            if(barcodes.get(x).equals(barcode.getBarcode())){
                quantity++;
            }
        }
        return quantity;
    }

    /*
    P: 10mins
    D: 15mins
    C: always return null when running
    A: change to use .equals() then work
    */
    // variable x
    private ItemDetails getBarcodeData(String barcode, List<ItemInfo> barcodeInfos) {
        for(int x = 0; x < barcodeInfos.size(); x++){
            if(barcode.equals(barcodeInfos.get(x).getBarcode())){
                ItemDetails barcodeDetails = new ItemDetails();
                barcodeDetails.setBarcode(barcodeInfos.get(x).getBarcode());
                barcodeDetails.setName(barcodeInfos.get(x).getName());
                barcodeDetails.setPrice(barcodeInfos.get(x).getPrice());
                return barcodeDetails;
            }
        }
        return null;
    }
