package ee.hitsa.ois.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.service.OisFileService;

@RestController
@RequestMapping("/oisfile")
public class OisFileController {

    @Autowired
    private OisFileService oisFileService;

    /**
     * FIXME: Add authorization. This solution is bad, currently any user can download any file.
     *
     * Method for downloading or displaying files. Firstly was used on
     * higher/vocational curriculum forms
     */
    @GetMapping("/get/{type}/{id}")
    public void get(@PathVariable("type") String type, @PathVariable("id") String id, HttpServletResponse response) throws IOException {
        oisFileService.get(type, id, response);
    }
}
