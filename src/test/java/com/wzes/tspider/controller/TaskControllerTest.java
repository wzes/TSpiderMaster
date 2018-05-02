package com.wzes.tspider.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Create by xuantang
 * @date on 5/1/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext transdocApplication;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.transdocApplication).build();
    }
    @Test
    public void build() {
        try {
            MvcResult mvcResult = mockMvc.perform(post("/task"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andReturn();
            System.out.println(mvcResult.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void downloadConfigJsonFile() {
    }

    @Test
    public void viewProgress() {
        try {
            MvcResult mvcResult = mockMvc.perform(get("/task/progress/7f0b560ee6254afb8301f3fe02a94cf2"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andReturn();
            System.out.println(mvcResult.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void viewDetail() {
        try {
            MvcResult mvcResult = mockMvc.perform(get("/task/detail/7f0b560ee6254afb8301f3fe02a94cf2"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andReturn();
            System.out.println(mvcResult.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}