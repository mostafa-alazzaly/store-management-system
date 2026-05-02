package com.store.management.system.store_management.service;

import com.store.management.system.store_management.dto.employee.request.CreateEmployeeRequest;
import com.store.management.system.store_management.dto.employee.response.EmployeeResponse;
import com.store.management.system.store_management.dto.employee.request.UpdateEmployeeRequest;
import com.store.management.system.store_management.entity.Employee;
import com.store.management.system.store_management.entity.User;
import com.store.management.system.store_management.exception.*;
import com.store.management.system.store_management.repo.EmployeeRepo;
import com.store.management.system.store_management.repo.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {


    private final UserRepo userRepo;
    private final EmployeeRepo employeeRepo;
    public EmployeeService(EmployeeRepo employeeRepo,UserRepo userRepo){
        this.employeeRepo = employeeRepo ;
        this.userRepo = userRepo;
    }

    private EmployeeResponse mapToResponse(Employee employee){
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setId(employee.getId());
        employeeResponse.setFirstName(employee.getFirstName());
        employeeResponse.setLastName(employee.getLastName());
        employeeResponse.setAddress(employee.getAddress());
        employeeResponse.setZipCode(employee.getZipCode());
        employeeResponse.setJobTitle(employee.getJobTitle());
        employeeResponse.setStatus(employee.getStatus());
        employeeResponse.setSalary(employee.getSalary());
        employeeResponse.setHireDate(employee.getHireDate());
        return employeeResponse;
    }

    public List<EmployeeResponse> getAllEmployees(){
        return  employeeRepo.findAll()
                    .stream()
                    .map(this::mapToResponse)
                    .toList();
    }

    public EmployeeResponse getEmployeeById(Integer id){
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(()->new EmployeeNotFoundException("Employee not found with id : " +id));
        return mapToResponse(employee);

    }

    public void validateEmployeeUserAccountType (User user){
        if (user.getAccountType() != (User.AccountType.EMPLOYEE)){
            throw new InvalidAccountTypeException("Employee can only be linked to a user with account type EMPLOYEE");
        }
    }

    @Transactional
    public EmployeeResponse createEmployee(CreateEmployeeRequest request) {

        Employee employee = new Employee();

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setAddress(request.getAddress());
        employee.setZipCode(request.getZipCode());
        employee.setJobTitle(request.getJobTitle());
        employee.setStatus(
                request.getStatus() != null ? request.getStatus() : Employee.Status.ACTIVE);
        employee.setSalary(request.getSalary());
        employee.setHireDate(request.getHireDate());

        User user = userRepo.findById(request.getUserId())
                .orElseThrow( ()-> new UserNotFoundException("User not found : " + request.getUserId()));

        validateEmployeeUserAccountType(user);

        if (employeeRepo.existsByUser_Id(request.getUserId())) {
            throw new UserAlreadyAssignedToEmployeeException("User already assigned to employee");
        }

        employee.setUser(user);
        Employee savedEmployee = employeeRepo.save(employee);
        return mapToResponse(savedEmployee);
    }

    private void updateFirstName(Employee employee, UpdateEmployeeRequest request){
        if (request.getFirstName() != null){
            employee.setFirstName(request.getFirstName());
        }
    }
    private void updateLastName(Employee employee, UpdateEmployeeRequest request){
        if (request.getLastName() != null){
            employee.setLastName(request.getLastName());
        }
    }
    private void updateAddress(Employee employee, UpdateEmployeeRequest request){
        if (request.getAddress() != null){
            employee.setAddress(request.getAddress());
        }
    }
    private void updateZipCode(Employee employee, UpdateEmployeeRequest request){
        if (request.getZipCode() != null){
            employee.setZipCode(request.getZipCode());
        }
    }
    private void updateJobTitle(Employee employee, UpdateEmployeeRequest request){
        if(request.getJobTitle() != null){
            employee.setJobTitle(request.getJobTitle());
        }
    }
    private void updateStatus(Employee employee, UpdateEmployeeRequest request){
        if (request.getStatus() != null){
            employee.setStatus(request.getStatus());
        }
    }
    private void  updateSalary(Employee employee, UpdateEmployeeRequest request){
        if(request.getSalary() != null){
            employee.setSalary(request.getSalary());
        }
    }
    private void updateHireDate(Employee employee, UpdateEmployeeRequest request){
        if(request.getHireDate() != null){
            employee.setHireDate(request.getHireDate());
        }
    }
    private void updateUserId(Employee employee, UpdateEmployeeRequest request){
        Integer userId = request.getUserId();

        if (userId == null ||
                (employee.getUser()!=null && employee.getUser().getId()==userId)){
            return;
        }
        User user = userRepo.findById(userId)
                .orElseThrow( ()-> new UserNotFoundException("User not found with id  : " + userId));

        validateEmployeeUserAccountType(user);

        if (employeeRepo.existsByUser_Id(userId)) {
            throw new UserAlreadyAssignedToEmployeeException("User already assigned to employee : " + userId);
        }

        employee.setUser(user);
    }

    @Transactional
    public EmployeeResponse updateEmployee(int id, UpdateEmployeeRequest request){
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() ->new EmployeeNotFoundException("Employee not found with id: " + id));

        updateFirstName(employee, request);
        updateLastName(employee, request);
        updateAddress(employee, request);
        updateZipCode(employee, request);
        updateJobTitle(employee, request);
        updateStatus(employee, request);
        updateSalary(employee, request);
        updateHireDate(employee, request);
        updateUserId(employee, request);

        employeeRepo.save(employee);

        return  mapToResponse(employee);
    }

    @Transactional
    public void  deleteEmployee(int id){
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() ->new EmployeeNotFoundException("Employee not found with id: " + id));

        User user = employee.getUser();
        employeeRepo.delete(employee);

        if (user != null){
            user.setEnabled(false);
            user.setAccountNonLocked(false);
            userRepo.save(user);
        }
    }
}
