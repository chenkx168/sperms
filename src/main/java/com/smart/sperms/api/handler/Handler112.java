package com.smart.sperms.api.handler;

import com.smart.sperms.api.protocol.DataBody112;
import com.smart.sperms.api.protocol.MsgPayload;
import com.smart.sperms.config.PropertiesConfig;
import com.smart.sperms.dao.MonitorPicDao;
import com.smart.sperms.dao.model.MonitorPic;
import com.smart.sperms.utils.DateUtils;
import com.smart.sperms.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * 处理110协议
 */
@Component
public class Handler112 extends Handler {

    @Autowired
    private PropertiesConfig propsConfig;

    @Autowired
    private MonitorPicDao monitorPicDao;

    @Override
    public void execute(String eId, MsgPayload req) {
        DataBody112 body = (DataBody112)req.getData();
        logger.info("recv android msg...body = {}", body);
        saveImage(eId, body.getImage());
    }

    /**
     * 保存设备上传的图片
     * @param eId
     * @param imgBase64
     */
    private void saveImage(String eId, String imgBase64) {
        Date curDate = new Date();
        long timeMs = curDate.getTime();
        String timeDir = DateUtils.parseDateToStr(curDate, "yyyyMMdd");

        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-", "");
        String imgName = "IMG112_"+ eId +"_"+ timeMs +"_"+ uuid +".jpg";

        String IMG_GROUP_PATH = propsConfig.getImage_file_group();
        String IMG_STORE_PATH = propsConfig.getImage_file_path();
        if(StringUtils.isEmpty(IMG_STORE_PATH)) {
            IMG_STORE_PATH = "/opt/file_store/images";
        }
        String absPath = IMG_STORE_PATH + File.separator + eId + File.separator + timeDir + File.separator +imgName;
        String groupPath = absPath.replace(IMG_STORE_PATH, IMG_GROUP_PATH);

        OutputStream out = null;
        try {
            File imgFile = new File(IMG_STORE_PATH);
            if(imgFile.exists()) {
                logger.info("image is exists... FILE_PATH = {}", IMG_STORE_PATH);
                return;
            }

            byte[] imgByte = Base64.getDecoder().decode(imgBase64);

            File fileParent = imgFile.getParentFile();
            if(!fileParent.exists()){
                fileParent.mkdirs();
            }

            out = new FileOutputStream(imgFile);
            out.write(imgByte);
            out.flush();

            MonitorPic pic = new MonitorPic();
            pic.seteId(eId);
            pic.setPicFilename(imgFile.getName());
            pic.setPicPath(groupPath);
            pic.setPicTime(curDate);

            monitorPicDao.saveData(pic);

        } catch (Exception e) {
            logger.error("保存图片数据异常", e);
        } finally {
            try {
                if(out != null) {
                    out.close();
                    out = null;
                }
            } catch (Exception e2) {
                logger.error("关闭图片文件输出流异常", e2);
            }
        }
    }
}
