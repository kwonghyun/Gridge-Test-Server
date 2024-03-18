package com.example.demo.src.feed.model;

import com.example.demo.src.feed.entity.Report;
import lombok.Getter;

@Getter
public class PostReportCommentReq {
    private Report.Reason reason;
}
