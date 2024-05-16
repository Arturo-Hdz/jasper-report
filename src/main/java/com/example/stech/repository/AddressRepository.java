package com.example.stech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.stech.model.Address;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    Address getEmployeeById(@Param("id") Integer id);
    Address getAddressById(Integer id);
}
