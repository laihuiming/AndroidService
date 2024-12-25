
package com.example.androidservice.service.impl;

import com.example.androidservice.service.DocService;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import org.springframework.stereotype.Service;

/**
 @author: laihuiming
 @date: 2024/2/4
 @version: 1.0
 @description:
 **/
@Service
public class DocServiceImpl implements DocService {
    @Override
    public void wordToPdf() {
        Document document = new Document();
        document.loadFromFile("D:\\AJavaWork\\AndroidService\\src\\main\\resources\\file\\接口测试用例.doc");
        document.saveToFile("D:\\AJavaWork\\AndroidService\\src\\main\\resources\\file\\接口测试用例.pdf", FileFormat.PDF);
    }
}
