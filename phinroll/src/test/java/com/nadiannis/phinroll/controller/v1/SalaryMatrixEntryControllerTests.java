package com.nadiannis.phinroll.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nadiannis.phinroll.dto.salarymatrixentry.SalaryMatrixEntryDto;
import com.nadiannis.phinroll.service.SalaryMatrixEntryService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class SalaryMatrixEntryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SalaryMatrixEntryService salaryMatrixEntryService;

    @InjectMocks
    private SalaryMatrixEntryController salaryMatrixEntryController;

    @Autowired
    private ObjectMapper objectMapper;

    private SalaryMatrixEntryDto salaryMatrixEntryDto1;
    private SalaryMatrixEntryDto salaryMatrixEntryDto2;

    @BeforeEach
    public void init() {
        salaryMatrixEntryDto1 = SalaryMatrixEntryDto.builder()
                .grade(1)
                .basicSalary(BigDecimal.valueOf(2000000))
                .payCut(BigDecimal.valueOf(50000))
                .allowance(BigDecimal.valueOf(50000))
                .headOfFamily(BigDecimal.valueOf(100000))
                .build();
        salaryMatrixEntryDto2 = SalaryMatrixEntryDto.builder()
                .grade(2)
                .basicSalary(BigDecimal.valueOf(4000000))
                .payCut(BigDecimal.valueOf(50000))
                .allowance(BigDecimal.valueOf(50000))
                .headOfFamily(BigDecimal.valueOf(100000))
                .build();
    }

    // @GetMapping
    // public ResponseEntity<?> getAll()
    @Test
    public void SalaryMatrixEntryController_GetAll_ReturnsSalaryMatrixEntryDtoList() throws Exception {
        List<SalaryMatrixEntryDto> salaryMatrixEntries = Arrays.asList(salaryMatrixEntryDto1, salaryMatrixEntryDto2);
        when(salaryMatrixEntryService.findAll()).thenReturn(salaryMatrixEntries);

        ResultActions response = mockMvc.perform(get("/api/v1/salary-matrix")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("salary matrix entries retrieved successfully"))
                .andExpect(jsonPath("$.data.size()").value(salaryMatrixEntries.size()))
                .andExpect(jsonPath("$.metadata", CoreMatchers.nullValue()));
    }

    // @PostMapping
    // public ResponseEntity<?> add(@Valid @RequestBody SalaryMatrixEntryDto body)
    @Test
    public void SalaryMatrixEntryController_Add_ReturnsSalaryMatrixEntryDto() throws Exception {
        when(salaryMatrixEntryService.save(any(SalaryMatrixEntryDto.class))).thenReturn(salaryMatrixEntryDto1);

        ResultActions response = mockMvc.perform(post("/api/v1/salary-matrix")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(salaryMatrixEntryDto1)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("salary matrix entry added successfully"))
                .andExpect(jsonPath("$.data.grade").value(salaryMatrixEntryDto1.getGrade()))
                .andExpect(jsonPath("$.data.basic_salary").value(salaryMatrixEntryDto1.getBasicSalary()))
                .andExpect(jsonPath("$.data.pay_cut").value(salaryMatrixEntryDto1.getPayCut()))
                .andExpect(jsonPath("$.data.allowance").value(salaryMatrixEntryDto1.getAllowance()))
                .andExpect(jsonPath("$.data.head_of_family").value(salaryMatrixEntryDto1.getHeadOfFamily()));
    }

    // @GetMapping("/{grade}")
    // public ResponseEntity<?> getByGrade(@PathVariable Integer grade)
    @Test
    public void SalaryMatrixEntryController_GetByGrade_ReturnsSalaryMatrixEntryDto() throws Exception {
        Integer grade = 2;
        salaryMatrixEntryDto1.setGrade(grade);
        when(salaryMatrixEntryService.findByGrade(anyInt())).thenReturn(salaryMatrixEntryDto1);

        ResultActions response = mockMvc.perform(get("/api/v1/salary-matrix/" + grade)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("salary matrix entry retrieved successfully"))
                .andExpect(jsonPath("$.data.grade").value(salaryMatrixEntryDto1.getGrade()))
                .andExpect(jsonPath("$.data.basic_salary").value(salaryMatrixEntryDto1.getBasicSalary()))
                .andExpect(jsonPath("$.data.pay_cut").value(salaryMatrixEntryDto1.getPayCut()))
                .andExpect(jsonPath("$.data.allowance").value(salaryMatrixEntryDto1.getAllowance()))
                .andExpect(jsonPath("$.data.head_of_family").value(salaryMatrixEntryDto1.getHeadOfFamily()));
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody SalaryMatrixEntryDto body)
    @Test
    public void SalaryMatrixEntryController_Update_ReturnsSalaryMatrixEntryDto() throws Exception {
        String salaryMatrixEntryId = UUID.randomUUID().toString();
        salaryMatrixEntryDto1.setId(salaryMatrixEntryId);
        salaryMatrixEntryDto2.setId(salaryMatrixEntryId);
        when(salaryMatrixEntryService.update(anyString(), any(SalaryMatrixEntryDto.class))).thenReturn(salaryMatrixEntryDto2);

        ResultActions response = mockMvc.perform(put("/api/v1/salary-matrix/" + salaryMatrixEntryId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(salaryMatrixEntryDto1)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("salary matrix entry updated successfully"))
                .andExpect(jsonPath("$.data.id").value(salaryMatrixEntryId))
                .andExpect(jsonPath("$.data.grade").value(salaryMatrixEntryDto2.getGrade()))
                .andExpect(jsonPath("$.data.basic_salary").value(salaryMatrixEntryDto2.getBasicSalary()))
                .andExpect(jsonPath("$.data.pay_cut").value(salaryMatrixEntryDto2.getPayCut()))
                .andExpect(jsonPath("$.data.allowance").value(salaryMatrixEntryDto2.getAllowance()))
                .andExpect(jsonPath("$.data.head_of_family").value(salaryMatrixEntryDto2.getHeadOfFamily()));
    }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<?> delete(@PathVariable String id)
    @Test
    public void SalaryMatrixEntryController_Delete_ReturnsSalaryMatrixEntryDto() throws Exception {
        String salaryMatrixEntryId = UUID.randomUUID().toString();
        salaryMatrixEntryDto1.setId(salaryMatrixEntryId);
        when(salaryMatrixEntryService.delete(anyString())).thenReturn(salaryMatrixEntryDto1);

        ResultActions response = mockMvc.perform(delete("/api/v1/salary-matrix/" + salaryMatrixEntryId)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("salary matrix entry deleted successfully"))
                .andExpect(jsonPath("$.data.id").value(salaryMatrixEntryId));
    }

}
