package com.dotmarketing.image.filter;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.dotmarketing.util.UtilMethods;

public class SVGImageReadingTest {

    @Test
    public void test_embedded_SVG_readability() throws IOException {


        final String svgImageGood = "<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 512 512\"><path d=\"M459.37 151.716c.325 4.548.325 9.097.325 13.645 0 138.72-105.583 298.558-298.558 298.558-59.452 0-114.68-17.219-161.137-47.106 8.447.974 16.568 1.299 25.34 1.299 49.055 0 94.213-16.568 130.274-44.832-46.132-.975-84.792-31.188-98.112-72.772 6.498.974 12.995 1.624 19.818 1.624 9.421 0 18.843-1.3 27.614-3.573-48.081-9.747-84.143-51.98-84.143-102.985v-1.299c13.969 7.797 30.214 12.67 47.431 13.319-28.264-18.843-46.781-51.005-46.781-87.391 0-19.492 5.197-37.36 14.294-52.954 51.655 63.675 129.3 105.258 216.365 109.807-1.624-7.797-2.599-15.918-2.599-24.04 0-57.828 46.782-104.934 104.934-104.934 30.213 0 57.502 12.67 76.67 33.137 23.715-4.548 46.456-13.32 66.599-25.34-7.798 24.366-24.366 44.833-46.132 57.827 21.117-2.273 41.584-8.122 60.426-16.243-14.292 20.791-32.161 39.308-52.628 54.253z\"/></svg>";
        final String svgImageBad = "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" viewBox=\"0 0 179 179\"><defs><style>.cls-1{isolation:isolate;}.cls-2{opacity:0.15;mix-blend-mode:multiply;}.cls-3{fill:#fff;}.cls-4{fill:url(#_3_5);}</style><linearGradient id=\"_3_5\" x1=\"48.56\" y1=\"106.77\" x2=\"123.06\" y2=\"62.27\" gradientUnits=\"userSpaceOnUse\"><stop offset=\"0\" stop-color=\"#c336e5\"/><stop offset=\"1\" stop-color=\"#ef187a\"/></linearGradient></defs><title>Section_Bookmark_Book</title><g class=\"cls-1\"><g id=\"Layer_2\" data-name=\"Layer 2\"><g id=\"Layer_1-2\" data-name=\"Layer 1\"><image class=\"cls-2\" width=\"179\" height=\"179\" xlink:href=\"data:image/png;base64,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\"/><circle class=\"cls-3\" cx=\"89.5\" cy=\"81.5\" r=\"65\"/><path class=\"cls-4\" d=\"M70.61,51.87a5.32,5.32,0,0,0-5.3,5.32v45.57a9.14,9.14,0,0,0-.06,1,7.4,7.4,0,0,0,7.39,7.39h39.47a1.64,1.64,0,0,0,1.64-1.64v-56a1.64,1.64,0,0,0-1.64-1.64Zm7.7,3.28H93.6V73L87,67.17a1.63,1.63,0,0,0-2.17,0L78.31,73Zm32.16,52.7H72.64a4.11,4.11,0,0,1-4.11-4.11c0-.16,0-.31,0-.47l.07-.66h.07a4.12,4.12,0,0,1,4-3h37.83Zm0-11.5H72.64a7.22,7.22,0,0,0-3,.64l-1,.47V57.19a2,2,0,0,1,2-2H75V76.61a1.62,1.62,0,0,0,1,1.49,1.64,1.64,0,0,0,1.76-.26L86,70.58l8.2,7.26a1.68,1.68,0,0,0,1.76.26,1.64,1.64,0,0,0,1-1.49V55.15h13.59Z\"/></g></g></g></svg>";
        File temp = File.createTempFile("tempfile", ".svg");

        String[] images = {svgImageGood, svgImageBad};

        for (String image : images) {
            try (PrintStream out = new PrintStream(new FileOutputStream(temp))) {
                out.print(image);
            }
            try {
                assertTrue(".svg is an image", UtilMethods.isImage(temp.getAbsolutePath()));
                ImageIO.read(temp);
            } catch (Exception e) {
                e.printStackTrace();
                assertTrue("image cannot be read:" + image, false);

            }


        }
    }

}