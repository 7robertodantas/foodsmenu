package sales;

import model.Discount;
import model.OrderItem;

import java.util.Optional;

public interface SaleStrategy {

    Optional<Discount> apply(OrderItem order, double netOrderPrice);

}
