package com.nadiannis.phinroll.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nadiannis.phinroll.dto.DataList;
import com.nadiannis.phinroll.dto.Metadata;
import com.nadiannis.phinroll.dto.employee.EmployeeDto;
import com.nadiannis.phinroll.dto.employee.EmployeeMinimalDto;
import com.nadiannis.phinroll.dto.payroll.PayrollDetailDto;
import com.nadiannis.phinroll.dto.payroll.PayrollDto;
import com.nadiannis.phinroll.dto.payroll.PayrollFormDto;
import com.nadiannis.phinroll.service.PayrollService;
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
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PayrollControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PayrollService payrollService;

    @InjectMocks
    private PayrollController payrollController;

    @Autowired
    private ObjectMapper objectMapper;

    private PayrollDto payrollDto1;
    private PayrollDto payrollDto2;
    private PayrollFormDto payrollFormDto;
    private PayrollDetailDto payrollDetailDto1;
    private PayrollDetailDto payrollDetailDto2;
    private EmployeeDto employeeDto;
    private EmployeeMinimalDto employeeMinimalDto;

    @BeforeEach
    public void init() {
        employeeDto = EmployeeDto.builder().id(UUID.randomUUID().toString()).name("John").gender("m").grade(1).isMarried(true).build();
        employeeMinimalDto = EmployeeMinimalDto.builder().id(employeeDto.getId()).name(employeeDto.getName()).build();
        payrollDto1 = PayrollDto.builder()
                .attendanceDays(15)
                .absenceDays(5)
                .basicSalary(BigDecimal.valueOf(2000000))
                .totalPayCut(BigDecimal.valueOf(250000))
                .totalAllowance(BigDecimal.valueOf(750000))
                .headOfFamily(BigDecimal.valueOf(100000))
                .totalAdditionalSalary(BigDecimal.valueOf(850000))
                .startDate(LocalDate.parse("2024-06-01"))
                .endDate(LocalDate.parse("2024-06-30"))
                .employee(employeeMinimalDto)
                .build();
        payrollDto2 = PayrollDto.builder()
                .attendanceDays(10)
                .absenceDays(10)
                .basicSalary(BigDecimal.valueOf(2000000))
                .totalPayCut(BigDecimal.valueOf(500000))
                .totalAllowance(BigDecimal.valueOf(500000))
                .headOfFamily(BigDecimal.valueOf(100000))
                .totalAdditionalSalary(BigDecimal.valueOf(600000))
                .startDate(LocalDate.parse("2024-07-01"))
                .endDate(LocalDate.parse("2024-07-31"))
                .employee(employeeMinimalDto)
                .build();
        payrollFormDto = PayrollFormDto.builder()
                .employeeId(employeeMinimalDto.getId())
                .attendanceDays(15)
                .absenceDays(5)
                .startDate(LocalDate.parse("2024-06-01"))
                .endDate(LocalDate.parse("2024-06-30"))
                .build();
        payrollDetailDto1 = PayrollDetailDto.builder()
                .attendanceDays(15)
                .absenceDays(5)
                .basicSalary(BigDecimal.valueOf(2000000))
                .totalPayCut(BigDecimal.valueOf(250000))
                .totalAllowance(BigDecimal.valueOf(750000))
                .headOfFamily(BigDecimal.valueOf(100000))
                .totalAdditionalSalary(BigDecimal.valueOf(850000))
                .totalSalary(BigDecimal.valueOf(2600000))
                .startDate(LocalDate.parse("2024-06-01"))
                .endDate(LocalDate.parse("2024-06-30"))
                .employee(employeeDto)
                .build();
        payrollDetailDto2 = PayrollDetailDto.builder()
                .attendanceDays(10)
                .absenceDays(10)
                .basicSalary(BigDecimal.valueOf(2000000))
                .totalPayCut(BigDecimal.valueOf(500000))
                .totalAllowance(BigDecimal.valueOf(500000))
                .headOfFamily(BigDecimal.valueOf(100000))
                .totalAdditionalSalary(BigDecimal.valueOf(600000))
                .totalSalary(BigDecimal.valueOf(2100000))
                .startDate(LocalDate.parse("2024-07-01"))
                .endDate(LocalDate.parse("2024-07-31"))
                .employee(employeeDto)
                .build();
    }

    // @GetMapping
    // public ResponseEntity<?> getAll(
    //     @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) int page,
    //     @RequestParam(value = "limit", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false) int limit
    // )
    @Test
    public void PayrollController_GetAll_ReturnsPayrollDtoList() throws Exception {
        List<PayrollDto> payrolls = Arrays.asList(payrollDto1, payrollDto2);
        Metadata metadata = Metadata.builder().page(0).limit(10).totalElements(payrolls.size()).totalPages(1).last(true).build();
        DataList<List<PayrollDto>> payrollsWithMetadata = new DataList<>(payrolls, metadata);
        when(payrollService.findAll(0, 10)).thenReturn(payrollsWithMetadata);

        ResultActions response = mockMvc.perform(get("/api/v1/payrolls")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo","0")
                .param("pageSize", "10"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("payrolls retrieved successfully"))
                .andExpect(jsonPath("$.data.size()").value(payrollsWithMetadata.getData().size()))
                .andExpect(jsonPath("$.metadata", CoreMatchers.notNullValue()));
    }

    // @PostMapping
    // public ResponseEntity<?> add(@Valid @RequestBody PayrollFormDto body)
    @Test
    public void PayrollController_Add_ReturnsPayrollDto() throws Exception {
        when(payrollService.save(any(PayrollFormDto.class))).thenReturn(payrollDto1);

        ResultActions response = mockMvc.perform(post("/api/v1/payrolls")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payrollFormDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("payroll added successfully"))
                .andExpect(jsonPath("$.data.attendance_days").value(payrollDto1.getAttendanceDays()))
                .andExpect(jsonPath("$.data.absence_days").value(payrollDto1.getAbsenceDays()))
                .andExpect(jsonPath("$.data.basic_salary").value(payrollDto1.getBasicSalary()))
                .andExpect(jsonPath("$.data.total_pay_cut").value(payrollDto1.getTotalPayCut()))
                .andExpect(jsonPath("$.data.total_allowance").value(payrollDto1.getTotalAllowance()))
                .andExpect(jsonPath("$.data.head_of_family").value(payrollDto1.getHeadOfFamily()))
                .andExpect(jsonPath("$.data.total_additional_salary").value(payrollDto1.getTotalAdditionalSalary()))
                .andExpect(jsonPath("$.data.start_date").value(payrollDto1.getStartDate().toString()))
                .andExpect(jsonPath("$.data.end_date").value(payrollDto1.getEndDate().toString()))
                .andExpect(jsonPath("$.data.employee.id").value(payrollDto1.getEmployee().getId()))
                .andExpect(jsonPath("$.data.employee.name").value(payrollDto1.getEmployee().getName()));
    }

    // @GetMapping("/employees/{id}")
    // public ResponseEntity<?> getByEmployeeId(
    //     @PathVariable String id,
    //     @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) int page,
    //     @RequestParam(value = "limit", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false) int limit
    // )
    @Test
    public void PayrollController_GetPayrollsByEmployeeId_ReturnsPayrollDetailDtoList() throws Exception {
        String employeeId = employeeDto.getId();

        List<PayrollDetailDto> payrolls = Arrays.asList(payrollDetailDto1, payrollDetailDto2);
        Metadata metadata = Metadata.builder().page(0).limit(10).totalElements(payrolls.size()).totalPages(1).last(true).build();
        DataList<List<PayrollDetailDto>> payrollsWithMetadata = new DataList<>(payrolls, metadata);
        when(payrollService.findByEmployeeId(employeeId, 0, 10)).thenReturn(payrollsWithMetadata);

        ResultActions response = mockMvc.perform(get("/api/v1/payrolls/employees/" + employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo","0")
                .param("pageSize", "10"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("payrolls retrieved successfully"))
                .andExpect(jsonPath("$.data.size()").value(payrollsWithMetadata.getData().size()))
                .andExpect(jsonPath("$.metadata", CoreMatchers.notNullValue()));
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<?> getById(@PathVariable String id)
    @Test
    public void PayrollController_GetById_ReturnsPayrollDetailDto() throws Exception {
        String payrollId = UUID.randomUUID().toString();
        payrollDetailDto1.setId(payrollId);
        when(payrollService.findById(anyString())).thenReturn(payrollDetailDto1);

        ResultActions response = mockMvc.perform(get("/api/v1/payrolls/" + payrollId)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("payroll retrieved successfully"))
                .andExpect(jsonPath("$.data.id").value(payrollId))
                .andExpect(jsonPath("$.data.attendance_days").value(payrollDetailDto1.getAttendanceDays()))
                .andExpect(jsonPath("$.data.absence_days").value(payrollDetailDto1.getAbsenceDays()))
                .andExpect(jsonPath("$.data.basic_salary").value(payrollDetailDto1.getBasicSalary()))
                .andExpect(jsonPath("$.data.total_pay_cut").value(payrollDetailDto1.getTotalPayCut()))
                .andExpect(jsonPath("$.data.total_allowance").value(payrollDetailDto1.getTotalAllowance()))
                .andExpect(jsonPath("$.data.head_of_family").value(payrollDetailDto1.getHeadOfFamily()))
                .andExpect(jsonPath("$.data.total_additional_salary").value(payrollDetailDto1.getTotalAdditionalSalary()))
                .andExpect(jsonPath("$.data.start_date").value(payrollDetailDto1.getStartDate().toString()))
                .andExpect(jsonPath("$.data.end_date").value(payrollDetailDto1.getEndDate().toString()))
                .andExpect(jsonPath("$.data.employee.id").value(payrollDetailDto1.getEmployee().getId()))
                .andExpect(jsonPath("$.data.employee.name").value(payrollDetailDto1.getEmployee().getName()))
                .andExpect(jsonPath("$.data.employee.gender").value(payrollDetailDto1.getEmployee().getGender()))
                .andExpect(jsonPath("$.data.employee.grade").value(payrollDetailDto1.getEmployee().getGrade()))
                .andExpect(jsonPath("$.data.employee.is_married").value(payrollDetailDto1.getEmployee().getIsMarried()));
    }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<?> delete(@PathVariable String id)
    @Test
    public void PayrollController_Delete_ReturnsPayrollDto() throws Exception {
        String payrollId = UUID.randomUUID().toString();
        payrollDto1.setId(payrollId);
        when(payrollService.delete(anyString())).thenReturn(payrollDto1);

        ResultActions response = mockMvc.perform(delete("/api/v1/payrolls/" + payrollId)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("payroll deleted successfully"))
                .andExpect(jsonPath("$.data.id").value(payrollId));
    }

}
