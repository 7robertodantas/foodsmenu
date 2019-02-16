package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class Receipt {

    @Getter
    private final List<ReceiptItem> items;

    @Getter
    private final double costPrice;

    @Getter
    private final double totalPrice;

}
