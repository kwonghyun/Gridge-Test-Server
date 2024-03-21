package com.example.demo.src.report.model;

import com.example.demo.common.Constant;
import com.example.demo.common.validation.EnumValueCheck;
import com.example.demo.src.report.entity.Report;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class PostReportFeedReq {
    @EnumValueCheck(enumClass = Report.Reason.class, message = Constant.REPORT_REASON_VALID)
    @NotNull(message = Constant.REPORT_REASON_VALID)
    private String reason;
}
