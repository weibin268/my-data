package com.zhuang.data.jdbc.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import com.zhuang.data.util.DataTypeUtils;
import com.zhuang.data.util.EntityUtils;

public class PreparedStatementUtils {

    public static void setParameter(PreparedStatement preparedStatement, Object parameter, Map<String, Integer> parametersIndex) throws SQLException {
        if (DataTypeUtils.isPrimitiveType(parameter)) {
            preparedStatement.setObject(1, parameter);
        } else {
            Map<String, Object> mapParameter = EntityUtils.entityToMap(parameter);
            for (String key : parametersIndex.keySet()) {
                if (mapParameter.containsKey(key)) {
                    Integer parameterIndex = parametersIndex.get(key);
                    Object parameterValue = mapParameter.get(key);
                    preparedStatement.setObject(parameterIndex, parameterValue);
                }
            }
        }
    }
}
