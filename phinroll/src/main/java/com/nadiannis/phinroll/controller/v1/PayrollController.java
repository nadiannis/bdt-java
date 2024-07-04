package com.nadiannis.phinroll.controller.v1;

import com.nadiannis.phinroll.dto.*;
import com.nadiannis.phinroll.dto.payroll.PayrollDetailDto;
import com.nadiannis.phinroll.dto.payroll.PayrollDto;
import com.nadiannis.phinroll.dto.payroll.PayrollFormDto;
import com.nadiannis.phinroll.service.PayrollService;
import com.nadiannis.phinroll.utils.Constants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/payrolls")
public class PayrollController {

    private PayrollService service;

    @Autowired
    public PayrollController(PayrollService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) int page,
            @RequestParam(value = "limit", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false) int limit
    ) {
        DataList<List<PayrollDto>> payrollsWithMetadata = service.findAll(page, limit);
        String message = "payrolls retrieved successfully";

        SuccessResponse<List<PayrollDto>> response = new SuccessResponse<>(message, payrollsWithMetadata.getData(), payrollsWithMetadata.getMetadata());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody PayrollFormDto body) {
        PayrollDto data = service.save(body);
        String message = "payroll added successfully";

        SuccessResponse<PayrollDto> response = new SuccessResponse<>(message, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<?> getByEmployeeId(
            @PathVariable String id,
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) int page,
            @RequestParam(value = "limit", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false) int limit
    ) {
        DataList<List<PayrollDetailDto>> payrollsWithMetadata = service.findByEmployeeId(id, page, limit);
        String message = "payrolls retrieved successfully";

        SuccessResponse<List<PayrollDetailDto>> response = new SuccessResponse<>(message, payrollsWithMetadata.getData(), payrollsWithMetadata.getMetadata());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        PayrollDetailDto data = service.findById(id);
        String message = "payroll retrieved successfully";

        SuccessResponse<PayrollDetailDto> response = new SuccessResponse<>(message, data);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        PayrollDto data = service.delete(id);
        String message = "payroll deleted successfully";

        SuccessResponse<PayrollDto> response = new SuccessResponse<>(message, data);
        return ResponseEntity.ok(response);
    }

}
