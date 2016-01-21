package org.cxio.tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageCacheTest {

    public static void main(final String[] args) throws IOException {
        final String id = "9000";
        final String image_format_name = "png";
        BufferedImage img_0 = null;

        img_0 = ImageIO.read(new File("/users/cmzmasek/scratch/__0.png"));

        final String msg = ImageCacheTools.postImage("http://52.35.61.6/image-cache/v1/image/" + image_format_name, id, img_0, image_format_name, BufferedImage.TYPE_INT_ARGB);

        System.out.println("msg  : " + msg);

        System.out.println("img 0: " + img_0);

        final BufferedImage img_1 = ImageCacheTools.readImage("http://52.32.158.148:8080/" + id + "." + image_format_name);

        System.out.println("img 1:" + img_1);

        ImageIO.write(img_1, image_format_name, new File("/users/cmzmasek/scratch/" + id + "." + image_format_name));

        System.out.println("OK");
    }

}
