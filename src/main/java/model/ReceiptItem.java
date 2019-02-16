package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class ReceiptItem {

    @Getter
    private final OrderItem item;

    @Getter
    private final List<Discount> discounts;

    @Getter
    private final double costPrice;

    @Getter
    private final double totalPrice;

}
