package nttdata.messalhi.forte;


import nttdata.messalhi.forte.dao.UserCredentialsDAO;
import nttdata.messalhi.forte.dao.UserInfoDAO;
import nttdata.messalhi.forte.entities.UserCredentials;
import nttdata.messalhi.forte.entities.UserInfo;
import nttdata.messalhi.forte.utils.UserUtils;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class TestUserCredentials {
    @Autowired
    private MockMvc server;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private UserCredentialsDAO userCredentialsDAO;

    @Autowired
    private UserInfoDAO userInfoDAO;

    @BeforeEach
    void setUp() {
        String username1 = "firstUserTest";
        String email1 = "firstEmailTest";
        String type1 = "firstTypeTest";

        UserInfo userInfo1 = new UserInfo(username1, email1, type1);
        this.userInfoDAO.save(userInfo1);

        String username2 = "firstUserTest2";
        String email2 = "firstEmailTest2";
        String type2 = "firstTypeTest2";
        UserInfo userInfo2 = new UserInfo(username2, email2, type2);
        this.userInfoDAO.save(userInfo2);

        String password = "firstPasswordTest";
        UserUtils userCredentialsUtils = new UserUtils();;
        String salt = userCredentialsUtils.generateSalt();
        String password_hash = userCredentialsUtils.generateHash(password, salt);
        UserCredentials userCredentials = new UserCredentials(username1, password_hash, salt);
        userCredentials.setUserInfo(userInfo1);
        this.userCredentialsDAO.save(userCredentials);
    }

    @Test
    void testAddOK()throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        String requestBody = "{ \"username\": \"firstUserTest2\", \"password\": \"passTest\" }";

        RequestBuilder request = MockMvcRequestBuilders
                .post("/user/UserCredentials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());



    }
    @Test
    void testAddError()throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        String requestBody = "{ \"username\": \"firstUserTest\", \"password\": \"firstPasswordTest\" }";

        RequestBuilder request = MockMvcRequestBuilders
                .post("/user/UserCredentials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    void testGetOk()throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        RequestBuilder request = MockMvcRequestBuilders
                .get("/user/UserCredentials/firstUserTest")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
        }
    @Test
    void testGetError()throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        RequestBuilder request = MockMvcRequestBuilders
                .get("/user/UserCredentials/InvalidUser")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    void testDeleteOk()throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/user/UserCredentials/firstUserTest")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string("UserCredentials firstUserTest deleted"));
    }

    @Test
    void testUpdateOk()throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String requestBody = "{ \"username\": \"firstUserTest\", \"password\": \"passTest\" }";

        RequestBuilder request = MockMvcRequestBuilders
                .put("/user/UserCredentials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
        }
    @Test
    void testUpdateError()throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String requestBody = "{ \"username\": \"InvalidUser\", \"password\": \"passTest\" }";

        RequestBuilder request = MockMvcRequestBuilders
                .put("/user/UserCredentials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    void testCheckUserOk()throws Exception{
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String requestBody = "{ \"username\": \"firstUserTest\", \"password\": \"firstPasswordTest\" }";

        RequestBuilder request = MockMvcRequestBuilders
                .post("/user/UserCredentials/checkUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testCheckUserError1()throws Exception{
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String requestBody = "{ \"username\": \"firstUserTest\", \"password\": \"invalidPass\" }";

        RequestBuilder request = MockMvcRequestBuilders
                .post("/user/UserCredentials/checkUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }
    @Test
    void testCheckUserError2()throws Exception{
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String requestBody = "{ \"username\": \"InvalidUser\", \"password\": \"firstPasswordTest\" }";

        RequestBuilder request = MockMvcRequestBuilders
                .post("/user/UserCredentials/checkUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

}