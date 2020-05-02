package org.smarttomcat.web;

import org.smarttomcat.service.MyServeice;
import org.smarttomcat.service.MyServeiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {

    private Logger logger = LoggerFactory.getLogger(WelcomeServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        logger.info("request welcome");

        MyServeice service = new MyServeiceImpl();
        final String s = service.doService();


        try (PrintWriter writer = res.getWriter()) {

            writer.write("<html>");
            writer.write("<head>");
            writer.write("<title>web sample</title>");
            writer.write("</head>");
            writer.write("<body>");

            writer.write("<div>welcome sample</div>");
            writer.write("<div>" + s + "</div>");

            writer.write("<div><a href='db'>context</a></div>");

            writer.write("</body></html>");

        }



    }
}
