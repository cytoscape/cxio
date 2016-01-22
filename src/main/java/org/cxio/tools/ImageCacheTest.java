package org.cxio.tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageCacheTest {

    public static void main(final String[] args) throws IOException {
        final String id = "9ed2f322-c08b-11e5-8de3-0251251672f9";
        final String image_format_name = "png";
        BufferedImage img_0 = null;

        img_0 = ImageIO.read(new File("/users/cmzmasek/scratch/__0.png"));

        final String msg = ImageCacheTools.postImage(ImageCacheTools.DEFAULT_WRITE_URL, id, img_0, image_format_name, BufferedImage.TYPE_INT_ARGB);

        System.out.println("msg  : " + msg);

        System.out.println("img 0: " + img_0);

        final BufferedImage img_1 = ImageCacheTools.readImage(ImageCacheTools.DEFAULT_READ_URL, id, image_format_name);

        System.out.println("img 1: " + img_1);

        ImageIO.write(img_1, image_format_name, new File("/users/cmzmasek/scratch/" + id + "." + image_format_name));

        System.out.println("OK");
    }

}
