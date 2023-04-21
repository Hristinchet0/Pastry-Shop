package com.example.pastry.shop.service;

import com.example.pastry.shop.model.dto.OrderStatusSendAdmin;
import com.example.pastry.shop.model.dto.OrdersStatusDTO;
import com.example.pastry.shop.model.entity.Orders;
import com.example.pastry.shop.model.entity.OrdersProcessing;
import com.example.pastry.shop.model.entity.Products;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.model.enums.AuthorityEnum;
import com.example.pastry.shop.repository.OrdersProcessingRepository;
import com.example.pastry.shop.repository.OrdersRepository;
import com.example.pastry.shop.repository.ProductRepository;
import com.example.pastry.shop.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrderService {

    private final OrdersRepository ordersRepository;

    private final UsersRepository usersRepository;

    private final ProductRepository productRepository;

    private final OrdersProcessingRepository ordersProcessingRepository;

    private Long keyProductOrder = keyOrderProduct();

    private final static AtomicLong subIdCounter = new AtomicLong(System.nanoTime());

    public OrderService(OrdersRepository ordersRepository, UsersRepository usersRepository, ProductRepository productRepository, OrdersProcessingRepository ordersProcessingRepository) {
        this.ordersRepository = ordersRepository;
        this.usersRepository = usersRepository;
        this.productRepository = productRepository;
        this.ordersProcessingRepository = ordersProcessingRepository;
    }



    public Orders createOrder(Long id, Users user) {
        Orders newOrder = new Orders();
        newOrder.setDateCreated(LocalDate.now());
        Optional<Users> byUsername = this.usersRepository.findByUsername(user.getUsername());
        newOrder.setUsers(byUsername.get());
        Optional<Products> product = productRepository.findById(id);
        newOrder.setStatus("newOrder");
        newOrder.setPrice(product.get().getPrice());
        newOrder.setProductName(product.get().getName());
        ordersRepository.save(newOrder);
        return newOrder;
    }

    public Set<Orders> findByUser(Users user) {
        boolean isUser = isUser(user);
        if (isUser) {
            return ordersRepository.findByUsers(user);
        } else {
            return null;
        }
    }

    private static boolean isUser(Users user) {
        return user.getAuthorities()
                .stream().anyMatch(auth -> AuthorityEnum.user.name().equals(auth.getAuthority()));
    }

    public void removeProduct(Long id) {
        Optional<Orders> byId = this.ordersRepository.findById(id);
        this.ordersRepository.delete(byId.get());
    }


    public Orders updateStatus(OrdersStatusDTO ordersStatusDTO, Users user) {
        Set<Orders> byUsers = this.ordersRepository.findByUsers(user);
        Set<Orders> lastKey = this.ordersRepository.findAllOrders();
        Long mostBigKey = 0L;
        for (Orders order : lastKey) {
            if (order.getKeyOrderProduct() == null) {
                continue;
            }
            if (order.getKeyOrderProduct() > mostBigKey) {
                mostBigKey = order.getKeyOrderProduct();
            }
        }
        for (Orders currentOrder : byUsers) {
            currentOrder.setStatus(ordersStatusDTO.getStatus());
            currentOrder.setKeyOrderProduct(mostBigKey + 1);
            this.ordersRepository.save(currentOrder);
        }
        return (Orders) byUsers;
    }

    public Set<Orders> findByStatus(Users user) {
        boolean isAdmin = isAdmin(user);
        if (isAdmin) {
            return this.ordersRepository.findByStatus();
        } else {
            return null;
        }
    }

    private static boolean isAdmin(Users user) {
        return user.getAuthorities()
                .stream().anyMatch(auth -> AuthorityEnum.admin.name().equals(auth.getAuthority()));
    }

    public Set<Orders> findByUsersId(Long id, Users user) {
        OrdersProcessing ordersProcessing = new OrdersProcessing();
        Set<Orders> byOrderKey = this.ordersRepository.findByKeyOrderProduct(id);
        double totalPrice = 0;
        for (Orders currentOrder : byOrderKey) {
            totalPrice += currentOrder.getPrice();
        }
        ordersProcessing.setTotalPrice(totalPrice);
        Optional<Users> currentUser = this.usersRepository.findUserBayKey(id);
        ordersProcessing.setUser(currentUser.get());
        ordersProcessing.setStatusOrder("sent");
        ordersProcessing.setDateOfDispatch(LocalDate.now());
        Set<Orders> keyOrdersAll = this.ordersRepository.findByUsers_Id(id);
        ordersProcessing.setKeyOrder(id);
        this.ordersProcessingRepository.save(ordersProcessing);
        return byOrderKey;
    }


    private Long keyOrderProduct() {
        this.keyProductOrder = subIdCounter.incrementAndGet();
        return this.keyProductOrder;
    }

    public Orders updateStatusSend(OrderStatusSendAdmin orderStatusSendAdmin, Long id) {
        Set<Orders> orders = this.ordersRepository.findByKeyOrderProduct(id);
        for (Orders currentOrder : orders) {
            currentOrder.setStatus(orderStatusSendAdmin.getStatus());
            this.ordersRepository.save(currentOrder);
        }
        return (Orders) orders;
    }
}
