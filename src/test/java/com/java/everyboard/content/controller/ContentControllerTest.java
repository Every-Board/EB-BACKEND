//package com.java.everyboard.content.controller;
//
//import com.google.gson.Gson;
//import com.java.everyboard.constant.ActiveStatus;
//import com.java.everyboard.constant.Category;
//import com.java.everyboard.constant.LoginType;
//import com.java.everyboard.content.dto.ContentPatchDto;
//import com.java.everyboard.content.dto.ContentPostDto;
//import com.java.everyboard.content.dto.ContentResponseDto;
//import com.java.everyboard.content.entity.Content;
//import com.java.everyboard.content.mapper.ContentMapper;
//import com.java.everyboard.content.service.ContentService;
//import com.java.everyboard.user.User;
//import com.jayway.jsonpath.JsonPath;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.util.UriComponents;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import javax.transaction.Transactional;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotNull;
//
//import java.net.URI;
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.doNothing;
//import static org.slf4j.MDC.get;
//import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@Transactional
//@SpringBootTest
//@AutoConfigureMockMvc
//@WithMockUser
//class ContentControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private Gson gson;
//
//    @MockBean
//    private ContentService contentService;
//
//    @MockBean
//    private ContentMapper contentMapper;
//
//    @Test
//    void postContent() throws Exception{
//
//        // ContentPostDto에 기입될 정보 입력 //
//        ContentPostDto post = new ContentPostDto(1L, "반갑습니다","안녕하세요","image.png", Category.Board,"안녕");
//
//        ContentResponseDto responseBody = new ContentResponseDto(1L,1L,1L,1L,"반갑습니다","안녕하세요","image.png", Category.Board,"안녕", LocalDateTime.now(), LocalDateTime.now());
//
//        // given
//        given(contentMapper.contentPostDtoToContent(Mockito.any(ContentPostDto.class))).willReturn(new Content());
//
//        given(contentService.createContent(Mockito.any(Content.class))).willReturn(new Content());
//
//        given(contentMapper.contentToContentResponse(Mockito.any(Content.class))).willReturn(responseBody);
//
//        Gson gson = new Gson();
//
//        String content = gson.toJson(post);
//        URI uri = UriComponentsBuilder.newInstance().path("/contents").build().toUri();
//
//        // when
//                ResultActions actions =
//                mockMvc.perform(
//                        MockMvcRequestBuilders
//                                .post(uri)
//                                .accept(MediaType.APPLICATION_JSON)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .with(csrf()) // 403 에러가 날 때 csrf 때문에 error가 발생하는 것임 (.with(csrf())을 추가하면 됨)
//                                .content(content));
//        // then
//        MvcResult result = actions
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.data.userId").value(post.getUserId()))
//                .andExpect(jsonPath("$.data.title").value(post.getTitle()))
//                .andExpect(jsonPath("$.data.content").value(post.getContent()))
////                .andExpect(jsonPath("$.data.category").value(post.getCategory()))
//                .andExpect(jsonPath("$.data.tag").value(post.getTag()))
//                .andReturn();
//    }
//
//    /*@Test
//    void getContent() throws Exception {
//        //given
//        long contentId = 1L;
//
//        Content content = new Content();
//        content.setContentId(contentId);
//
//        ContentResponseDto response = new ContentResponseDto(1L,1L,1L,1L,"반갑습니다","안녕하세요","image.png", Category.Board,"안녕", LocalDateTime.now(), LocalDateTime.now());
//
//        given(contentService.findContent(Mockito.anyLong())).willReturn(new Content());
//
//        given(contentMapper.contentToContentResponse(Mockito.any(Content.class))).willReturn(response);
//
//
//        URI uri = UriComponentsBuilder.newInstance().path("/contents/{contentId}").buildAndExpand(contentId).toUri();
//
//        // when
//        ResultActions actions = mockMvc.perform(
//                MockMvcRequestBuilders
//                        .get(uri)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .with(csrf()));
//
//        // then
//        actions.andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.contentId").value(content.getContentId()))
//                .andExpect(jsonPath("$.viewCount").value(content.getViewCount()))
//                .andExpect(jsonPath("$.heartCount").value(content.getHeartCount()))
//                .andExpect(jsonPath("$.title").value(content.getTitle()))
//                .andExpect(jsonPath("$.content").value(content.getContent()))
//                .andExpect(jsonPath("$.imageUrl").value(content.getImageUrl()))
//                .andExpect(jsonPath("$.category").value(content.getCategory()))
//                .andExpect(jsonPath("$.createAt").value(content.getCreatedAt().toLocalTime()))
//                .andExpect(jsonPath("$.modifiedAt").value(content.getModifiedAt().toLocalTime()))
//                .andExpect(jsonPath("$.tag").value(content.getTag()));
//    }*/
//
////    @Test
////    void getContents() {
////
////        ContentPostDto post1 = new ContentPostDto(1L, "반갑습니다","안녕하세요","image.png", Category.Board,"안녕");
////        String postContent1 = gson.toJson(post1);
////
////        ContentPostDto post2 = new ContentPostDto(2L, "반과수깡","안녕하수꽝","image2.png", Category.Board,"안녕");
////        String postContent2 = gson.toJson(post2);
////
////        URI postUri = UriComponentsBuilder.newInstance().path("/contents").build().toUri();
////
////        String page = "1";
////        String size = "10";
////        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
////        queryParams.add("page", page);
////        queryParams.add("size", size);
////
////        URI getUri = UriComponentsBuilder.newInstance().path("/contents").build().toUri();
////
////
////        ResultActions actions = mockMvc.perform(getRequestBuilder(uri, queryParams));
////        // when
////        ResultActions actions =
////                mockMvc.perform(
//////                        get(getUri)
//////                                .params(queryParams)
////                        getRequestBuilder(getUri,queryParams)
////                                .accept(MediaType.APPLICATION_JSON)
////                );
////
////        // then
////        MvcResult result = actions
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.content").isArray())
////                .andReturn();
////
////        List list = JsonPath.parse(result.getResponse().getContentAsString()).read("$.data");
////
////        assertThat(list.size(), is(2));
////    }
//
//    @Test
//    void patchContent() throws Exception {
//
//        // 컨텐츠 수정 정보 기입 //
//        long contentId = 1L;
//        ContentPatchDto patch = new ContentPatchDto(1L, "반가와유","안냐세용","image2.png", Category.Board,"안녕");
//
//        // 컨텐츠 수정 정보 리스폰스 //
//        ContentResponseDto response = new ContentResponseDto(1L,1L, 2L, 1L,"반가와유","안냐세용","image2.png", Category.Board,"안녕",LocalDateTime.now(),LocalDateTime.now());
//
//
//        //given
//        given(contentMapper.contentPatchDtoToContent(Mockito.any(ContentPatchDto.class))).willReturn(new Content());
//
//        given(contentService.updateContent(Mockito.any(Content.class))).willReturn(new Content());
//
//        given(contentMapper.contentToContentResponse(Mockito.any(Content.class))).willReturn(response);
//
//        Gson gson = new Gson();
//
//        String content = gson.toJson(patch);
//
//        URI uri = UriComponentsBuilder.newInstance().path("/contents/{contentId}").buildAndExpand(contentId).toUri();
//
//
//        // when
//        ResultActions actions =
//        mockMvc.perform(
//                MockMvcRequestBuilders
//                        .patch(uri)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .with(csrf())
//                        .content(content));
//
//        // then
//        actions.andExpect(status().isOk())
//                .andExpect(jsonPath("$.contentId").value(patch.getContentId()))
//                .andExpect(jsonPath("$.title").value(patch.getTitle()))
//                .andExpect(jsonPath("$.content").value(patch.getContent()))
//                .andExpect(jsonPath("$.imageUrl").value(patch.getImageUrl()))
////                .andExpect(jsonPath("$.data.category").value(patch.getCategory()))
//                .andExpect(jsonPath("$.tag").value(patch.getTag()));
//
//    }
//
//    @Test
//    void deleteContent() throws Exception {
//        // given
//        long contentId = 1L;
//        doNothing().when(contentService).deleteContent(contentId);
//
//        // when
//        ResultActions actions = mockMvc.perform(delete("/contents/" + contentId).with(csrf()));
//
//        // then
//        actions.andExpect(status().isNoContent());
//    }
//
////    @Test
////    void getContentFromCategory() {
////    }
//}