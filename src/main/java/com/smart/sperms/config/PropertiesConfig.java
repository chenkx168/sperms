package com.smart.sperms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Descript 自定义配置-实体类映射
 * @Author mojianzhang
 * @Date 2018-07-17 11:15:48
 * @Version 1.0
 */
@Configuration
public class PropertiesConfig {

    @Value("${file_store.image_path}")
    private String image_file_path;

    @Value("${file_store.image_group}")
    private String image_file_group;

    public String getImage_file_path() {
        return image_file_path;
    }

    public void setImage_file_path(String image_file_path) {
        this.image_file_path = image_file_path;
    }

    public String getImage_file_group() {
        return image_file_group;
    }

    public void setImage_file_group(String image_file_group) {
        this.image_file_group = image_file_group;
    }
}
