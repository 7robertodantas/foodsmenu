package com.food.core.facade;

import java.util.List;
import java.util.Set;

public interface Item {

    String getName();

    List<String> getElements();

    Set<String> getEligibleSalesCodes();

}
