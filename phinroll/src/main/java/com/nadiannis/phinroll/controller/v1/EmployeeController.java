package com.nadiannis.phinroll.controller.v1;

import com.nadiannis.phinroll.dto.DataList;
import com.nadiannis.phinroll.dto.employee.EmployeeDto;
import com.nadiannis.phinroll.dto.SuccessResponse;
import com.nadiannis.phinroll.service.EmployeeService;
import com.nadiannis.phinroll.utils.Constants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private EmployeeService service;

    @Autowired
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) int page,
            @RequestParam(value = "limit", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false) int limit
    ) {
        DataList<List<EmployeeDto>> employeesWithMetadata = service.findAll(page, limit);
        String message = "employees retrieved successfully";

        SuccessResponse<List<EmployeeDto>> response = new SuccessResponse<>(message, employeesWithMetadata.getData(), employeesWithMetadata.getMetadata());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody EmployeeDto body) {
        EmployeeDto data = service.save(body);
        String message = "employee added successfully";

        SuccessResponse<EmployeeDto> response = new SuccessResponse<>(message, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        EmployeeDto employee = service.findById(id);
        String message = "employee retrieved successfully";

        SuccessResponse<EmployeeDto> response = new SuccessResponse<>(message, employee);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody EmployeeDto body) {
        EmployeeDto data = service.update(id, body);
        String message = "employee updated successfully";

        SuccessResponse<EmployeeDto> response = new SuccessResponse<>(message, data);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        EmployeeDto data = service.delete(id);
        String message = "employee deleted successfully";

        SuccessResponse<EmployeeDto> response = new SuccessResponse<>(message, data);
        return ResponseEntity.ok(response);
    }

}
