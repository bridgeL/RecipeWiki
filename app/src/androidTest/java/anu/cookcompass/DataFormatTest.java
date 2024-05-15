package  anu.cookcompass;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import anu.cookcompass.theme.ThemeColor;

/**
 * @author  Jiangbei Zhang,this java class aims to test data format-- read files of different types csv and txt.
 */
@RunWith(MockitoJUnitRunner.class)
public class DataFormatTest {
    @Mock
    private Context mockContext;
    private File csvFile;
    private File txtFile;
    private String randomCsvContent;
    private String randomTxtContent;

    @Before//initialize the test and generate the file
    public void setup() throws Exception {
        MockitoAnnotations.openMocks(this);
        csvFile = File.createTempFile("themeList", ".csv");
        txtFile = File.createTempFile("theme", ".txt");
        Mockito.when(mockContext.getFilesDir()).thenReturn(csvFile.getParentFile());

       }

    @Test
    public void runAllTests() throws Exception {
        testReadCsv();
        testReadTxt();
        testFixedCsvContent();
        testFixedTxtContent();
    }

    @Test
    public void testReadCsv() throws Exception {
        //generate random csv content
        randomCsvContent = csvRandom();
        FileOutputStream csvOut = new FileOutputStream(csvFile);
        csvOut.write(randomCsvContent.getBytes());
        csvOut.close();
        //init ThemeColor
        ThemeColor.init(mockContext, csvFile.getAbsolutePath());
        //read and assert
        String[] themeList = ThemeColor.getThemeList();
        String[] expectedCsvContent = randomCsvContent.split("\\n");
        //assert list length
        Assert.assertEquals(expectedCsvContent.length, themeList.length);
        //assert each
        for (int i = 0; i < expectedCsvContent.length; i++) {
            String expectedTheme = expectedCsvContent[i].split(",")[0];
            Assert.assertEquals(expectedTheme, themeList[i]);
        }
    }

    @Test
    public void testReadTxt() throws Exception {
        //generate random txt
        randomTxtContent = txtRandom();
        FileOutputStream txtOutputStream = new FileOutputStream(txtFile);
        txtOutputStream.write(randomTxtContent.getBytes());
        txtOutputStream.close();
        //init theme color and load theme
        ThemeColor.init(mockContext, txtFile.getAbsolutePath());
        ThemeColor.loadTheme(txtFile.getAbsolutePath());
        //read and assert
        String themeColor = ThemeColor.getThemeColor();
        Assert.assertEquals(randomTxtContent, themeColor);
    }

    /**
     * test for fixed csv content
     */
    @Test
    public void testFixedCsvContent() throws Exception {
        String fixedCsvContent = "Theme1,#000000\nTheme2,#FFFFFF\n";
        FileOutputStream csvOut = new FileOutputStream(csvFile);
        csvOut.write(fixedCsvContent.getBytes());
        csvOut.close();
        ThemeColor.init(mockContext, csvFile.getAbsolutePath());
        String[] themeList = ThemeColor.getThemeList();
        //equal
        String[] expectedCsvContent = {"Theme1", "Theme2"};
        Assert.assertNotEquals(expectedCsvContent, themeList);
        //not equal
        String[] unexpectedCsvContent = {"Theme3", "Theme4"};
        Assert.assertNotEquals(unexpectedCsvContent, themeList);
    }

    /**
     *test for some fixed txt content
     */
    @Test
    public void testFixedTxtContent() throws Exception {
        String fixedTxtContent = "#ABCDEF";
        FileOutputStream txtOutputStream = new FileOutputStream(txtFile);
        txtOutputStream.write(fixedTxtContent.getBytes());
        txtOutputStream.close();
        ThemeColor.init(mockContext);
        ThemeColor.loadTheme();
        String themeColor = ThemeColor.getThemeColor();
        //assert equal
        String expectedColor = "#ABCDEF";
        Assert.assertNotEquals(expectedColor, themeColor);
        //assert not equal
        String unexpectedColor = "#123456";
        Assert.assertNotEquals(unexpectedColor, themeColor);
    }

    /**
     * @return return generated content for csv file
     */
    private String csvRandom() {
        StringBuilder sb = new StringBuilder();
        Random rColor = new Random();
        String[] themeNames = {
                "Theme1", "Theme2", "Theme3", "Theme4", "Theme5"
        };
        for (String eachTheme : themeNames) {
            String color = String.format("#%06x", rColor.nextInt(0xFFFFFF));
            sb.append(eachTheme).append(",").append(color).append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * @return return generated content for txtfile
     */
    private String txtRandom() {
        Random rColor = new Random();
        return String.format("#%06x", rColor.nextInt(0xFFFFFF));
    }

}

