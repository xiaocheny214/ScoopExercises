package com.ScoopLink.manageQuestion.papers.server;

import com.ScoopLink.manageQuestion.papers.dto.PaperImportTemplate;

public interface PaperImportServer {

    /**
     * 导入试卷（从模板）
     * @param importTemplate 导入模板
     * @return 是否成功
     */
    boolean importPaper(PaperImportTemplate importTemplate);

    /**
     * 从JSON字符串导入试卷
     * @param jsonString JSON格式的试卷数据
     * @return 是否成功
     */
    boolean importPaperFromJson(String jsonString);

    /**
     * 从Excel文件导入试卷
     * @param filePath Excel文件路径
     * @return 是否成功
     */
    boolean importPaperFromExcel(String filePath);
}
