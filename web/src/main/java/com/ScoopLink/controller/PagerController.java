package com.ScoopLink.controller;


import com.ScoopLink.manageQuestion.papers.dto.Paper;
import com.ScoopLink.manageQuestion.papers.server.PaperServer;
import com.ScoopLink.response.CommonResponse;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/pager/v1")
@RestController
public class PagerController {

    @Resource
    private PaperServer paperServer;

    /**
     * 初始化试卷
     * @param paper 试卷信息
     * @return 试卷信息
     */
    @PostMapping("/init")
    public CommonResponse<Paper> initPaper(@RequestBody Paper paper) {
        Paper createdPaper = paperServer.CreatePaper(paper);
        return CommonResponse.success(createdPaper);
    }


    /**
     * 获取试卷详情
     * @param id 试卷ID
     * @return 试卷信息
     */
    @GetMapping("/{id}")
    public CommonResponse<Paper> getPaperDetail(@PathVariable Long id) {
        Paper paper = paperServer.GetPaper(id);
        return CommonResponse.success(paper);
    }


     /**
      * 获取试卷列表
      * @return 试卷列表
      */
    @GetMapping("/list")
    public CommonResponse<List<Paper>> getPaperList() {
        List<Paper> paperList = paperServer.GetPaperList();
        return CommonResponse.success(paperList);
    }


    /**
     * 删除试卷
     * @param id 试卷ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public CommonResponse<Boolean> deletePaper(@PathVariable Long id) {
        boolean isDeleted = paperServer.DeletePaper(id);
        return CommonResponse.success(isDeleted);
    }

    /**
     * 批量删除试卷
     * @param ids 试卷ID列表
     * @return 删除结果
     */
    @PostMapping("/batch")
    public CommonResponse<Boolean> deletePaperList(@RequestBody List<Long> ids) {
        boolean isDeleted = paperServer.DeletePaperList(ids);
        return CommonResponse.success(isDeleted);
    }


     /**
      * 更新试卷信息
      * @param paper 试卷信息
      * @return 更新结果
      */
    @PutMapping("/update")
    public CommonResponse<Paper> updatePaper(@RequestBody Paper paper) {
        Paper updatedPaper = paperServer.UpdatePaper(paper);
        return CommonResponse.success(updatedPaper);
    }






}
