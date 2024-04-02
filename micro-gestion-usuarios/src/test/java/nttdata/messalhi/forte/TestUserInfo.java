package nttdata.messalhi.forte;


import nttdata.messalhi.forte.dao.UserInfoDAO;
import nttdata.messalhi.forte.entities.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class TestUserInfo {
    @Autowired
    private MockMvc server;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private UserInfoDAO userInfoDAO;

    @BeforeEach
    void setUp() {
        String username = "firstUserTest";
        String email = "firstEmailTest";
        String type = "firstTypeTest";
        Date last_access = new Date();
        UserInfo userInfo = new UserInfo(username, email, type);
        userInfo.setLast_access(last_access);
        this.userInfoDAO.save(userInfo);
    }

    @Test
    void testAddOK()throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String requestBody = "{ \"username\": \"userTest\", \"email\": \"emailTest\", \"type\": \"typeTest\"}";

        RequestBuilder request = MockMvcRequestBuilders
                .post("/user/UserInfo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testAddError()throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String requestBody = "{ \"username\": \"firstUserTest\", \"email\": \"emailTest\", \"type\": \"typeTest\"}";

        RequestBuilder request = MockMvcRequestBuilders
                .post("/user/UserInfo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    void testGetOk()throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        RequestBuilder request = MockMvcRequestBuilders
                .get("/user/UserInfo/firstUserTest")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
        }
    @Test
    void testGetError()throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        RequestBuilder request = MockMvcRequestBuilders
                .get("/user/UserInfo/InvalidUser")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    void testDeleteOk()throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/user/UserInfo/firstUserTest")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testUpdateOk()throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        String requestBody = "{ \"username\": \"firstUserTest\", \"email\": \"emailTest\", \"type\": \"typeTest\"}";

        RequestBuilder request = MockMvcRequestBuilders
                .put("/user/UserInfo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
        }
    @Test
    void testUpdateError()throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        String requestBody = "{ \"username\": \"InvalidUser\", \"email\": \"emailTest\", \"type\": \"typeTest\"}";

        RequestBuilder request = MockMvcRequestBuilders
                .put("/user/UserInfo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }
}