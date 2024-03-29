package tss.g2.fyre.test.models.utils;

import io.javalin.http.UploadedFile;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tss.g2.fyre.utils.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

public class UtilsTest {
    @Test
    public void testDate() throws ParseException {
        Date dateConverter = new DateConverter("29/03/1999 22:17:43").date();
       // Assert.assertEquals(new DateConverter("29/03/1999 22:17:43"), dateConverter);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Date date = dateFormat.parse("29/03/1999 22:17:43");
        date.setTime(date.getTime() + date.getTimezoneOffset() * 60000);
        Assert.assertEquals(dateConverter, date);
    }

    @Test
    public void toHashTest() {
        String toHash = new ToHash("asd").getHash();
        Assert.assertEquals("688787d8ff144c502c7f5cffaafe2cc588d86079f9de88304c26b0cb99ce91c6", toHash);
    }

//    database_host: 127.0.0.1
//    database_port: 5432
//    database_database: postgres
//    database_user: postgres
//    database_password: config/password
//    port: 7000
    @Before
    public void confTestStart() throws IOException {
        File config = new File("configuration.yml");
        File pass = new File("password");
        FileWriter frconf = new FileWriter(config);
        FileWriter frpass = new FileWriter(pass);
        frconf.write("database_host: 127.0.0.1\n");
        frconf.write("database_port: 5432\n");
        frconf.write("database_database: postgres\n");
        frconf.write("database_user: postgres\n");
        frconf.write("database_password: password\n");
        frconf.write("port: 7000\n");
        frpass.write("postgress");
        frconf.close();
        frpass.close();

    }

    @After
    public void confTestEnd() {
         new File("configuration.yml").delete();
        new File("password").delete();

    }
    @Test
    public void testExistConf() {
         Properties properties = new Configuration("configuration.yml").getProperties();
         String host = properties.getProperty("database_host");
         String port = properties.getProperty("database_port");
         String database = properties.getProperty("database_database");
         String user = properties.getProperty("database_user");
         String password = properties.getProperty("database_password");
         String port1 = properties.getProperty("port");
         Assert.assertEquals("127.0.0.1", host);
        Assert.assertEquals("5432", port);
        Assert.assertEquals("postgres", database);
        Assert.assertEquals("postgres", user);
        Assert.assertEquals("postgress", password);
        Assert.assertEquals("7000", port1);

    }

    @Test
    public void randomStringTest() {
        RandomString rs = new RandomString(10);
        String s1 = rs.generate();
        String s2 = rs.generate();
        String s3 = rs.generate();
        Assert.assertNotEquals(s1, s2);
        Assert.assertNotEquals(s1, s3);
    }

    private static double compareByteArrays(byte[] a, byte[] b) {
      int n = Math.min(a.length, b.length), nLarge = Math.max(a.length, b.length);
      int unequalCount = nLarge - n;
      for (int i=0; i<n; i++)
        if (a[i] != b[i]) unequalCount++;
      return unequalCount * 100.0 / nLarge;
    }

    @Test
    public void testStoreImage () throws IOException {
      UploadedFile file = new UploadedFile(new FileInputStream(new File("images/unnamed")),
          "image", Files.readAllBytes(Paths.get("images/unnamed")).length,
          "images/unnamed", "jpg");
      String path = "images/" + new StoreImage(file).store();
      double p = compareByteArrays(Files.readAllBytes(Paths.get("images/unnamed")), Files.readAllBytes(Paths.get(path)));
      Assert.assertEquals(p, 0, 0.0000001);
      new File(path).delete();
    }
}
