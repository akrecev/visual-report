package com.rokdc.report.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class ElementResultSetExtractor implements ResultSetExtractor<LinkedHashMap<String, LinkedList<String>>> {

    @Override
    @Nullable
    public LinkedHashMap<String, LinkedList<String>> extractData(ResultSet rs) throws SQLException, DataAccessException {
        LinkedHashMap<String, LinkedList<String>> map = new LinkedHashMap<>();

        ResultSetMetaData rsMetaData = rs.getMetaData();

        int colCnt = rsMetaData.getColumnCount();

        while (rs.next()) {

            for (int i = 1; i <= colCnt; ++i) {
                String colName = rsMetaData.getColumnName(i);
                map.putIfAbsent(colName, new LinkedList<String>());
                map.get(colName).add(rs.getString(i));
            }
        }
        return map;
    }


}


