package com.winterhold.service;

import com.winterhold.dao.CustomerRepository;
import com.winterhold.dto.customer.CustomerGridDTO;
import com.winterhold.dto.customer.InsertCustomerDTO;
import com.winterhold.dto.customer.SingleCustomerDTO;
import com.winterhold.dto.customer.UpdateCustomerDTO;
import com.winterhold.dto.utility.DropdownDTO;
import com.winterhold.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Page<CustomerGridDTO> getCustomerGrid(Integer pageNumber, String number,
                                                 String searchName){
        var pageable = PageRequest.of(pageNumber - 1, 10);
        return customerRepository.findAll(number, searchName, pageable);
    }

    public SingleCustomerDTO getOneCustomer(String number){
        Customer customer = customerRepository.findById(number).get();
        return SingleCustomerDTO.builder()
                .membershipNumber(customer.getMembershipNumber())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .birthDate(customer.getBirthDate())
                .gender(customer.getGender())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .membershipExpireDate(customer.getMembershipExpireDate())
                .build();
    }

    public SingleCustomerDTO insertCustomer(InsertCustomerDTO dto){
        dto.setMembershipExpireDate(LocalDate.now().plusYears(2));
        Customer customer = new Customer(
                dto.getMembershipNumber(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getBirthDate(),
                dto.getGender(),
                dto.getPhone(),
                dto.getAddress(),
                dto.getMembershipExpireDate());
        customerRepository.save(customer);
        return SingleCustomerDTO.builder()
                .membershipNumber(dto.getMembershipNumber())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .birthDate(dto.getBirthDate())
                .gender(dto.getGender())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .membershipExpireDate(dto.getMembershipExpireDate())
                .build();
    }

    public SingleCustomerDTO updateCustomer(UpdateCustomerDTO dto){
        Customer customer = new Customer(
                dto.getMembershipNumber(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getBirthDate(),
                dto.getGender(),
                dto.getPhone(),
                dto.getAddress(),
                dto.getMembershipExpireDate());
        customerRepository.save(customer);
        return SingleCustomerDTO.builder()
                .membershipNumber(dto.getMembershipNumber())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .birthDate(dto.getBirthDate())
                .gender(dto.getGender())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .membershipExpireDate(dto.getMembershipExpireDate())
                .build();
    }

    public UpdateCustomerDTO getUpdate(String number){
        var entity = customerRepository.findById(number).get();
        return UpdateCustomerDTO.builder()
                .membershipNumber(entity.getMembershipNumber())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .birthDate(entity.getBirthDate())
                .gender(entity.getGender())
                .phone(entity.getPhone())
                .address(entity.getAddress())
                .membershipExpireDate(entity.getMembershipExpireDate())
                .build();
    }

    public Boolean isValidMembershipNumber(String number){
        var total = customerRepository.count(number);
        return total < 1;
    }

    public void delete(String number){
        customerRepository.deleteById(number);
    }

    public SingleCustomerDTO extend(String number){
        Customer dto = customerRepository.findById(number).get();
        dto.setMembershipExpireDate(LocalDate.now().plusYears(2));
        customerRepository.save(dto);
        return SingleCustomerDTO.builder()
                .membershipNumber(dto.getMembershipNumber())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .birthDate(dto.getBirthDate())
                .gender(dto.getGender())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .membershipExpireDate(dto.getMembershipExpireDate())
                .build();
    }

    public List<DropdownDTO> getCustomerDropdown(){
        return customerRepository.getDropdown(LocalDate.now());
    }

    public Long getTotalDependencies(String number){
        return customerRepository.countById(number);
    }
}
