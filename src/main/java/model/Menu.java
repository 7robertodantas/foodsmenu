package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sales.SaleStrategy;

import java.util.List;

@AllArgsConstructor
public class Menu {

    @Getter
    private final List<SaleStrategy> sales;

    @Getter
    private final List<MenuItem> menu;

}
