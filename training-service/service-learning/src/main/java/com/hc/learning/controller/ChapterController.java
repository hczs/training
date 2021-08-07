package com.hc.learning.controller;


import com.hc.common.utils.TrainingResult;
import com.hc.learning.entity.Chapter;
import com.hc.learning.entity.vo.TreeChapter;
import com.hc.learning.service.ChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author houcheng
 * @since 2021-02-25
 */
@RestController
@RequestMapping("/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @GetMapping("/treeList/{courseId}")
    @ApiOperation("获取课程下章节树形结构数据")
    public TrainingResult getTreeList(@PathVariable(value = "courseId") String courseId) {
        List<TreeChapter> treeChapter = chapterService.getTreeChapter(courseId);
        return TrainingResult.ok().data("treeChapter", treeChapter);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据章节id获取章节信息")
    public TrainingResult getById(@PathVariable(value = "id") String chapterId) {
        Chapter chapter = chapterService.getById(chapterId);
        return TrainingResult.ok().data("chapter", chapter);
    }

    @PostMapping("/add")
    @ApiOperation("添加章节")
    public TrainingResult addChapter(@RequestBody Chapter chapter) {
        boolean success = chapterService.save(chapter);
        return success ? TrainingResult.ok() : TrainingResult.error().message("服务器错误，添加失败！");
    }

    @PostMapping("/update")
    @ApiOperation("更新章节")
    public TrainingResult updateChapter(@RequestBody Chapter chapter) {
        boolean success = chapterService.saveOrUpdate(chapter);
        return success ? TrainingResult.ok() : TrainingResult.error().message("服务器错误，修改失败！");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除章节")
    public TrainingResult deleteChapter(@PathVariable(value = "id") String id) {
        boolean success = chapterService.deleteChapter(id);
        return success ? TrainingResult.ok() : TrainingResult.error().message("服务器错误，删除失败！");
    }
}

