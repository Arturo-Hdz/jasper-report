package com.example.stech.service;

import com.example.stech.model.Address;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * @author Arturo Hdez on 5/15/24;
 * @project springboot-jasper-report-main
 */
public interface JReportService {
//    void exportJasperReport2(Integer id, HttpServletResponse response);
    void exportJasperReport2(Integer id, HttpServletResponse response);
    ResponseEntity<Address> getAddessById(Integer id);

}
