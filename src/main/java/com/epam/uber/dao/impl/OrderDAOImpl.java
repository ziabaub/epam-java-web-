package com.epam.uber.dao.impl;

import com.epam.uber.dao.AbstractDAO;
import com.epam.uber.entity.order.Order;
import com.epam.uber.entity.order.OrderInfo;
import com.epam.uber.exceptions.DAOException;
import com.epam.uber.utils.ZoneMapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class OrderDAOImpl extends AbstractDAO<Order> {


    public void updateOrder(Order order) throws DAOException {
        String sqlQuery = "UPDATE journey SET taxi_id=? ,status=? WHERE id=?";
        String taxiId = String.valueOf(order.getTaxiId());
        String orderId = String.valueOf(order.getId());
        List<String> params = Arrays.asList(taxiId, order.getStatus(), orderId);
        executeQuery(sqlQuery, params);
    }

    public Order getOrderById(int id) throws DAOException {
        String sqlQuery = "SELECT * FROM journey WHERE id=?";
        List<String> params = Collections.singletonList(String.valueOf(id));
        return getEntity(sqlQuery, params);
    }

    public void deleteOrderById(int id) throws DAOException {
        String sqlQuery = "delete from journey where id = ? ";
        List<String> params = Collections.singletonList(String.valueOf(id));
        executeQuery(sqlQuery, params);
    }

    public int insertOrder(Order order) throws DAOException {
        String fields = "insert into journey (date, customer_id, tariff_id,from_location_id, to_location_id, cost, status) values(?,?,?,?,?,?,?)";
        return insert(order, fields);
    }


    public List<OrderInfo> selectFinishedOrders(int id) throws DAOException {
        String condition = "journey.taxi_id = " + id + " and journey.status = 'done'";
        return getOrderInfos(condition);
    }

    public List<OrderInfo> selectWaitingOrders() throws DAOException {
        String condition = "journey.status ='waiting'";
        return getOrderInfos(condition);
    }

    private List<OrderInfo> getOrderInfos(String condition) throws DAOException {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("select journey.id as id,date ,l.zone as current_zone , l2.zone as destination_zone , journey.cost from journey ")
                .append("inner join ")
                .append("location l on journey.from_location_id = l.id ")
                .append("inner join location l2 on to_location_id =l2.id where ")
                .append(condition)
                .append(" order by date");
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            List<OrderInfo> orders = new ArrayList<>();
            ResultSet result = statement.executeQuery(sqlQuery.toString());
            while (result.next()) {
                int id = result.getInt(ID_COLUMN_LABEL);
                Date d = result.getDate("date");
                LocalDateTime date = convertToLocalDateTime(d);
                int currZone = result.getInt("current_zone");
                int destinationZon = result.getInt("destination_zone");
                int cost = result.getInt("cost");
                String currArea = ZoneMapper.getArea(currZone);
                String destArea = ZoneMapper.getArea(destinationZon);
                orders.add(new OrderInfo(id, date, currArea, destArea, cost));
            }
            return orders;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    protected List<String> getEntityParameters(Order entity) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String date = dateFormat.format(new Date());
        String costumerId = String.valueOf(entity.getCostumerId());
        String tariffId = String.valueOf(entity.getTariffId());
        String from = String.valueOf(entity.getCurrLocationId());
        String to = String.valueOf(entity.getDestinationLocationId());
        String cost = String.valueOf(entity.getCost());
        String status = entity.getStatus();

        return Arrays.asList(date, costumerId, tariffId, from, to, cost, status);
    }

    @Override
    protected Order buildEntity(ResultSet result) throws DAOException {
        try {
            Order order = new Order();
            int id = result.getInt(ID_COLUMN_LABEL);
            Date date = result.getDate("date");
            int taxiId = result.getInt("taxi_id");
            int customerId = result.getInt("customer_id");
            int tariffId = result.getInt("tariff_id");
            int from = result.getInt("from_location_id");
            int to = result.getInt("to_location_id");
            int cost = result.getInt("cost");
            String status = result.getString("status");
            int rate = result.getInt("rate");

            order.setId(id);
            order.setDate(date);
            order.setTaxiId(taxiId);
            order.setCostumerId(customerId);
            order.setTariffId(tariffId);
            order.setCurrLocationId(from);
            order.setDestinationLocationId(to);
            order.setCost(cost);
            order.setStatus(status);
            order.setRate(rate);
            return order;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    private LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }


}
