package com.hc.qa.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hc.common.utils.JwtUtils;
import com.hc.common.utils.ResultStatus;
import com.hc.common.utils.TrainingResult;
import com.hc.qa.client.UcenterClient;
import com.hc.qa.entity.Answer;
import com.hc.qa.entity.Question;
import com.hc.qa.entity.Staff;
import com.hc.qa.entity.TreeAnswer;
import com.hc.qa.service.AnswerService;
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
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    private UcenterClient ucenterClient;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionService questionService;


    @GetMapping("/{page}/{limit}/{questionId}")
    @ApiOperation("分页获取指定问题下的回答及评论数据")
    public TrainingResult getTreeAnswer(@PathVariable("page") Long page,
                                        @PathVariable("limit") Long limit,
                                        @PathVariable("questionId") String questionId) {
        Page<Answer> answerPage = new Page<>(page, limit);
        List<TreeAnswer> treeAnswer = answerService.getTreeAnswer(answerPage, questionId);
        return TrainingResult.ok().data("treeAnswer", treeAnswer).data("total", answerPage.getTotal());
    }

    @GetMapping("/staff/{page}/{limit}")
    public TrainingResult getStaffAnswer(@PathVariable("page") Long page,
                                         @PathVariable("limit") Long limit,
                                         String staffId){
        Page<Question> questionPage = new Page<>(page, limit);
        answerService.getMyAnswer(questionPage, staffId);
        return TrainingResult.ok().data("total", questionPage.getTotal()).data("rows", questionPage.getRecords());
    }



    @PostMapping("/add")
    @ApiOperation("添加回答")
    public TrainingResult addAnswer(@RequestBody Answer answer, HttpServletRequest request) {
        // 检查登录
        String staffId = JwtUtils.getStaffIdByJwtToken(request);
        if (StringUtils.isEmpty(staffId)) {
            return TrainingResult.ok().status(ResultStatus.UNAUTHORIZED.getCode()).message("请先登录！");
        } else {
            // 调用用户中心微服务获取回答人相关信息
            TrainingResult result = ucenterClient.getUserInfo(staffId);
            if (result.getSuccess()) {
                Map<String, Object> data = result.getData();
                Object staffInfo = data.get("staffInfo");
                ObjectMapper objectMapper = new ObjectMapper();
                Staff staff = objectMapper.convertValue(staffInfo, Staff.class);
                // 设置回答人相关信息
                answer.setAvatar(staff.getAvatar());
                answer.setUsername(staff.getUsername());
                answer.setStaffId(staffId);
                boolean success = answerService.save(answer);
                // 回答成功之后问题表回答数+1
                if (success) {
                    success = questionService.updateAnswerNum(answer.getQuestionId());
                }
                return success ? TrainingResult.ok() : TrainingResult.error().message("服务器错误！回答失败！");
            } else {
                return TrainingResult.error().message("获取回答人信息失败！");
            }
        }
    }
}

