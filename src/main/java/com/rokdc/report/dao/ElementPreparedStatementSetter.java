package com.rokdc.report.dao;

import com.rokdc.report.log.LogWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ElementPreparedStatementSetter implements PreparedStatementSetter {
    private final List<String> parameters;
    private final LogWriter log;

    public ElementPreparedStatementSetter(List<String> parameters, @Autowired LogWriter log) {
        this.parameters = parameters == null ? new ArrayList<String>() : parameters;  //Так как на главной странице еще нет инициированных параметров, а селект используем с NULL
        this.log = log;
    }

    @Override
    public void setValues(PreparedStatement ps) throws SQLException {
        int parametersCount = ps.getParameterMetaData().getParameterCount();
        if (parametersCount == 1 && parameters.size() == 0) {
            parameters.add(null);
        }

        if (parametersCount > 0 && parameters != null) {
            if (!isNumberOfParametersEqual(parametersCount)) {
                log.writeError("Несоответствие количества параметров в запросе фильтрам со страницы. Параметров в запросе: "
                        + parametersCount + "; пришло с фронта: " + parameters.size());
                return;
            }

            for (int i = 0; i < parametersCount; i++) {
                ps.setString(i + 1, parameters.get(i));
            }
        } else return;

    }


    private boolean isNumberOfParametersEqual(int prepStatParamCnt) {
        if (parameters == null) return false;
        return prepStatParamCnt == parameters.size();
    }


}
