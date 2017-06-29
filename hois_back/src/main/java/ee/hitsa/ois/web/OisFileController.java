package ee.hitsa.ois.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.WithEntity;

@RestController
@RequestMapping("/oisfile")
public class OisFileController {

    /**
     * Method for downloading or displaying files. Firstly was used on
     * higher/vocational curriculum forms
     */
    @GetMapping("/get/{id:\\d+}")
    public void get(@WithEntity("id") OisFile oisFile, HttpServletResponse response) throws IOException {
        HttpUtil.file(response, oisFile.getFname(), oisFile.getFtype(), oisFile.getFdata());
    }
}
