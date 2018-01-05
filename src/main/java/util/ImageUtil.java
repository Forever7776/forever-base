package util;

import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kz
 */
public class ImageUtil {

    /**
     * 计算图片尺寸大小等信息：w宽、h高、s大小。异常时返回null。
     *
     * @param file 图片
     * @return 图片信息map
     */
    public static Map<String, Long> getImgInfo(File file) {
        Map<String, Long> map = new HashMap<String, Long>(3);
        try {
            BufferedImage buff = ImageIO.read(file);
            map.put("w", buff.getWidth() * 1L);
            map.put("h", buff.getHeight() * 1L);
        } catch (IOException e) {
            System.err.println("计算图片信息失败！");
            map = null;
        }
        return map;
    }

    /**
     * 实现图像的等比缩放
     *
     * @param inputStream 图片文件流
     * @param targetW 目标宽度
     * @param targetH 目标高度
     * @param isFixWidthAndHeight 是否固定宽高,true固定宽高压缩|false等比压缩
     * @return
     */
    public static BufferedImage resize(InputStream inputStream, int targetW,
                                       int targetH, boolean isFixWidthAndHeight) throws IOException {
        BufferedImage source = ImageIO.read(inputStream);
        int type = source.getType();
        BufferedImage target = null;
        double sx = (double) targetW / source.getWidth();
        double sy = (double) targetH / source.getHeight();
        if(!isFixWidthAndHeight){
            if (sx < sy) {
                sx = sy;
                targetW = (int) (sx * source.getWidth());
            } else {
                sy = sx;
                targetH = (int) (sy * source.getHeight());
            }
        }
        // handmade
        if (type == BufferedImage.TYPE_CUSTOM) {
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW,
                    targetH);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else {
            target = new BufferedImage(targetW, targetH, type);
        }

        Graphics2D g = target.createGraphics();
        // smoother than exlax:
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return target;
    }

    public static void main(String[] args) throws IOException {
        String file = "C:\\Users\\Dell\\Pictures\\DAimG_2015110122421225X7K8.jpg";
        String formatName = StringUtils.substringAfter(file, ".");
        InputStream in2 = new FileInputStream(new File(file));
        BufferedImage out = ImageUtil.resize(in2, 728, 390, false);
        String outFile = "d://test.jpg";
        ImageIO.write(out, formatName, new File(outFile));
    }
}
