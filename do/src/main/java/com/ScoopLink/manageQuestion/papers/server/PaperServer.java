package com.ScoopLink.manageQuestion.papers.server;

import com.ScoopLink.manageQuestion.papers.dto.Paper;
import java.util.List;

public interface PaperServer {

     /**
      * 创建试卷
      * @param paper 试卷信息
      * @return 是否成功
      */
    public boolean CreatePaper(Paper paper);

     /**
      * 获取试卷信息
      * @param id 试卷ID
      * @return 试卷信息
      */
    public Paper GetPaper(Long id);


     /**
      * 获取试卷列表
      * @return 试卷列表
      */
    public List<Paper> GetPaperList();


     /**
      * 更新试卷信息
      * @param paper 试卷信息
      * @return 是否成功
      */
    public boolean UpdatePaper(Paper paper);


     /**
      * 删除试卷
      * @param id 试卷ID
      * @return 是否成功
      */
    public boolean DeletePaper(Long id);

    /**
     * 批量删除试卷
     * @param ids 试卷ID列表
     * @return 是否成功
     */
    public boolean DeletePaperList(List<Long> ids);
}
