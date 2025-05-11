package com.metasoft.restyle.integration.flow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metasoft.restyle.platform.iam.domain.model.aggregates.User;
import com.metasoft.restyle.platform.iam.domain.model.valueobjects.Roles;
import com.metasoft.restyle.platform.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.metasoft.restyle.platform.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.metasoft.restyle.platform.iam.infrastructure.tokens.jwt.BearerTokenService;
import com.metasoft.restyle.platform.project.interfaces.rest.resources.CreateProjectResource;
import com.metasoft.restyle.platform.project.interfaces.rest.resources.ProjectResource;
import com.metasoft.restyle.platform.projectRequest.interfaces.rest.resources.CreateProjectRequestResource;
import com.metasoft.restyle.platform.projectRequest.interfaces.rest.resources.ProjectRequestResource;
import com.metasoft.restyle.platform.reviews.interfaces.rest.resources.CreateReviewResource;
import com.metasoft.restyle.platform.reviews.interfaces.rest.resources.ReviewResource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(properties = {
        "spring.security.user.name=admin",
        "spring.security.user.password=admin",
        "jwt.secret=testSecretKeyForJwtTokenGenerationInTestEnvironment1234567890",
        "jwt.expiration=86400000"
})
public class ProjectAssignmentToCompletionFlowTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BearerTokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    private static String businessToken;
    private static String contractorToken;
    private static Long businessUserId;
    private static Long contractorUserId;
    private static Long projectRequestId;
    private static Long projectId;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        // Create business user if not exists
        if (!userRepository.existsByUsername("business_user")) {
            User biz = new User();
            biz.setUsername("business_user");
            biz.setPassword(passwordEncoder.encode("password123"));
            biz.setEmail("business@example.com");
            biz.setFirstName("John");
            biz.setPaternalSurname("Doe");
            biz.setMaternalSurname("Smith");
            User savedBiz = userRepository.save(biz);
            var role = roleRepository.findByName(Roles.ROLE_REMODELER)
                    .orElseThrow(() -> new RuntimeException("ROLE_REMODELER not found"));
            savedBiz.setRoles(Set.of(role));
            userRepository.save(savedBiz);
            businessUserId = savedBiz.getId();
            // generate token
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    "business_user", null,
                    List.of(new SimpleGrantedAuthority("ROLE_REMODELER"))
            );
            businessToken = tokenService.generateToken(auth);
        }

        // Create contractor user if not exists
        if (!userRepository.existsByUsername("contractor_user")) {
            User ctr = new User();
            ctr.setUsername("contractor_user");
            ctr.setPassword(passwordEncoder.encode("password123"));
            ctr.setEmail("contractor@example.com");
            ctr.setFirstName("Jane");
            ctr.setPaternalSurname("Smith");
            ctr.setMaternalSurname("Johnson");
            User savedCtr = userRepository.save(ctr);
            var role = roleRepository.findByName(Roles.ROLE_CONTRACTOR)
                    .orElseThrow(() -> new RuntimeException("ROLE_CONTRACTOR not found"));
            savedCtr.setRoles(Set.of(role));
            userRepository.save(savedCtr);
            contractorUserId = savedCtr.getId();
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    "contractor_user", null,
                    List.of(new SimpleGrantedAuthority("ROLE_CONTRACTOR"))
            );
            contractorToken = tokenService.generateToken(auth);
        }
    }

    @Test
    @Order(1)
    void testCreateProjectRequestByBusiness() throws Exception {
        // Business submits a project request
        CreateProjectRequestResource req = new CreateProjectRequestResource(
                "Kitchen Renovation", "Smith", "client@example.com",
                "123456789", "123 Main St", "New York",
                "Complete kitchen renovation", businessUserId.intValue(), contractorUserId.intValue(),
                new Date(System.currentTimeMillis() + 30L * 24 * 3600 * 1000), 3, 50000
        );
        MvcResult res = mockMvc.perform(post("/api/v1/project-requests")
                        .header("Authorization", "Bearer " + businessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andReturn();
        ProjectRequestResource pr = objectMapper.readValue(
                res.getResponse().getContentAsString(), ProjectRequestResource.class
        );
        projectRequestId = pr.id();
        assertNotNull(projectRequestId);
    }

    @Test
    @Order(2)
    void testCreateProjectByContractor() throws Exception {
        assertNotNull(projectRequestId);
        CreateProjectResource projectRes = new CreateProjectResource(
                "Kitchen Renovation Project", "Implementation of kitchen",
                businessUserId.intValue(), contractorUserId.intValue(),
                new Date(), new Date(System.currentTimeMillis() + 30L * 24 * 3600 * 1000), "kitchen.jpg"
        );
        MvcResult res = mockMvc.perform(post("/api/v1/projects")
                        .header("Authorization", "Bearer " + contractorToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRes)))
                .andExpect(status().isCreated())
                .andReturn();
        ProjectResource prj = objectMapper.readValue(
                res.getResponse().getContentAsString(), ProjectResource.class
        );
        projectId = prj.id();
        assertNotNull(projectId);
    }

    @Test
    @Order(3)
    void testAddReviewByBusiness() throws Exception {
        assertNotNull(projectId);
        CreateReviewResource review = new CreateReviewResource(
                contractorUserId.intValue(), projectId.intValue(),
                "4 weeks", 5, "Excellent work!", "completed.jpg"
        );
        MvcResult res = mockMvc.perform(post("/api/v1/reviews")
                        .header("Authorization", "Bearer " + businessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(review)))
                .andExpect(status().isCreated())
                .andReturn();
        ReviewResource rv = objectMapper.readValue(
                res.getResponse().getContentAsString(), ReviewResource.class
        );
        assertEquals(5, rv.rating());
        assertEquals(contractorUserId.intValue(), rv.contractorId());
        assertEquals(projectId.intValue(), rv.projectId());
    }
}
