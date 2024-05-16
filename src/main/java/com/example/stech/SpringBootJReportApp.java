package com.example.stech;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.example.stech.service.JReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.stech.model.Address;
import com.example.stech.repository.AddressRepository;
import com.example.stech.service.JReportServiceImpl;

import net.sf.jasperreports.engine.JRException;

@SpringBootApplication
@RestController
public class SpringBootJReportApp {

    @Autowired
    JReportService jReportService;

    @Autowired
    private AddressRepository repository;

    @Autowired
    private JReportServiceImpl service;



    @GetMapping("/getAddress")
    public List<Address> getAddress() {
        List<Address> address = (List<Address>) repository.findAll();
        return address;
    }
 
         
    @GetMapping("/jasperpdf/export")
    public void createPDF(HttpServletResponse response) throws IOException, JRException {
       response.setContentType("application/pdf");
       DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
       String currentDateTime = dateFormatter.format(new Date());
       String headerKey = "Content-Disposition";
       String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
       response.setHeader(headerKey, headerValue);
        service.exportJasperReport(response);
    }

    @GetMapping("/jasperpdf/export/{id}")
    public void createPDF2(@PathVariable("id")Integer id, HttpServletResponse response) throws IOException, JRException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        jReportService.exportJasperReport2(id, response);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Address> listarAddressPorId(@PathVariable Integer id){
        try{
            return jReportService.getAddessById(id);
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new Address(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
 
    public static void main(String[] args) {
        SpringApplication.run(SpringBootJReportApp.class, args);
    }
 
}
