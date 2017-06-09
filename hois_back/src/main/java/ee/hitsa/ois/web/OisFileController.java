package ee.hitsa.ois.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.util.WithEntity;

@RestController
@RequestMapping("/oisfile")
public class OisFileController {

    /**
     * Method for downloading or displaying files.
     * Firstly was used on higher/vocational curriculum forms
     */
    @SuppressWarnings("finally")
    @GetMapping("/get/{id:\\d+}")
    public void get(@WithEntity("id") OisFile oisFile, HttpServletResponse response) throws IOException {
        try {
            response.setContentType(oisFile.getFtype());
            response.setHeader("Content-Disposition", "attachment;filename=" + oisFile.getFname());
            response.getOutputStream().write(oisFile.getFdata());
            response.flushBuffer();
        } finally {
            response.getOutputStream().close();
            return;
        }
    }
}
