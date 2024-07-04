package com.nadiannis.phinroll.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nadiannis.phinroll.dto.DataList;
import com.nadiannis.phinroll.dto.Metadata;
import com.nadiannis.phinroll.dto.employee.EmployeeDto;
import com.nadiannis.phinroll.service.EmployeeService;
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
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @Autowired
    private ObjectMapper objectMapper;

    private EmployeeDto employeeDto1;
    private EmployeeDto employeeDto2;

    @BeforeEach
    public void init() {
        employeeDto1 = EmployeeDto.builder().name("John").gender("m").grade(1).isMarried(true).build();
        employeeDto2 = EmployeeDto.builder().name("Taylor").gender("f").grade(2).isMarried(false).build();
    }

    // @GetMapping
    // ResponseEntity<?> getAll(
    //     @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) int page,
    //     @RequestParam(value = "limit", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false) int limit
    // )
    @Test
    public void EmployeeController_GetAll_ReturnsEmployeeDtoList() throws Exception {
        List<EmployeeDto> employees = Arrays.asList(employeeDto1, employeeDto2);
        Metadata metadata = Metadata.builder().page(0).limit(10).totalElements(employees.size()).totalPages(1).last(true).build();
        DataList<List<EmployeeDto>> employeesWithMetadata = new DataList<>(employees, metadata);
        when(employeeService.findAll(0, 10)).thenReturn(employeesWithMetadata);

        ResultActions response = mockMvc.perform(get("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo","0")
                .param("pageSize", "10"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("employees retrieved successfully"))
                .andExpect(jsonPath("$.data.size()").value(employeesWithMetadata.getData().size()))
                .andExpect(jsonPath("$.metadata", CoreMatchers.notNullValue()));
    }

    // @PostMapping
    // public ResponseEntity<?> add(@Valid @RequestBody EmployeeDto body)
    @Test
    public void EmployeeController_Add_ReturnsEmployeeDto() throws Exception {
        when(employeeService.save(any(EmployeeDto.class))).thenReturn(employeeDto1);

        ResultActions response = mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDto1)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("employee added successfully"))
                .andExpect(jsonPath("$.data.name").value(employeeDto1.getName()))
                .andExpect(jsonPath("$.data.gender").value(employeeDto1.getGender()))
                .andExpect(jsonPath("$.data.grade").value(employeeDto1.getGrade()))
                .andExpect(jsonPath("$.data.is_married").value(employeeDto1.getIsMarried()));
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<?> getById(@PathVariable String id)
    @Test
    public void EmployeeController_GetById_ReturnsEmployeeDto() throws Exception {
        String employeeId = UUID.randomUUID().toString();
        employeeDto1.setId(employeeId);
        when(employeeService.findById(anyString())).thenReturn(employeeDto1);

        ResultActions response = mockMvc.perform(get("/api/v1/employees/" + employeeId)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("employee retrieved successfully"))
                .andExpect(jsonPath("$.data.id").value(employeeId))
                .andExpect(jsonPath("$.data.name").value(employeeDto1.getName()))
                .andExpect(jsonPath("$.data.gender").value(employeeDto1.getGender()))
                .andExpect(jsonPath("$.data.grade").value(employeeDto1.getGrade()))
                .andExpect(jsonPath("$.data.is_married").value(employeeDto1.getIsMarried()));
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody EmployeeDto body)
    @Test
    public void EmployeeController_Update_ReturnsEmployeeDto() throws Exception {
        String employeeId = UUID.randomUUID().toString();
        employeeDto1.setId(employeeId);
        employeeDto2.setId(employeeId);
        when(employeeService.update(anyString(), any(EmployeeDto.class))).thenReturn(employeeDto2);

        ResultActions response = mockMvc.perform(put("/api/v1/employees/" + employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDto1)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("employee updated successfully"))
                .andExpect(jsonPath("$.data.id").value(employeeId))
                .andExpect(jsonPath("$.data.name").value(employeeDto2.getName()))
                .andExpect(jsonPath("$.data.gender").value(employeeDto2.getGender()))
                .andExpect(jsonPath("$.data.grade").value(employeeDto2.getGrade()))
                .andExpect(jsonPath("$.data.is_married").value(employeeDto2.getIsMarried()));
    }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<?> delete(@PathVariable String id)
    @Test
    public void EmployeeController_Delete_ReturnsEmployeeDto() throws Exception {
        String employeeId = UUID.randomUUID().toString();
        employeeDto1.setId(employeeId);
        when(employeeService.delete(anyString())).thenReturn(employeeDto1);

        ResultActions response = mockMvc.perform(delete("/api/v1/employees/" + employeeId)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("employee deleted successfully"))
                .andExpect(jsonPath("$.data.id").value(employeeId));
    }

}
