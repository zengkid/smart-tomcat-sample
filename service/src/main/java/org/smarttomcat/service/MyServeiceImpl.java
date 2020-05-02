package org.smarttomcat.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyServeiceImpl implements MyServeice {
    private Logger logger = LoggerFactory.getLogger(MyServeiceImpl.class);

    public String doService() {
        logger.info("I am doing the service");
        return "do a service";
    }
}
