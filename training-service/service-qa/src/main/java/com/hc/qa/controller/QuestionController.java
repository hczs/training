package com.hc.qa.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hc.common.utils.JwtUtils;
import com.hc.common.utils.ResultStatus;
import com.hc.common.utils.TrainingResult;
import com.hc.qa.client.UcenterClient;
import com.hc.qa.entity.Question;
import com.hc.qa.entity.Staff;
import com.hc.qa.service.QuestionService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author houcheng
 * @since 2021-04-02
 */
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UcenterClient ucenterClient;

    @GetMapping("/{page}/{limit}")
    @ApiOperation("分页查询问题列表")
    public TrainingResult pageQuestion(@PathVariable("page") Long page, @PathVariable("limit") Long limit) {
        Page<Question> questionPage = new Page<>(page, limit);
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        // 按照更新时间倒序查询
        questionService.page(questionPage, wrapper.orderByDesc("update_time"));
        return TrainingResult.ok().data("total", questionPage.getTotal()).data("rows", questionPage.getRecords());
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id获取问题详情信息")
    public TrainingResult getQuestion(@PathVariable("id") String id) {
        Question question = questionService.getById(id);
        if (question != null) {
            return TrainingResult.ok().data("question", question);
        } else {
            return TrainingResult.error().message("问题详情获取失败！");
        }
    }

    @GetMapping("/my-question/{page}/{limit}")
    @ApiOperation("我的提问")
    public TrainingResult myQuestion(HttpServletRequest request,
                                     @PathVariable("page") Long page,
                                     @PathVariable("limit") Long limit) {
        String staffId = JwtUtils.getStaffIdByJwtToken(request);
        if (StringUtils.isEmpty(staffId)) {
            return TrainingResult.ok().status(ResultStatus.UNAUTHORIZED.getCode()).message("请先登录！");
        } else {
            Page<Question> questionPage = new Page<>(page, limit);
            QueryWrapper<Question> wrapper = new QueryWrapper<>();
            wrapper.eq("staff_id", staffId);
            questionService.page(questionPage, wrapper);
            return TrainingResult.ok().data("rows", questionPage.getRecords()).data("total", questionPage.getTotal());
        }
    }

    @PostMapping("/add")
    @ApiOperation("添加问题")
    public TrainingResult addQuestion(@RequestBody Question question, HttpServletRequest request) {
        String staffId = JwtUtils.getStaffIdByJwtToken(request);
        if (StringUtils.isEmpty(staffId)) {
            return TrainingResult.ok().status(ResultStatus.UNAUTHORIZED.getCode()).message("请先登录！");
        } else {
            // 调用用户中心微服务获取提问人相关信息
            TrainingResult result = ucenterClient.getUserInfo(staffId);
            if (result.getSuccess()) {
                Map<String, Object> data = result.getData();
                Object staffInfo = data.get("staffInfo");
                ObjectMapper objectMapper = new ObjectMapper();
                Staff staff = objectMapper.convertValue(staffInfo, Staff.class);
                // 封装提问人的信息
                question.setAvatar(staff.getAvatar());
                question.setUsername(staff.getUsername());
                question.setStaffId(staffId);
                boolean success = questionService.save(question);
                return success ? TrainingResult.ok() : TrainingResult.error().message("服务器错误，提问失败！");
            } else {
                return TrainingResult.error().message("获取提问人信息失败！");
            }
        }
    }
}

