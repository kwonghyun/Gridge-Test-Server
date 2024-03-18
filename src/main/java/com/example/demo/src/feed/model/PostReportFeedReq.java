package com.example.demo.src.feed.model;

import com.example.demo.src.feed.entity.Report;
import lombok.Getter;

@Getter
public class PostReportFeedReq {
    private Report.Reason reason;
}
