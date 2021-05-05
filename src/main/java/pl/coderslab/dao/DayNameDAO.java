package pl.coderslab.dao;

import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.DayName;
import pl.coderslab.utils.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DayNameDAO {
    private static String FIND_DAY_BY_ID = "SELECT * from day_name;";

    /**
     * Return all days
     *
     * @return
     */

    public List<DayName> findAll() {
        List<DayName> days = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             Statement statement = connection.createStatement();
        ) {
            ResultSet resultSet = statement.executeQuery(FIND_DAY_BY_ID);
            while (resultSet.next()) {
                DayName day = new DayName();
                day.setId(resultSet.getInt("id"));
                day.setName(resultSet.getString("name"));
                day.setDisplayOrder(resultSet.getInt("display_order"));
                days.add(day);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(days.size() == 0){
            throw new NotFoundException("Tabela day_name pusta!!!");
        } else {
            return days;
        }
    }
}
