package com.store.management.system.store_management.service;

import com.store.management.system.store_management.dto.CreateCustomerRequest;
import com.store.management.system.store_management.dto.CustomerResponse;
import com.store.management.system.store_management.dto.UpdateCustomerRequest;
import com.store.management.system.store_management.entity.Customer;
import com.store.management.system.store_management.entity.User;
import com.store.management.system.store_management.exception.*;
import com.store.management.system.store_management.repo.CustomerRepo;
import com.store.management.system.store_management.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final UserRepo userRepo;

    @Autowired
    public CustomerService(CustomerRepo customerRepo, UserRepo userRepo) {
        this.customerRepo = customerRepo;
        this.userRepo = userRepo;
    }

    private CustomerResponse mapToCustomerResponse(Customer customer){
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setId(customer.getId());
        customerResponse.setFirstName(customer.getFirstName());
        customerResponse.setLastName(customer.getLastName());
        customerResponse.setAddress(customer.getAddress());
        customerResponse.setCity(customer.getCity());
        customerResponse.setCountry(customer.getCountry());
        customerResponse.setZipCode(customer.getZipCode());
        customerResponse.setShopName(customer.getShopName());
        customerResponse.setStatus(customer.getStatus());
        customerResponse.setNotes(customer.getNotes());
        return  customerResponse;
    }

    public List<CustomerResponse> getAllCustomers(){
        return customerRepo.findAll()
                .stream()
                .map(this::mapToCustomerResponse)
                .toList();
    }

    public CustomerResponse getCustomerById(Integer id){
        Customer customer = customerRepo.findById(id)
                .orElseThrow (()-> new CustomerNotFoundException ("Customer not found with id " + id));

        return mapToCustomerResponse(customer);
    }


    private  void validateCustomerUserAccountType(User user){
        if (user.getAccountType() != (User.AccountType.CUSTOMER)){
            throw new InvalidAccountTypeException("Customer can only be linked to a user with account type CUSTOMER");
        }
    }

    @Transactional
    public CustomerResponse createCustomer(CreateCustomerRequest request) {

        Customer customer = new Customer();

        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setAddress(request.getAddress());
        customer.setCity(request.getCity());
        customer.setCountry(request.getCountry());
        customer.setZipCode(request.getZipCode());
        customer.setShopName(request.getShopName());
        customer.setStatus(
                request.getStatus() != null ? request.getStatus() : Customer.Status.ACTIVE);
        customer.setNotes(request.getNotes());

        User user  = userRepo.findById(request.getUserId())
                .orElseThrow (()-> new UserNotFoundException("User not found : " + request.getUserId()));

        validateCustomerUserAccountType(user);

        if (customerRepo.existsByUser_Id(request.getUserId())) {
            throw new UserAlreadyAssignedToCustomerException("User already assigned to Customer");
        }

        customer.setUser(user);
        Customer savedCustomer = customerRepo.save(customer);
        return  mapToCustomerResponse(savedCustomer);
    }

    private void updateFirstName(Customer customer , UpdateCustomerRequest request){
        if(request.getFirstName() != null){
            customer.setFirstName(request.getFirstName());
        }
    }
    private void updateLastName(Customer customer , UpdateCustomerRequest request){
        if(request.getLastName() != null){
            customer.setLastName(request.getLastName());
        }
    }
    private void  updateAddress(Customer customer , UpdateCustomerRequest request){
        if(request.getAddress() != null){
            customer.setAddress(request.getAddress());
        }
    }
    private void updateCity(Customer customer , UpdateCustomerRequest request){
        if(request.getCity() != null){
            customer.setCity(request.getCity());
        }
    }
    private  void updateCountry(Customer customer , UpdateCustomerRequest request){
        if(request.getCountry() != null){
            customer.setCountry(request.getCountry());
        }
    }
    private void updateZipCode(Customer customer , UpdateCustomerRequest request){
        if(request.getZipCode() != null){
            customer.setZipCode(request.getZipCode());
        }
    }
    private void updateShopName(Customer customer , UpdateCustomerRequest request){
        if(request.getShopName() != null){
        customer.setShopName(request.getShopName());}
    }
    private void updateNotes(Customer customer , UpdateCustomerRequest request){
        if (request.getNotes() != null){
            customer.setNotes(request.getNotes());
        }
    }
    private void updateStatus(Customer customer , UpdateCustomerRequest request){
        if (request.getStatus() != null){
            customer.setStatus(request.getStatus());
        }
    }
    private void updateUserId(Customer customer , UpdateCustomerRequest request){
        Integer  userId = request.getUserId();
        if (userId == null){
            return;
        }
        if(customer.getUser()!=null &&  customer.getUser().getId()==userId){
            return;
        }
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id  : " + userId));

        validateCustomerUserAccountType(user);

        if (customerRepo.existsByUser_Id(userId)){
            throw new UserAlreadyAssignedToCustomerException("User already assigned to Customer");
        }
        customer.setUser(user);

    }

    @Transactional
    public CustomerResponse updateCustomer(Integer id ,UpdateCustomerRequest request){
        Customer customer = customerRepo.findById(id)
                        .orElseThrow (()-> new CustomerNotFoundException("Customer not found with id " + id));

        updateFirstName(customer, request);
        updateLastName(customer, request);
        updateAddress(customer, request);
        updateCity(customer, request);
        updateCountry(customer, request);
        updateZipCode(customer, request);
        updateShopName(customer, request);
        updateStatus(customer, request);
        updateNotes(customer, request);
        updateUserId(customer, request);

        customerRepo.save(customer);
        return mapToCustomerResponse(customer);

    }
    @Transactional
    public  void deleteCustomerById(Integer id){
        Customer customer = customerRepo.findById(id)
                .orElseThrow (()-> new CustomerNotFoundException("Customer not found with id " + id));

        User user = customer.getUser();
        customerRepo.delete(customer);

        if (user!=null){
            user.setEnabled(false);
            user.setAccountNonLocked(false);
            userRepo.save(user);
        }
    }
}
