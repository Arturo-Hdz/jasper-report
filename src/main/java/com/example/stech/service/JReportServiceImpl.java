package com.example.stech.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.example.stech.exceptions.NotFoundException;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.example.stech.model.Address;
import com.example.stech.repository.AddressRepository;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
@Slf4j
public class JReportServiceImpl implements JReportService {
 
    @Autowired
    private AddressRepository repository;

    public void exportJasperReport(HttpServletResponse response) throws JRException, IOException {
        List<Address> address = repository.findAll();
        //Get file and compile it
        File file = ResourceUtils.getFile("classpath:employees.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(address);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Simplifying Tech");
        //Fill Jasper report
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        //Export report
        JasperExportManager.exportReportToPdfStream(jasperPrint,response.getOutputStream());
    }

    public void exportJasperReport2(Integer id, HttpServletResponse response) {
        try {
            Address address2 = repository.getEmployeeById(id);
//                    .orElseThrow(() -> new NotFoundException("Order Not found with id = " + id));
            log.info("Id= "+ id);
            log.info("Info= "+ address2.getFirstname()+" "+address2.getLastname()+" "+
                    address2.getStreet()+" "+address2.getCity());

            //Get file and compile it
            File file = ResourceUtils.getFile("classpath:employeesid.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singleton(address2));
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("id", id);
//        parameters.put("createdBy", "Simplifying Tech");

            //Fill Jasper report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);

            //Export report
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResponseEntity<Address> getAddessById(Integer id) {
        try{
            log.info("Address "+ id);
//            Address address2 = repository.getEmployeeById(id);
            return new ResponseEntity<>(repository.getAddressById(id), HttpStatus.OK);
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new Address(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
