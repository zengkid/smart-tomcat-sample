package org.smarttomcat.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Map;
import java.util.Set;

@WebServlet("/db")

public class DbAccessServlet extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(DbAccessServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        logger.info("starting request....");


        try {

            final PrintWriter writer = res.getWriter();

            writer.write("<html>");
            writer.write("<head>");
            writer.write("<title>web sample</title>");
            writer.write("</head>");
            writer.write("<body>");

            Context ctx = new InitialContext();
            ctx = (Context) ctx.lookup("java:comp/env");
            DataSource datasource = (DataSource) ctx.lookup("jdbc/ds");
            String value1 = (String) ctx.lookup("varName1");
            String value2 = (String) ctx.lookup("varName2");

            writer.write("<div> context.xml value1 = " + value1 + "</div>");
            writer.write("<div> context.xml value2 = " + value2 + "</div>");
//            Map<String, String> getenv = System.getenv();

            writer.write("<hr/><div> get data source from context.xml </div>");
            writer.write("<div> " + datasource + "</div><hr/>");

            try (Connection connection = datasource.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    writer.write("<table  border='1' cellspacing='0' cellpadding='5'>");
                    try (ResultSet rs = statement.executeQuery("select * from INFORMATION_SCHEMA.TABLES")) {
                        final ResultSetMetaData metaData = rs.getMetaData();
                        final int columnCount = metaData.getColumnCount();
                        writer.write("<th>");
                        for (int i = 0; i < columnCount; i++) {
                            final String columnName = metaData.getColumnName(i + 1);
                            writer.write("<td>");
                            writer.write(columnName);
                            writer.write("</td>");
                        }
                        writer.write("</th>");
                        System.out.println();
                        while (rs.next()) {
                            writer.write("<tr>");
                            for (int i = 0; i < columnCount; i++) {
                                final Object columnValue = rs.getObject(i + 1);
                                writer.write("<td>");
                                writer.write(columnValue + "&nbsp;");
                                writer.write("</td>");
                            }
                            writer.write("</tr>");
                        }
                    }
                    writer.write("</table>");
                }
            }

            writer.write("</body></html>");
            writer.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }


        logger.info("response end.");
    }
}
