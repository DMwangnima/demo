package cn.nuofankj.myblog.dto.impl;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class AboutDto {
    private Map<String, String> qrcode = new HashMap<>();
    private String html;

    public static AboutDto valueOf(Map<String, String> qrcode, String html) {
        AboutDto aboutDto = new AboutDto();
        aboutDto.setHtml(html);
        aboutDto.setQrcode(qrcode);
        return aboutDto;
    }
}
