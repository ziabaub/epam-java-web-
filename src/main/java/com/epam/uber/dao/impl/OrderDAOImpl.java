package com.epam.uber.dao.impl;

import com.epam.uber.dao.AbstractDAO;
import com.epam.uber.entity.client.OrderInfo;
import com.epam.uber.entity.order.Order;
import com.epam.uber.exceptions.DAOException;

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


    private Connection connection;

    public OrderDAOImpl(Connection connection) {
        super(connection, "journey");
        this.connection = connection;
    }

    public List<Order> selectAllOrder() throws DAOException {
        String sqlQuery = "SELECT * FROM journey";
        return getEntities(sqlQuery);
    }

    public boolean updateOrder(Order order) throws DAOException {
        String sqlQuery = "UPDATE journey SET taxi_id=? ,status=? WHERE id=?";
        String taxiId = String.valueOf(order.getTaxiId());
        String orderId = String.valueOf(order.getId());
        List<String> params = Arrays.asList(taxiId, order.getStatus(), orderId);
        return executeQuery(sqlQuery, params);
    }

    public Order getOrderById(int id) throws DAOException {
        String sqlQuery = "SELECT * FROM journey WHERE id=?";
        List<String> params = Collections.singletonList(String.valueOf(id));
        return getEntity(sqlQuery, params);
    }

    public int insertOrder(Order order) throws DAOException {
        String fields = "(date, costumer_id, tariff_id,from_location_id, to_location_id, cost, status)";
        return insert(order, fields);
    }

    public List<OrderInfo> selectOrdersByDriverId(int id) throws DAOException {
        String condition = "journey.taxi_id =" + id;
        return getOrderInfos(condition);
    }

    public List<OrderInfo> selectWaitingOrders() throws DAOException {
        String condition = "journey.status ='waiting'";
        return getOrderInfos(condition);
    }

    public boolean deleteByTaxiId(int id) throws DAOException {
        String sqlQuery = "DELETE FROM journey WHERE taxi_id= ?";
        List<String> params = Collections.singletonList(String.valueOf(id));
        return executeQuery(sqlQuery, params);
    }

    private List<OrderInfo> getOrderInfos(String condition) throws DAOException {
        String sqlQuery =
                "select journey.id as id,date ,l.zone as current_zone , l2.zone as destination_zone , journey.cost from journey " +
                        "inner join " +
                        "location l on journey.from_location_id = l.id " +
                        "inner join location l2 on to_location_id =l2.id where " + condition + " order by date";
        try {
            Statement statement = connection.createStatement();
            List<OrderInfo> orders = new ArrayList<>();
            ResultSet result = statement.executeQuery(sqlQuery);
            while (result.next()) {
                int id = result.getInt(ID_COLUMN_LABEL);
                Date d = result.getDate("date");
                LocalDateTime date = convertToLocalDateTime(d);
                int currZone = result.getInt("current_zone");
                int destinationZon = result.getInt("destination_zone");
                int cost = result.getInt("cost");
                orders.add(new OrderInfo(id, date, currZone, destinationZon, cost));
            }
            return orders;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<String> getEntityParameters(Order entity) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String date = dateFormat.format(new Date());
        String costumerId = String.valueOf(entity.getCostumerId());
        String tariffId = String.valueOf(entity.getTariffId());
        String from = String.valueOf(entity.getCurrLocationId());
        String to = String.valueOf(entity.getDestinationLocationId());
        String cost = String.valueOf(entity.getCost());
        String status = entity.getStatus();

        return new ArrayList<>(Arrays.asList(date, costumerId, tariffId, from, to, cost, status));
    }

    @Override
    public Order buildEntity(ResultSet result) throws DAOException {
        try {
            int id = result.getInt(ID_COLUMN_LABEL);
            Date date = result.getDate("date");
            int taxiId = result.getInt("taxi_id");
            int costumerId = result.getInt("costumer_id");
            int tariffId = result.getInt("tariff_id");
            int from = result.getInt("from_location_id");
            int to = result.getInt("to_location_id");
            int cost = result.getInt("cost");
            String status = result.getString("status");
            int rate = result.getInt("rate");

            return new Order(id, date, taxiId, costumerId, tariffId, from, to, cost, status, rate);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    public LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }


}
