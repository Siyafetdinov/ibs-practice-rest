package ru.ibs.dataBaseControl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ru.ibs.product.Product;
import ru.ibs.utils.DBUtils;

import java.util.List;

public class DataBaseControl {
    private static DataBaseControl dataBaseControl;
    private static JdbcTemplate jdbcTemplate;

    public static DataBaseControl getInstance() {
        if (dataBaseControl == null) {
            dataBaseControl = new DataBaseControl(new JdbcTemplate(DBUtils.getDataSourceHikari()));
        }
        return dataBaseControl;
    }
    private DataBaseControl(JdbcTemplate jdbcTemplate) {
        DataBaseControl.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> selectAllProductFrom(String nameTable) {
        String query = "SELECT FOOD_NAME AS name, FOOD_TYPE AS type, FOOD_EXOTIC AS exotic FROM " + nameTable;
        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Product.class));
    }

    public Product getProductFromTable(Product product, String nameTable) {
        String sql = "SELECT * FROM " + nameTable + " WHERE FOOD_NAME = ? AND FOOD_TYPE = ? AND FOOD_EXOTIC = ?";
        Object[] params = {product.getName(), product.getType(), product.isExotic()};
        RowMapper<Product> rowMapper = (resultSet, rowNum) -> Product.builder()
                .name(resultSet.getString("FOOD_NAME"))
                .type(resultSet.getString("FOOD_TYPE"))
                .exotic(resultSet.getBoolean("FOOD_EXOTIC"))
                .build();
        return jdbcTemplate.query(sql, params, rowMapper).stream().findFirst().orElse(null);
    }

    public void addProductFromTable(Product product, String nameTable) {
        String sql = "INSERT INTO " + nameTable + " VALUES ((SELECT MAX(FOOD_ID) + 1 FROM FOOD), ?, ?, ?)";
        Object[] params = {product.getName(), product.getType(), product.isExotic()};
        jdbcTemplate.update(sql, params);
    }

    public void deleteProductFromTable(Product product, String nameTable) {
        String sql = "DELETE FROM " + nameTable + " WHERE FOOD_NAME = ? AND FOOD_TYPE = ? AND FOOD_EXOTIC = ?";
        Object[] params = {product.getName(), product.getType(), product.isExotic()};
        jdbcTemplate.update(sql, params);
    }
}