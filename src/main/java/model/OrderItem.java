package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@ToString
public class OrderItem {

    @Getter
    private String itemName;

    @Getter
    private List<String> ingredients;

}
