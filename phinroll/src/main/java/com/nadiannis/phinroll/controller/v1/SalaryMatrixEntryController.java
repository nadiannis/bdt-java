package com.nadiannis.phinroll.controller.v1;

import com.nadiannis.phinroll.dto.salarymatrixentry.SalaryMatrixEntryDto;
import com.nadiannis.phinroll.dto.SuccessResponse;
import com.nadiannis.phinroll.service.SalaryMatrixEntryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/salary-matrix")
public class SalaryMatrixEntryController {

    private SalaryMatrixEntryService service;

    @Autowired
    public SalaryMatrixEntryController(SalaryMatrixEntryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<SalaryMatrixEntryDto> data = service.findAll();
        String message = "salary matrix entries retrieved successfully";

        SuccessResponse<List<SalaryMatrixEntryDto>> response = new SuccessResponse<>(message, data);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody SalaryMatrixEntryDto body) {
        SalaryMatrixEntryDto data = service.save(body);
        String message = "salary matrix entry added successfully";

        SuccessResponse<SalaryMatrixEntryDto> response = new SuccessResponse<>(message, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{grade}")
    public ResponseEntity<?> getByGrade(@PathVariable Integer grade) {
        SalaryMatrixEntryDto salaryMatrixEntry = service.findByGrade(grade);
        String message = "salary matrix entry retrieved successfully";

        SuccessResponse<SalaryMatrixEntryDto> response = new SuccessResponse<>(message, salaryMatrixEntry);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody SalaryMatrixEntryDto body) {
        SalaryMatrixEntryDto data = service.update(id, body);
        String message = "salary matrix entry updated successfully";

        SuccessResponse<SalaryMatrixEntryDto> response = new SuccessResponse<>(message, data);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        SalaryMatrixEntryDto data = service.delete(id);
        String message = "salary matrix entry deleted successfully";

        SuccessResponse<SalaryMatrixEntryDto> response = new SuccessResponse<>(message, data);
        return ResponseEntity.ok(response);
    }

}
