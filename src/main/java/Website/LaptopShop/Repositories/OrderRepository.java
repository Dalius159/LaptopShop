package Website.LaptopShop.Repositories;

import Website.LaptopShop.Entities.Orders;
import Website.LaptopShop.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long>, QuerydslPredicateExecutor<Orders> {


    @Query(value = "select DATE_FORMAT(dh.receivedDate, '%m') as month, "
            + " DATE_FORMAT(dh.receivedDate, '%Y') as year, sum(ct.receivedQuantity * ct.cost) as total "
            + " from Orders dh, OrderDetails ct"
            + " where dh.id = ct.order.id and dh.orderStatus ='Completed'"
            + " group by DATE_FORMAT(dh.receivedDate, '%Y%m')"
            + " order by DATE_FORMAT(dh.receivedDate, '%Y') asc" )
    public List<Object> getOrderByMonthAndYear();
    public List<Orders> findByOrderer(Users orderer);

    @Query(value = "SELECT od.* FROM orders od WHERE od.id = (SELECT MAX(od2.id) FROM orders od2 WHERE od2.orderer_id = :ordererID)", nativeQuery = true)
    public Orders findLatestOrderByOrdererID(@Param("ordererID") Long ordererID);

    public List<Orders> findByOrderStatusAndDeliver(String status, Users deliver);

    public int countByOrderStatus(String orderStatus);
}
